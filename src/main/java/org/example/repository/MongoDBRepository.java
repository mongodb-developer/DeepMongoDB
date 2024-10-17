package org.example.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.model.Song;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MongoDBRepository {

    private final MongoCollection<Document> songCollection;

    public MongoDBRepository(MongoClient mongoClient,
                             @Value("${mongodb.database}") String databaseName,
                             @Value("${mongodb.collection}") String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.songCollection = database.getCollection(collectionName);
    }

    /**
     * Store the song embedding along with song details
     *
     * @param lyrics The song lyrics
     * @param title The song title
     * @param artist The artist name
     * @param embedding The vector embedding for the song
     */
    public void storeEmbedding(String lyrics, String title, String artist, List<Double> embedding) {
        Document songDocument = new Document()
                .append("title", title)
                .append("artist", artist)
                .append("lyrics", lyrics)
                .append("embedding", embedding);
        try {
            songCollection.insertOne(songDocument); // Store the song document in MongoDB
        } catch (Exception e) {
            throw new RuntimeException("Failed to store song embedding", e);
        }
    }

    /**
     * Fetch similar songs using MongoDB's $vectorSearch aggregation based on the playlist embedding.
     *
     * @param playlistEmbedding The playlist title embedding used as the query vector
     * @return List of Song objects representing similar songs
     */
    public List<Song> getSimilarSongs(List<Double> playlistEmbedding) {
        List<Document> similarSongsDocs = new ArrayList<>();

        // Perform the vector search using the generated embedding
        String indexName = "vector_index";
        int numCandidates = 150;
        int limit = 10;

        List<Document> pipeline = Arrays.asList(
                new Document("$vectorSearch",
                        new Document("index", indexName)
                                .append("path", "embedding")
                                .append("queryVector", playlistEmbedding)
                                .append("numCandidates", numCandidates)
                                .append("limit", limit)
                ),
                new Document("$limit", limit)
        );

        try {
            songCollection.aggregate(pipeline).into(similarSongsDocs);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve similar songs", e);
        }

        // Convert the retrieved documents into Song objects
        List<Song> similarSongs = new ArrayList<>();
        for (Document doc : similarSongsDocs) {
            Song song = new Song(
                    doc.getString("title"),
                    doc.getString("artist"),
                    doc.getString("lyrics"),
                    doc.getList("embedding", Double.class)  // Extract the embedding as a List<Double>
            );
            similarSongs.add(song);
        }

        return similarSongs;
    }
}
