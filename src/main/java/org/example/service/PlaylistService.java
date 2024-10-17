package org.example.service;

import org.example.model.Playlist;
import org.example.model.Song;
import org.example.repository.MongoDBRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final EmbeddingService embeddingService;
    private final MongoDBRepository songRepository;

    public PlaylistService(EmbeddingService embeddingService, MongoDBRepository songRepository) {
        this.embeddingService = embeddingService;
        this.songRepository = songRepository;
    }

    /**
     * Generates a playlist based on the given playlist name.
     * It embeds the playlist name, fetches similar songs from the database, and returns the playlist.
     *
     * @param playlistName the name of the playlist.
     * @return Playlist object containing the name and a list of similar songs.
     */
    public Playlist generatePlaylist(String playlistName) {
        // Generate the embedding for the playlist title
        List<Double> playlistEmbedding = embeddingService.embedText(playlistName);

        if (playlistEmbedding == null || playlistEmbedding.isEmpty()) {
            throw new RuntimeException("Failed to generate embedding for playlist: " + playlistName);
        }

        // Query the database to find similar songs
        List<Song> similarSongs = songRepository.getSimilarSongs(playlistEmbedding);

        // Construct and return the Playlist
        return new Playlist(playlistName, similarSongs);
    }
}
