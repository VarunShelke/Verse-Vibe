package edu.isr.versevibe.config;

import edu.isr.versevibe.service.index.IndexManagementService;
import edu.isr.versevibe.service.index.SongSearchService;
import edu.isr.versevibe.service.index.impl.DefaultIndexManagementService;
import edu.isr.versevibe.service.index.impl.DefaultSongSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VerseVibeConfig {
    @Bean
    public IndexManagementService indexManagementService() {
        return new DefaultIndexManagementService(ElasticSearchConfig.createClient());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SongSearchService searchService() {
        return new DefaultSongSearchService(ElasticSearchConfig.createClient());
    }
}
