package org.example.service;

import jakarta.annotation.PostConstruct;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class EmbeddingService {

    private final Map<String, INDArray> gloveEmbeddings = new HashMap<>();
    private final DefaultTokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
    private final Set<String> stopWords;  // Set of stop words for filtering

    @Value("${embedding.model.path}")
    private String preTrainedGlovePath;

    // Constructor
    public EmbeddingService() {
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
        stopWords = loadStopWords();
    }

    // PostConstruct to load GloVe model after Spring injects values
    @PostConstruct
    private void init() throws IOException {
        loadGloveModel(preTrainedGlovePath);
    }

    // Load GloVe embeddings from a file
    private void loadGloveModel(String preTrainedGlovePath) throws IOException {
        InputStream gloveStream = getClass().getResourceAsStream("/glove.840B.300d.txt");
        if (gloveStream == null) {
            throw new IOException("GloVe model not found in resources: " + preTrainedGlovePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(gloveStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                String word = split[0];
                float[] vector = new float[300];  // 300-dimensional GloVe vector
                for (int i = 1; i < split.length; i++) {
                    vector[i - 1] = Float.parseFloat(split[i]);
                }
                INDArray wordVector = Nd4j.create(vector);
                gloveEmbeddings.put(word, wordVector);
            }
        }
    }

    // Load stop words (this list can be expanded or replaced with a more comprehensive one)
    private Set<String> loadStopWords() {
        return new HashSet<>(Arrays.asList(
                "the", "a", "an", "and", "is", "in", "at", "of", "to", "for", "with", "on", "by", "this", "that", "it", "i", "you", "they", "we", "but", "or", "as", "if", "when"
        ));
    }

    // Remove content inside square brackets
    private String removeBracketedText(String text) {
        return text.replaceAll("\\[.*?]", "").trim();
    }

    // Tokenize text into words using DefaultTokenizerFactory and remove stop words
    public List<String> tokenizeText(String text) {
        text = removeBracketedText(text);

        Tokenizer tokenizer = tokenizerFactory.create(text);
        List<String> tokens = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (!stopWords.contains(token)) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    // Get the embedding for a word
    public INDArray getEmbeddingForWord(String word) {
        return gloveEmbeddings.getOrDefault(word, null);
    }

    // Generate the embedding for text by averaging word vectors
    public List<Double> getEmbeddingForText(List<String> tokens) {
        INDArray embedding = null;
        int validTokenCount = 0;

        for (String token : tokens) {
            INDArray wordVector = getEmbeddingForWord(token);
            if (wordVector != null) {
                if (embedding == null) {
                    embedding = wordVector.dup();  // Duplicate the word vector
                } else {
                    embedding.addi(wordVector);  // Sum the word embeddings
                }
                validTokenCount++;
            }
        }

        // Average the embeddings and convert to List<Double>
        if (embedding != null && validTokenCount > 0) {
            embedding.divi(validTokenCount);
            return convertINDArrayToDoubleList(embedding);  // Convert INDArray to List<Double>
        }
        return Collections.emptyList();  // Return an empty list if no valid embeddings
    }

    private List<Double> convertINDArrayToDoubleList(INDArray indArray) {
        double[] array = indArray.toDoubleVector();  // Convert to double array
        List<Double> doubleList = new ArrayList<>();
        for (double value : array) {
            doubleList.add(value);
        }
        return doubleList;
    }

    // Generate an embedding for the text
    public List<Double> embedText(String text) {
        List<String> tokens = tokenizeText(text);
        return getEmbeddingForText(tokens);
    }
}
