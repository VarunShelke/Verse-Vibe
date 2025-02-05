package edu.isr.versevibe.service.index.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import com.fasterxml.jackson.databind.MappingIterator;
import edu.isr.versevibe.config.ElasticSearchConfig;
import edu.isr.versevibe.dto.SongDocument;
import edu.isr.versevibe.service.index.IndexManagementService;
import edu.isr.versevibe.utils.CSVUtils;
import edu.isr.versevibe.utils.IOUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static edu.isr.versevibe.constants.Constants.INDEX_CREATION_ERROR;
import static edu.isr.versevibe.constants.Constants.INDEX_NAME;

@Getter
public class DefaultIndexManagementService implements IndexManagementService {
    private final ElasticsearchClient searchClient;
    private int indexedDocumentsCount;

    @Value("${lyrics.file.path}")
    private String filePath;

    @Value("${elasticsearch.attribute-mappings.path}")
    private String attributeMappingsPath;

    @Value("${document.batch.size}")
    private int batchSize;

    public DefaultIndexManagementService(final ElasticsearchClient searchClient) {
        this.searchClient = searchClient;
        this.indexedDocumentsCount = 0;
    }

    @SneakyThrows
    @Override
    public boolean createIndex(final String indexName, final String attributeMappings) {
        try {
            final boolean exists =
                    getSearchClient().indices().exists(ExistsRequest.of(index -> index.index(indexName))).value();
            if (Boolean.FALSE.equals(exists)) {
                final CreateIndexResponse response = getSearchClient().indices()
                        .create(index -> index.index(indexName).withJson(new StringReader(attributeMappings)));
                return response.acknowledged();
            } else {
                return true;
            }
        } catch (Exception e) {
            System.err.println(INDEX_CREATION_ERROR + e.getMessage());
            return false;
        }
    }

    @SneakyThrows
    public void bulkIndex(final String indexName, final List<edu.isr.versevibe.dto.SongDocument> documents) {
        final List<BulkOperation> operations = new ArrayList<>();
        for (SongDocument document : documents) {
            final String id = UUID.randomUUID().toString();
            final BulkOperation operation =
                    BulkOperation.of(op -> op.index(idx -> idx.index(indexName).id(id).document(document)));
            operations.add(operation);
            this.indexedDocumentsCount++;
        }
        executeBulkRequest(operations);
        System.out.println("Indexed " + this.indexedDocumentsCount + " documents");
    }


    @SneakyThrows
    private void executeBulkRequest(List<BulkOperation> operations) {
        BulkRequest bulkRequest = BulkRequest.of(req -> req.operations(operations));
        BulkResponse response = getSearchClient().bulk(bulkRequest);
        if (response.errors()) {
            System.out.println("Bulk indexing had errors");
            response.items().forEach(item -> {
                if (item.error() != null) {
                    System.out.println("Error indexing document " + item.id() + ": " + item.error().reason());
                }
            });
        }
    }

    @Override
    public void deleteIndexIfExists(String indexName) throws IOException {
        final boolean exists = searchClient.indices().exists(e -> e.index(indexName)).value();
        if (exists) {
            boolean ack = deleteIndex(indexName);
            if (ack) {
                System.out.println("Deleted index " + indexName);
            }
        }
    }

    private boolean deleteIndex(String indexName) throws IOException {
        final DeleteIndexResponse response = searchClient.indices().delete(d -> d.index(indexName));
        return response.acknowledged();
    }

    @SneakyThrows
    public void indexDocuments() {
        int documentCounter = 0;
        List<SongDocument> songDocumentList = new ArrayList<>(batchSize);
        deleteIndexIfExists(INDEX_NAME);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Indexing song documents");
        String attributeMappings = ElasticSearchConfig.getAttributeMappings(attributeMappingsPath);
        final boolean indexCreated = createIndex(INDEX_NAME, attributeMappings);
        MappingIterator<Map<String, String>> csvIterator = null;
        try {
            csvIterator = IOUtils.initReader(filePath);
            if (Boolean.TRUE.equals(indexCreated)) {
                while (csvIterator.hasNext()) {
                    if (documentCounter >= batchSize) {
                        bulkIndex(INDEX_NAME, songDocumentList);
                        documentCounter = 0;
                        songDocumentList = new ArrayList<>();
                    }
                    final Map<String, String> csvRecord = csvIterator.next();
                    final SongDocument songDocument = CSVUtils.populateSongDTO(csvRecord);
                    songDocumentList.add(songDocument);
                    documentCounter++;
                }
                if (Boolean.FALSE.equals(songDocumentList.isEmpty())) {
                    bulkIndex(INDEX_NAME, songDocumentList);
                }
            } else {
                System.out.println(INDEX_CREATION_ERROR);
            }
        } catch (Exception e) {
            System.err.println(INDEX_CREATION_ERROR + e.getMessage());
            throw e;
        }
        finally {
            if (Objects.nonNull(csvIterator)) {
                csvIterator.close();
            }
        }
    }
}
