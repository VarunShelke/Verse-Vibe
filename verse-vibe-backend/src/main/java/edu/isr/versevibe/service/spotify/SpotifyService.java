package edu.isr.versevibe.service.spotify;

import edu.isr.versevibe.dto.spotify.SpotifySearchResponse;

public interface SpotifyService {
    SpotifySearchResponse searchTrack(String trackName, String artistName);
}
