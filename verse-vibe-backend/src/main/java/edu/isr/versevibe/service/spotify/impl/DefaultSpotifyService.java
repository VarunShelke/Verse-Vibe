package edu.isr.versevibe.service.spotify.impl;

import edu.isr.versevibe.config.SpotifyAPIConfig;
import edu.isr.versevibe.dto.spotify.SpotifySearchResponse;
import edu.isr.versevibe.service.spotify.SpotifyService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service("spotifyService")
public class DefaultSpotifyService implements SpotifyService {
    @Value("${spotify.api-base-url}")
    private String apiBaseUrl;

    @Resource(name = "spotifyAPIConfig")
    private SpotifyAPIConfig spotifyAPIConfig;

    private final RestTemplate restTemplate;

    public DefaultSpotifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public SpotifySearchResponse searchTrack(String trackName, String artistName) {
        String accessToken = spotifyAPIConfig.getApiToken();

        String query = String.format("track:\"%s\"", trackName);
        if (Objects.nonNull(artistName)) {
            query = String.format("\"%s\" artist:\"%s\"", query, artistName);
        }

        String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl + "/search")
                .queryParam("q", query)
                .queryParam("type", "track")
                .queryParam("market", "US")
                .queryParam("limit", 5)
                .queryParam("offset", 0)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifySearchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                SpotifySearchResponse.class
        );
        return response.getBody();
    }
}
