package edu.isr.versevibe.service.index.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import edu.isr.versevibe.dto.Song;
import edu.isr.versevibe.dto.SongDocument;
import edu.isr.versevibe.service.index.SongSearchService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static edu.isr.versevibe.constants.Constants.INDEX_NAME;

@Service("songSearchService")
public class DefaultSongSearchService implements SongSearchService {
    private final ElasticsearchClient elasticsearchClient;

    public DefaultSongSearchService(final ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }


    @Override
    public List<Song> searchAcrossFields(String searchTerm) throws IOException {
        MultiMatchQuery multiMatchQuery = MultiMatchQuery.of(m -> m.fields("title", "lyrics^1.5", "artist").query(searchTerm));
        Query query = Query.of(q -> q.multiMatch(multiMatchQuery));
        SearchRequest searchRequest = SearchRequest.of(s -> s.index(INDEX_NAME).query(query));
        final SearchResponse<SongDocument> searchResponse = elasticsearchClient.search(searchRequest, SongDocument.class);
        return extractHits(searchResponse);
    }

    private List<Song> extractHits(SearchResponse<SongDocument> response) {
        List<Song> songs = new ArrayList<>();
        for (Hit<SongDocument> hit : response.hits().hits()) {
            final SongDocument songDocument = hit.source();
            if (Objects.nonNull(songDocument)) {
                final Song song = new Song();
                song.setElasticsearchId(hit.id());
                song.setDocId(songDocument.getId());
                song.setTitle(songDocument.getTitle());
                song.setArtists(songDocument.getArtists());
                song.setYear(songDocument.getYear());
                songs.add(song);
            }
        }
        return songs;
    }
}
