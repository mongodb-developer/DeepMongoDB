package org.example.controller;
import org.example.SongLyricsProcessor;
import org.example.model.Playlist;
import org.example.service.PlaylistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaylistController {

    PlaylistService playlistService;
    SongLyricsProcessor songLyricsProcessor;

    public PlaylistController(PlaylistService playlistService, SongLyricsProcessor songLyricsProcessor) {
        this.playlistService = playlistService;
        this.songLyricsProcessor = songLyricsProcessor;
    }

    @GetMapping("/newPlaylist")
    public Playlist newPlaylist(@RequestParam String playlistName) {
        return playlistService.generatePlaylist(playlistName);
    }

    @GetMapping("/loadSampleData")
    public void loadSampleData(@RequestParam String fileName) throws Exception {
        songLyricsProcessor.processAndStoreLyrics(fileName);
    }
}
