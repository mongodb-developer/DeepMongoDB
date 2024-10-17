package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.repository.MongoDBRepository;
import org.example.service.EmbeddingService;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

@Component
public class SongLyricsProcessor {

    private final EmbeddingService embeddingService;
    private final MongoDBRepository mongoDBRepository;

    public SongLyricsProcessor(EmbeddingService embeddingService, MongoDBRepository mongoDBRepository) {
        this.embeddingService = embeddingService;
        this.mongoDBRepository = mongoDBRepository;
    }

    public void processAndStoreLyrics(String csvFilePath) throws Exception {
        Reader reader = new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(csvFilePath))
        );

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        CSVParser csvParser = new CSVParser(reader, csvFormat);

        for (CSVRecord record : csvParser) {
            String title = record.get("title");
            String artist = record.get("artist");
            String lyrics = record.get("lyrics");
            String languageCld3 = record.get("language_cld3");
            String languageFt = record.get("language_ft");

            // Check if both language_cld3 and language_ft are 'en' (English)
            if (!"en".equalsIgnoreCase(languageCld3) || !"en".equalsIgnoreCase(languageFt)) {
                continue;
            }

            // Tokenize the lyrics and generate the embedding
            List<Double> lyricsEmbedding = embeddingService.embedText(lyrics);

            // Store the title, artist, lyrics, and embedding in MongoDB
            mongoDBRepository.storeEmbedding(lyrics, title, artist, lyricsEmbedding);
        }

        csvParser.close();
    }
}
