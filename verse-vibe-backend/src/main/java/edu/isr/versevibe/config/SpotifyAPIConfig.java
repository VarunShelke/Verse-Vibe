package edu.isr.versevibe.config;

import edu.isr.versevibe.dto.spotify.SpotifyOAuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Component("spotifyAPIConfig")
public class SpotifyAPIConfig {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.api.token.url}")
    private String tokenUrl;

    @Value("${spotify.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate;

    public SpotifyAPIConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getApiToken() {
        if (Objects.isNull(apiToken) || apiToken.isEmpty()) {
            return retrieveAccessToken();
        }

        return apiToken;
    }

    private String retrieveAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Encode client ID and client secret in Base64 using Java's Base64 class
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        // Body parameters for grant type
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        ResponseEntity<SpotifyOAuthResponse> response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                new HttpEntity<>(body, headers), // request entity
                SpotifyOAuthResponse.class
        );

        SpotifyOAuthResponse responseBody = response.getBody();
        if (Objects.nonNull(responseBody)) {
            return responseBody.getAccessToken();
        } else {
            throw new RuntimeException("Failed to retrieve access token from Spotify");
        }
    }
}
