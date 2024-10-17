# AI-Powered Playlist Generator

### Craft Custom Playlists with Deeplearning4j and MongoDB Atlas Vector Search

This project allows you to generate custom playlists based on a user-defined prompt, like “sad girl wistful Friday evening” or “midwestern emo puppy love.” By using Deeplearning4j to embed song lyrics into vectors and MongoDB Atlas's Vector Search to find similar songs, you can create playlists that match the semantic vibe of your chosen playlist name.

This repo demonstrates how to:
- Embed song lyrics using GloVe pre-trained embeddings with Deeplearning4j.
- Store and query embeddings in MongoDB Atlas using Vector Search.
- Generate playlists based on a descriptive prompt that captures the "vibe."

## Features
- **Custom Playlist Names**: Create playlists based on any prompt, and retrieve songs that match the semantic meaning of the prompt.
- **Deeplearning4j Integration**: Use Deeplearning4j to process and embed song lyrics into vectors.
- **MongoDB Atlas Vector Search**: Perform vector searches in MongoDB to find songs with similar lyrical content.

## How It Works
1. **Lyrics Embedding**: We use Deeplearning4j with GloVe embeddings to convert song lyrics into vectors.
2. **Vector Storage**: These embeddings are stored in MongoDB Atlas in a `songs` collection.
3. **Playlist Generation**: When a playlist name is provided, it is also embedded into a vector, and MongoDB’s Vector Search feature is used to find songs with similar embeddings.

## Prerequisites
Before you start, ensure you have the following:

- Java 11+ (Java 21 recommended)
- Maven 3.9.6+
- MongoDB Atlas with a deployed cluster (M0+)
- GloVe embeddings (`glove.840B.300d.txt`) from [GloVe: Global Vectors for Word Representation](https://nlp.stanford.edu/projects/glove/)
- Kaggle Genius Lyrics dataset (`song_lyrics.csv`) from [Kaggle](https://www.kaggle.com/datasets/carlosgdcj/genius-song-lyrics-with-language-information)

## Installation
1. Clone this repository:
    ```bash
    git clone https://github.com/yourusername/ai-powered-playlist-generator.git
    cd ai-powered-playlist-generator
    ```

2. Add the necessary resources:
    - Download the [GloVe embeddings](https://nlp.stanford.edu/projects/glove/) (`glove.840B.300d.txt`) and place it in the `src/main/resources` folder.
    - Download the [Genius Song Lyrics dataset](https://www.kaggle.com/datasets/carlosgdcj/genius-song-lyrics-with-language-information) and place `song_lyrics.csv` in the `src/main/resources` folder.

3. Set up MongoDB Atlas:
    - Create a MongoDB cluster.
    - Configure your `.env` or `application.properties` with the MongoDB URI and database/collection information:
      ```properties
      mongodb.uri=mongodb+srv://<username>:<password>@<cluster-url>/?retryWrites=true&w=majority
      mongodb.database=music
      mongodb.collection=songs
      ```

4. Run the project:
    ```bash
    mvn spring-boot:run
    ```

5. Load the song data into MongoDB by sending a request to the `/loadSampleData` endpoint:
    ```bash
    curl -X GET "http://localhost:8080/loadSampleData?fileName=song_lyrics.csv"
    ```

6. Generate a playlist by sending a request to `/newPlaylist` with your desired playlist name:
    ```bash
    curl -X GET "http://localhost:8080/newPlaylist?playlistName=sad%20girl%20wistful%20Friday%20evening"
    ```

## Project Structure
- **`src/main/java`**: Contains all Java classes for embedding, MongoDB interaction, and playlist generation.
- **`src/main/resources`**: Stores external resources like GloVe embeddings and song lyrics.
- **`pom.xml`**: Contains the necessary dependencies for Deeplearning4j, MongoDB, and other libraries.

## Tech Stack
- **Java**: Backend logic
- **Spring Boot**: REST API for playlist generation
- **Deeplearning4j**: Lyrics embedding using GloVe
- **MongoDB Atlas**: Vector Search for finding similar songs
- **Maven**: Dependency management

## Limitations
- The current implementation uses a pre-trained GloVe model, so results might not be as precise as a custom model trained on song lyrics.
- We are not using audio data or user listening history, which would improve the playlist recommendations further.

## Future Enhancements
- **Custom Model Training**: Fine-tune a model on song lyrics for better embedding accuracy.
- **Audio Data Integration**: Use audio features to enhance playlist generation.
- **User Personalization**: Incorporate user preferences and listening habits.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
