//package org.example.PlaylistGenerator;
//
//import org.example.model.Song;
//import org.example.repository.MongoDBSongRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.factory.Nd4j;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class MongoDBSongRepositoryTest {
//
//    private MongoDBSongRepository songRepository;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize the actual MongoDB repository
//        songRepository = new MongoDBSongRepository();
//    }
//
//    @AfterEach
//    void tearDown() {
//        // Clean up the test data from the database (optional)
//        // Could implement a clearSongs() method to delete test data if needed
//    }
//
//    @Test
//    void testSaveAndRetrieveSong() {
//        // Create a test song with a dummy embedding
//        INDArray embedding = Nd4j.create(new float[]{1.0f, 2.0f, 3.0f});
//        Song song = new Song("Test Song", "Test Artist", "Test Lyrics", embedding);
//
//        // Save the song to the repository
//        songRepository.saveSong(song);
//
//        // Retrieve the songs from the database (in this case, just to validate save works)
//        List<Song> songs = songRepository.findSongsByEmbedding(embedding, 10);
//
//        // Assert that the song was saved and retrieved correctly
//        assertNotNull(songs, "Song list should not be null");
//        assertFalse(songs.isEmpty(), "Song list should not be empty");
//        assertEquals("Test Song", songs.get(0).getTitle(), "The title should match");
//    }
//
//    @Test
//    void testFindClosestSongs() {
//        // Create some dummy embeddings and songs
//        INDArray promptEmbedding = Nd4j.create(new float[]{1.0f, 2.0f, 3.0f});
//        Song song1 = new Song("Song 1", "Artist 1", "Lyrics 1", promptEmbedding);
//        Song song2 = new Song("Song 2", "Artist 2", "Lyrics 2", Nd4j.create(new float[]{1.1f, 2.1f, 3.1f}));
//
//        // Save both songs
//        songRepository.saveSong(song1);
//        songRepository.saveSong(song2);
//
//        // Retrieve the closest songs based on the prompt embedding
//        List<Song> closestSongs = songRepository.findSongsByEmbedding(promptEmbedding, 2);
//
//        // Assert that the closest songs are found
//        assertNotNull(closestSongs, "Closest songs should not be null");
//        assertEquals(2, closestSongs.size(), "Should retrieve two songs");
//        assertEquals("Song 1", closestSongs.get(0).getTitle(), "First song should be the closest match");
//    }
//}
