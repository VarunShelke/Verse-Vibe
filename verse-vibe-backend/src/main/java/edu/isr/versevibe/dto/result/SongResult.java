package edu.isr.versevibe.dto.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SongResult extends SongMetadata {
    private Long docId;
    private String elasticSearchId;
    private String name;
    private List<Artist> artists;
    private Album album;
    private String duration;
    private Boolean explicit;
}

