//package org.example.PlaylistGenerator;
//
//import org.example.service.EmbeddingService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.nd4j.linalg.api.ndarray.INDArray;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class EmbeddingServiceTest {
//
//    private EmbeddingService embeddingService;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        // Load the GloVe 300d model from the resources folder
//        embeddingService = new EmbeddingService();
//    }
//
//    @Test
//    public void testGetEmbeddingForWord() {
//        // Test embedding for a single word ("twinkle")
//        String word = "twinkle";
//        INDArray wordEmbedding = embeddingService.getEmbeddingForWord(word);
//        assertNotNull(wordEmbedding, "Word embedding for 'twinkle' should not be null");
//        assertEquals(300, wordEmbedding.length(), "Word embedding should have 300 dimensions");
//    }
//
//    @Test
//    public void testGetEmbeddingForText_NurseryRhyme() {
//        // Test embedding for a nursery rhyme
//        String nurseryRhyme = "Twinkle twinkle little star how I wonder what you are";
//
//        // Tokenize and get the embedding for the whole rhyme
//        List<Float> rhymeEmbedding = embeddingService.embedText(nurseryRhyme);
//        assertNotNull(rhymeEmbedding, "Embedding for nursery rhyme should not be null");
//        assertEquals(300, rhymeEmbedding.size(), "The nursery rhyme embedding should have 300 dimensions");
//
//        // Get individual embeddings for some words and check averaging logic
//        INDArray twinkleEmbedding = embeddingService.getEmbeddingForWord("twinkle");
//        INDArray starEmbedding = embeddingService.getEmbeddingForWord("star");
//
//        assertNotNull(twinkleEmbedding, "Word embedding for 'twinkle' should not be null");
//        assertNotNull(starEmbedding, "Word embedding for 'star' should not be null");
//
//        // Ensure that the average embedding is different from single word embeddings
//        assertNotEquals(twinkleEmbedding, rhymeEmbedding, "Rhyme embedding should not be the same as the single word embedding for 'twinkle'");
//        assertNotEquals(starEmbedding, rhymeEmbedding, "Rhyme embedding should not be the same as the single word embedding for 'star'");
//    }
//
//    @Test
//    public void testGetEmbeddingForText_WithOOVWord() {
//        // Test embedding with an out-of-vocabulary (OOV) word in the text
//        String text = "Twinkle twinkle little star zanzibar";
//        List<Float> textEmbedding = embeddingService.embedText(text);
//
//        assertNotNull(textEmbedding, "Embedding for text with OOV word should not be null");
//        assertEquals(300, textEmbedding.size(), "The text embedding should have 300 dimensions");
//    }
//}
