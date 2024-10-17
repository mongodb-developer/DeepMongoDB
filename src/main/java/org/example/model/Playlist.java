package org.example.model;

import java.util.List;

public class Playlist {

    private String playlistName;
    private List<Song> songs;

    public Playlist() {
    }

    public Playlist(String playlistName, List<Song> songs) {
        this.playlistName = playlistName;
        this.songs = songs;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
