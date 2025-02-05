package edu.isr.versevibe.dto.result;

import edu.isr.versevibe.dto.spotify.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Album extends SongMetadata {
    private String releaseDate;
    private List<Image> images;
}
