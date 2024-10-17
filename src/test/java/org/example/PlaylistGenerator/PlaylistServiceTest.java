//package org.example.PlaylistGenerator;
//
//import org.example.model.Song;
//import org.example.repository.MongoDBRepository;
//import org.example.repository.MongoDBSongRepository;
//import org.example.service.EmbeddingService;
//import org.example.service.PlaylistService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.factory.Nd4j;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PlaylistServiceTest {
//
//    private PlaylistService playlistService;
//    private MongoDBRepository songRepository;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        // Initialize the actual services and repository
//        EmbeddingService embeddingService = new EmbeddingService();
//        songRepository = new MongoDBRepository();
//        playlistService = new PlaylistService(embeddingService, songRepository);
//    }
//
//    @Test
//    void testGeneratePlaylist() {
//        // Create dummy embeddings and save some songs in the database
//        INDArray embedding = Nd4j.create(new float[]{1.0f, 2.0f, 3.0f});
//        Song song = new Song("Test Song", "Test Artist", "Test Lyrics", embedding);
//        songRepository.saveSong(song);
//
//        // Generate a playlist based on the prompt
//        String prompt = "puppy love emo cowboy";
//        List<Song> playlist = playlistService.generatePlaylist(prompt);
//
//        // Assert that the playlist contains results
//        assertNotNull(playlist, "Playlist should not be null");
//        assertFalse(playlist.isEmpty(), "Playlist should not be empty");
//        assertEquals(1, playlist.size(), "There should be one song in the playlist");
//        assertEquals("Test Song", playlist.get(0).getTitle(), "The song title should match");
//    }
//}
