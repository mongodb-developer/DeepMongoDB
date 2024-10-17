package org.example.model;

import java.util.List;

public class Song {
    private String title;
    private String artist;
    private String lyrics;
    private List<Double> embedding;

    public Song(String title, String artist, String lyrics, List<Double> embedding) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        this.embedding = embedding;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }
}
