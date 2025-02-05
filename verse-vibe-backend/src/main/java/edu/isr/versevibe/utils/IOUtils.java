package edu.isr.versevibe.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.isr.versevibe.constants.Constants.FILE_NOT_FOUND_MSG;


public class IOUtils {

    public static MappingIterator<Map<String, String>> initReader(final String filePath) throws IOException {
        try {
            final CsvMapper mapper = new CsvMapper();
            final CsvSchema schema = CsvSchema.emptySchema().withHeader();
            return mapper.readerFor(Map.class).with(schema).readValues(new File(filePath));
        } catch (IOException exception) {
            final Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, FILE_NOT_FOUND_MSG, exception);
            throw exception;
        }
    }

}
