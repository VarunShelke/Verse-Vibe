package edu.isr.versevibe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Song {
    private String elasticsearchId;
    private Long docId;
    private String title;
    private List<String> artists;
    private String year;
}
