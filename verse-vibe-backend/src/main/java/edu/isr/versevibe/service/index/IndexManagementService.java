package edu.isr.versevibe.service.index;

import edu.isr.versevibe.dto.SongDocument;

import java.io.IOException;
import java.util.List;

public interface IndexManagementService {
    boolean createIndex(final String indexName, final String attributeMappings);

    void bulkIndex(final String indexName, final List<SongDocument> documents);

    void deleteIndexIfExists(final String indexName) throws IOException;

    void indexDocuments();
}
