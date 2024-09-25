package org.example;

import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import java.util.List;

public class LyricsPreprocessor {
    public List<String> tokenizeLyrics(String lyrics) {
        DefaultTokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
        return tokenizerFactory.create(lyrics).getTokens();
    }

    public static void main(String[] args) {
        String lyrics = "I love my puppy on a Sunday afternoon, riding horses in the sunshine!";
        LyricsPreprocessor preprocessor = new LyricsPreprocessor();
        List<String> tokens = preprocessor.tokenizeLyrics(lyrics);

        System.out.println(tokens); // Outputs tokenized words
    }
}
