package edu.isr.versevibe.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SpotifyTrack {
    private String id;
    private String name;
    private Integer popularity;
    private SpotifyAlbum album;
    private List<SpotifyArtist> artists;
    private Boolean explicit;

    @JsonProperty("duration_ms")
    private Integer durationInMilliseconds;

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
}
