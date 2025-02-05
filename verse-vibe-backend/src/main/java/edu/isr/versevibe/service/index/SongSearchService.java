package edu.isr.versevibe.service.index;

import edu.isr.versevibe.dto.Song;

import java.io.IOException;
import java.util.List;

public interface SongSearchService {

    List<Song> searchAcrossFields(final String searchTerm) throws IOException;

}
