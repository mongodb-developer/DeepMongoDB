package org.example;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class LyricsEmbeddingGenerator {
    private Word2Vec word2Vec;

    public LyricsEmbeddingGenerator(String preTrainedGlovePath) throws Exception {
        // Load GloVe model from the resources folder
        InputStream gloveStream = getClass().getClassLoader().getResourceAsStream(preTrainedGlovePath);
        if (gloveStream == null) {
            throw new Exception("File not found in resources: " + preTrainedGlovePath);
        }

        // Copy the model to a temporary file to load it
        Path tempFile = Files.createTempFile("glove-model", ".txt");
        Files.copy(gloveStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

        // Load the pre-trained GloVe model using the temporary file
        this.word2Vec = WordVectorSerializer.readWord2VecModel(tempFile.toFile());
    }

    public INDArray getEmbeddingForWord(String word) {
        return word2Vec.getWordVectorMatrix(word);
    }

    public INDArray getEmbeddingForLyrics(List<String> lyricsTokens) {
        INDArray embedding = null;
        for (String token : lyricsTokens) {
            INDArray wordVector = getEmbeddingForWord(token);
            if (wordVector != null) {
                if (embedding == null) {
                    embedding = wordVector;
                } else {
                    embedding.addi(wordVector);  // Sum the word embeddings to get the lyrics embedding
                }
            }
        }
        return embedding != null ? embedding.divi(lyricsTokens.size()) : null;  // Normalize by the number of tokens
    }

    public static void main(String[] args) throws Exception {
        // Load the GloVe model from the resources folder
        String preTrainedGlovePath = "glove.840B.300d.txt";  // File in src/main/resources
        LyricsEmbeddingGenerator embeddingGenerator = new LyricsEmbeddingGenerator(preTrainedGlovePath);

        // Example usage: tokenize lyrics
        List<String> lyricsTokens = List.of("puppy", "love", "cowboy", "sunday", "afternoon");

        // Generate embeddings for the lyrics
        INDArray lyricsEmbedding = embeddingGenerator.getEmbeddingForLyrics(lyricsTokens);

        // Output the vector representation for the lyrics
        if (lyricsEmbedding != null) {
            System.out.println(lyricsEmbedding);  // Print the embedding
        } else {
            System.out.println("No embeddings found for the input lyrics.");
        }
    }
}
