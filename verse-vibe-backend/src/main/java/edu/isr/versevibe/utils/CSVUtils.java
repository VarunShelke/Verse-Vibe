package edu.isr.versevibe.utils;

import edu.isr.versevibe.dto.SongDocument;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class CSVUtils {

    @SneakyThrows
    public static SongDocument populateSongDTO(final Map<String, String> csvRecord) {
        try {
            final SongDocument songDocument = new SongDocument();

            String id = csvRecord.getOrDefault("id", null);
            if (Objects.nonNull(id)) {
                songDocument.setId(Long.valueOf(id));
            }

            songDocument.setTitle(csvRecord.getOrDefault("title", null));
            songDocument.setTag(csvRecord.getOrDefault("tag", null));
            songDocument.setArtists(new ArrayList<>(
                    populateArtistData(
                            csvRecord.getOrDefault("artist", null),
                            csvRecord.getOrDefault("features", null)
                    )
            ));
            songDocument.setYear(csvRecord.getOrDefault("year", null));
            songDocument.setLyrics(csvRecord.getOrDefault("lyrics", null));
            songDocument.setLanguage(csvRecord.getOrDefault("language_ft", null));
            songDocument.setGeneratedAt(new Date());
            return songDocument;
        } catch (Exception e) {
            System.out.println("Exception while reading CSV.");
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private static Set<String> populateArtistData(final String artistName, final String features) {
        final String cleanedFeaturesValue = cleanFeaturesValue(features);
        final Set<String> currentArtistList = new HashSet<>();
        currentArtistList.add(artistName);
        if (StringUtils.isNotEmpty(cleanedFeaturesValue)) {
            currentArtistList.addAll(List.of(cleanedFeaturesValue.split(",")));
        }
        return currentArtistList;
    }

    private static String cleanFeaturesValue(final String features) {
        String cleanedFeaturesValue = null;
        if (StringUtils.isNotEmpty(features)) {
            cleanedFeaturesValue =
                    features.replace("\"", EMPTY).replace("{", EMPTY).replace("}", EMPTY).replace("\\", EMPTY);
        }
        return cleanedFeaturesValue;
    }
}
