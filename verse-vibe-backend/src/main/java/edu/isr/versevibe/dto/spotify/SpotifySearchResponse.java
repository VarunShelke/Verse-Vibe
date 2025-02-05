package edu.isr.versevibe.dto.spotify;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpotifySearchResponse {
    private SpotifySearchTracksResponse tracks;
}
