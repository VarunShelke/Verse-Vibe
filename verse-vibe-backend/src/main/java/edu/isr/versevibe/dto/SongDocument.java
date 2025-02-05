package edu.isr.versevibe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class SongDocument {
    private Long id;
    private String title;
    private String tag;
    private List<String> artists;
    private String year;
    private String lyrics;
    private String language;
    private Date generatedAt;

    @Override
    public String toString() {
        return "SongDocument{" +
                "title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", artist='" + artists + '\'' +
                ", year='" + year + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", id='" + id + '\'' +
                ", language='" + language + '\'' +
                ", generatedAt=" + generatedAt +
                '}';
    }
}