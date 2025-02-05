package edu.isr.versevibe.controllers;

import edu.isr.versevibe.dto.Song;
import edu.isr.versevibe.dto.result.SongResult;
import edu.isr.versevibe.dto.spotify.SpotifySearchResponse;
import edu.isr.versevibe.mapper.SongMapper;
import edu.isr.versevibe.service.index.SongSearchService;
import edu.isr.versevibe.service.spotify.SpotifyService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class SongSearchController {
    @Resource(name = "songSearchService")
    private SongSearchService songSearchService;

    @Resource(name = "spotifyService")
    private SpotifyService spotifyService;

    @SneakyThrows
    @GetMapping("/search")
    public List<SongResult> getSongInformation(@RequestParam final String searchQuery) {
        List<Song> songs = songSearchService.searchAcrossFields(searchQuery);
        List<SongResult> songResults = new ArrayList<>();
        for (Song song : songs) {
            String artist = song.getArtists().isEmpty() ? null : song.getArtists().get(0);
            SpotifySearchResponse spotifyResponse = spotifyService.searchTrack(song.getTitle(), artist);
            songResults.add(SongMapper.toSongResult(song, spotifyResponse));
        }
        return songResults;
    }
}
