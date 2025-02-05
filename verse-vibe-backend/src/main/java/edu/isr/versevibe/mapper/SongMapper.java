package edu.isr.versevibe.mapper;

import edu.isr.versevibe.dto.Song;
import edu.isr.versevibe.dto.result.Album;
import edu.isr.versevibe.dto.result.Artist;
import edu.isr.versevibe.dto.result.SongResult;
import edu.isr.versevibe.dto.spotify.SpotifyArtist;
import edu.isr.versevibe.dto.spotify.SpotifySearchResponse;
import edu.isr.versevibe.dto.spotify.SpotifyTrack;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SongMapper {
    public static SongResult toSongResult(final Song song, final SpotifySearchResponse spotifySearchResponse) {
        final SongResult songResult = new SongResult();
        songResult.setDocId(song.getDocId());
        songResult.setElasticSearchId(song.getElasticsearchId());

        if (spotifySearchResponse.getTracks().getItems().isEmpty()) {
            songResult.setName(song.getTitle());
            populateAlbumData(songResult, song);
            populateArtistData(songResult, song);
        } else {
            SpotifyTrack track = spotifySearchResponse.getTracks().getItems().get(0);
            songResult.setSpotifyId(track.getId());
            songResult.setName(track.getName());
            songResult.setSpotifyUrl(track.getExternalUrls().getSpotify());
            songResult.setExplicit(track.getExplicit());

            populateAlbumData(songResult, track);
            populateArtistData(songResult, track);
            populateDuration(songResult, track.getDurationInMilliseconds());
        }
        return songResult;
    }

    private static void populateDuration(final SongResult songResult, final Integer durationInMilliseconds) {
        final Duration duration = Duration.ofMillis(durationInMilliseconds);
        int minutes = duration.toMinutesPart();
        int seconds = duration.toSecondsPart();
        final String durationString = String.format("%02d:%02d", minutes, seconds);
        songResult.setDuration(durationString);
    }

    private static void populateArtistData(final SongResult songResult, final SpotifyTrack track) {
        final List<Artist> artists = new ArrayList<>();
        for (SpotifyArtist spotifyArtist : track.getArtists()) {
            final Artist artist = new Artist();
            artist.setSpotifyId(spotifyArtist.getId());
            artist.setName(spotifyArtist.getName());
            artist.setSpotifyUrl(spotifyArtist.getExternalUrls().getSpotify());
            artists.add(artist);
        }
        songResult.setArtists(artists);
    }

    private static void populateArtistData(final SongResult songResult, final Song song) {
        List<Artist> artists = IntStream.range(0, song.getArtists().size()).mapToObj(i -> {
            Artist artist = new Artist();
            artist.setSpotifyId(String.valueOf(i + 1));
            artist.setName(song.getArtists().get(i));
            return artist;
        }).toList();

        songResult.setArtists(artists);
    }

    private static void populateAlbumData(final SongResult songResult, final SpotifyTrack track) {
        final Album album = new Album();
        album.setSpotifyId(track.getAlbum().getId());
        album.setName(track.getAlbum().getName());
        album.setSpotifyUrl(track.getAlbum().getExternalUrls().getSpotify());
        album.setReleaseDate(track.getAlbum().getReleaseDate().substring(0, 4));
        album.setImages(track.getAlbum().getImages());
        songResult.setAlbum(album);
    }

    private static void populateAlbumData(final SongResult songResult, Song song) {
        final Album album = new Album();
        album.setName("Unknown Album");
        album.setReleaseDate(song.getYear());
        songResult.setAlbum(album);
    }
}
