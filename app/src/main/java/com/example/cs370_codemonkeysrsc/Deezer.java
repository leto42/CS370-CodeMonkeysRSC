package com.example.cs370_codemonkeysrsc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Deezer extends AsyncTask<Integer, Void, String> {

    private DeezerListener listener;
    public void setListener(DeezerListener listener) { this.listener = listener;}
    private static final String TAG = "Deezer";

    private static final String baseGenreApiUrl = "https://api.deezer.com/genre/";
    private static final String baseArtistApiUrl = "https://api.deezer.com/artist/";
    private static final String apiKey = "e88f496338d3154b4d61dec08f107edf";
    private static final String appId = "381624";

    @Override
    protected String doInBackground(Integer... ints) {
        //int genreID = YoutubePageActivity.GenreID;
        //Log.d("Deezer", "GenreID in Deezer: " + genreID); // See if GenreID made it to Deezer Activity **CURRENTLY NOT WORKING**
        int genreID = ints[0]; //ORIGINAL
        int artistID = getArtistID(genreID); // intermediate function that gets artist ID from list generated from genre ID.
        // need String artistName
        String songTitle = getSongTitle(artistID); // final function that gets song title from list of songs generated from artist ID.

        return songTitle; // returns the song title to onPostExecute which returns string to YoutubePageActivity for Youtube API search.
    }

    @Override
    protected void onPostExecute(String songTitle) {
        super.onPostExecute(songTitle);
        if (this.listener != null) {
            this.listener.onDeezerCallback(songTitle);
        }
    }

    private int getArtistID(int genreID) {
        String selectedGenre = String.valueOf(genreID); // genreID -> int to String for URL.
        int artistID = 0; // temp value.
        int numMatches; // = 0;
        String responseJSON = null; // temp null value before HttpClient.*

        String fullGenreApiUrl = baseGenreApiUrl + selectedGenre + "/artists/"; // = https://api.deezer.com/genre/132/artists/

        HttpUrl.Builder urlBuilder = HttpUrl.parse(fullGenreApiUrl).newBuilder();
        urlBuilder.addQueryParameter("_app_key", apiKey);
        urlBuilder.addQueryParameter("_app_id", appId);
        //urlBuilder.addQueryParameter("q", selectedGenre); // q? -> from Lab 4
        String url = urlBuilder.build().toString(); // response URL


        Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient();
        try {
            // ask the server for a response
            Response response = client.newCall(request).execute();
            // `response` also contains metadata: success/fail, travel time, etc.
            // only need the body of the result (as a string)
            responseJSON = response.body().string(); // ** call responseJSON
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<Artist> artistList = new ArrayList<>();

        if (responseJSON != null) { // url -> responseJSON
            try {
                // convert the raw string into a Java JSONObject
                JSONObject response = new JSONObject(responseJSON);
                JSONArray artists = response.getJSONArray("artists");

                for (int i = 0; i < artists.length(); i++){ // for all returned artist objects
                    JSONObject arrayElement = artists.getJSONObject(i); // grab artist object at index i

                    Artist artist = new Artist();
                    artist.setId(arrayElement.getInt("id")); // sets artist ID.
                    artist.setName(arrayElement.getString("name")); // sets artist name.
                    artist.setPicture(arrayElement.getString("picture")); // sets artist picture.
                    artist.setPicture_small(arrayElement.getString("picture_small")); // sets artist small picture.
                    artist.setPicture_medium(arrayElement.getString("picture_medium")); // sets artist medium picture.
                    artist.setPicture_big(arrayElement.getString("picture_big")); // set artist big picture.
                    artist.setPicture_xl(arrayElement.getString("picture_xl")); // set artist xl picture.
                    artist.setRadio(arrayElement.getBoolean("radio")); // set artist radio.
                    artist.setTracklist(arrayElement.getString("tracklist")); // set artist tracklist.
                    artist.setType(arrayElement.getString("type")); // set artist type.
                    artistList.add(artist); // adds artist[i] to artistList.
                }

                // grab total count of returned artists*
                numMatches = artistList.size(); // number of returned artists now in artistList.

                Random rand = new Random(); // set random number.
                int selectedArtist = rand.nextInt(numMatches); // get val between first and last artist in list.
                //String artistNumString = String.valueOf(selectedArtist); // selected artist index -> string
                Artist chosen = artistList.get(selectedArtist); // now "chosen" artist is randomly chosen artist from original artistList.
                artistID = chosen.getId(); // artist getter method gets artist ID and assigns it to artistID variable intended to be returned.

            } catch (JSONException e) {
                Log.e(TAG, "getArtistId: error parsing JSON", e);
            }
        }
        return artistID; // returns the chosen artist's ID value. (integer currently)
    }

    private String getSongTitle(int artistID) {
        String selectedArtist = String.valueOf(artistID); // int artistID -> type: string for URL.
        String songTitle = "temp"; // temp value
        int numSongs;
        String responseJSON = null; // temp null value before HttpClient.*

        // This creates the get url that requests the top 10 songs of the selected artist based on artistID.
        String fullArtistApiUrl = baseArtistApiUrl + selectedArtist + "/top?limit=10"; // = https://api.deezer.com/artist/384236/top?limit=10

        HttpUrl.Builder urlBuilder = HttpUrl.parse(fullArtistApiUrl).newBuilder();
        urlBuilder.addQueryParameter("_app_key", apiKey);
        urlBuilder.addQueryParameter("_app_id", appId);
        //urlBuilder.addQueryParameter("q", selectedArtist); // q? -> from Lab 4
        String url = urlBuilder.build().toString(); // response URL

        Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient();
        try {
            // ask the server for a response
            Response response = client.newCall(request).execute();
            // `response` also contains metadata: success/fail, travel time, etc.
            // only need the body of the result (as a string)
            responseJSON = response.body().string(); // ** call responseJSON
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Song> songList = new ArrayList<>();

        // want this: https://api.deezer.com/artist/384236/top?limit=10
        if (responseJSON != null) {
            try {
                // convert the raw string into a Java JSONObject
                JSONObject response = new JSONObject(responseJSON);
                JSONArray songs = response.getJSONArray("songs");

                for (int i = 0; i < songs.length(); i++) { // for all returned song objects in array
                    JSONObject arrayElement = songs.getJSONObject(i); // grab song object at index i

                    Song song = new Song();
                    song.setId(arrayElement.getInt("id")); // set song ID.
                    song.setReadable(arrayElement.getBoolean("readable")); // set song readable.
                    song.setTitle(arrayElement.getString("title")); // set song title.
                    song.setTitle_short(arrayElement.getString("title_short")); // set song short title.
                    song.setTitle_version(arrayElement.getString("title_version")); // set song title version
                    song.setDuration(arrayElement.getInt("duration")); // set song duration
                    song.setRank(arrayElement.getInt("rank")); // set song rank
                    song.setExplicit_lyrics(arrayElement.getBoolean("explicit_lyrics")); // set song explicit lyrics
                    song.setExplicit_content_lyrics(arrayElement.getInt("explicit_content_lyrics")); // set song explicit content lyrics
                    song.setExplicit_content_cover(arrayElement.getInt("explicit_content_cover")); // set song explicit content cover
                    song.setPreview(arrayElement.getString("preview")); // set song preview
                    song.setType(arrayElement.getString("type")); // set song type
                    songList.add(song); // adds song[i] to songList.
                }

                // grab total count of returned songs*
                numSongs = songList.size(); // number of returned songs now in songList.

                Random rand = new Random(); // set random number.
                int selectedSong = rand.nextInt(numSongs); // get val between first and last song in list. (inclusive)
                Song chosen = songList.get(selectedSong); // now "chosen" song is randomly chosen song from original songList.
                songTitle = chosen.getTitle(); // song getter method gets song title and assigns it to songTitle variable intended to be returned.
            }
            catch (JSONException e) {
                Log.e(TAG, "getSongTitle: error parsing JSON", e);
            }
        }
        return songTitle; // returns the chosen song's title. (string)
    }

    public interface DeezerListener {
        void onDeezerCallback(String songTitle);
    }
}

/* from getArtistID (first version)
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseGenreApiUrl).newBuilder();
        urlBuilder.addQueryParameter("_app_key", apiKey);
        urlBuilder.addQueryParameter("_app_id", appId);
        urlBuilder.addQueryParameter("q", selectedGenre); // q? -> from Lab 4
        String url = urlBuilder.build().toString(); // response URL
 */

/* Artists in pop (https://api.deezer.com/genre/132/artists):
{
  "data": [
    {
      "id": "384236",
      "name": "Ed Sheeran",
      "picture": "https://api.deezer.com/artist/384236/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/2a03fcb312d1fe3825f81a94c5d70741/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/2a03fcb312d1fe3825f81a94c5d70741/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/2a03fcb312d1fe3825f81a94c5d70741/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/2a03fcb312d1fe3825f81a94c5d70741/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/384236/top?limit=50",
      "type": "artist"
    },
    {
      "id": "9822974",
      "name": "Xxxtentacion",
      "picture": "https://api.deezer.com/artist/9822974/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/62e6edfaf5461eeb5b7310903229607a/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/62e6edfaf5461eeb5b7310903229607a/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/62e6edfaf5461eeb5b7310903229607a/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/62e6edfaf5461eeb5b7310903229607a/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/9822974/top?limit=50",
      "type": "artist"
    },
    {
      "id": "1562681",
      "name": "Ariana Grande",
      "picture": "https://api.deezer.com/artist/1562681/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/0719cb42b0b06111594604402ba085a5/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/0719cb42b0b06111594604402ba085a5/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/0719cb42b0b06111594604402ba085a5/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/0719cb42b0b06111594604402ba085a5/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/1562681/top?limit=50",
      "type": "artist"
    },
    {
      "id": "9236850",
      "name": "Camila Cabello",
      "picture": "https://api.deezer.com/artist/9236850/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/7545c1cf7251e889585191fb5ace2da3/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/7545c1cf7251e889585191fb5ace2da3/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/7545c1cf7251e889585191fb5ace2da3/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/7545c1cf7251e889585191fb5ace2da3/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/9236850/top?limit=50",
      "type": "artist"
    },
    {
      "id": "564",
      "name": "Rihanna",
      "picture": "https://api.deezer.com/artist/564/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/7d514d87a45a59d6094e028d750f3039/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/7d514d87a45a59d6094e028d750f3039/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/7d514d87a45a59d6094e028d750f3039/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/7d514d87a45a59d6094e028d750f3039/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/564/top?limit=50",
      "type": "artist"
    },
    {
      "id": "1188",
      "name": "Maroon 5",
      "picture": "https://api.deezer.com/artist/1188/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/459dfa4c1f2d710a7e97e70c15bb12a0/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/459dfa4c1f2d710a7e97e70c15bb12a0/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/459dfa4c1f2d710a7e97e70c15bb12a0/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/459dfa4c1f2d710a7e97e70c15bb12a0/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/1188/top?limit=50",
      "type": "artist"
    },
    {
      "id": "5962948",
      "name": "Shawn Mendes",
      "picture": "https://api.deezer.com/artist/5962948/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/92d39e0c63920be6f045377334d69ac8/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/92d39e0c63920be6f045377334d69ac8/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/92d39e0c63920be6f045377334d69ac8/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/92d39e0c63920be6f045377334d69ac8/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/5962948/top?limit=50",
      "type": "artist"
    },
    {
      "id": "12246",
      "name": "Taylor Swift",
      "picture": "https://api.deezer.com/artist/12246/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/254e356010a50dd4dababc4b50856a55/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/254e356010a50dd4dababc4b50856a55/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/254e356010a50dd4dababc4b50856a55/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/254e356010a50dd4dababc4b50856a55/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/12246/top?limit=50",
      "type": "artist"
    },
    {
      "id": "259",
      "name": "Michael Jackson",
      "picture": "https://api.deezer.com/artist/259/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/86b13342a65ffe06fa151ce353a7b278/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/86b13342a65ffe06fa151ce353a7b278/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/86b13342a65ffe06fa151ce353a7b278/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/86b13342a65ffe06fa151ce353a7b278/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/259/top?limit=50",
      "type": "artist"
    },
    {
      "id": "145",
      "name": "Beyoncé",
      "picture": "https://api.deezer.com/artist/145/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/129da72a39febe3247483ae2270739dd/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/129da72a39febe3247483ae2270739dd/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/129da72a39febe3247483ae2270739dd/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/129da72a39febe3247483ae2270739dd/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/145/top?limit=50",
      "type": "artist"
    },
    {
      "id": "292185",
      "name": "Selena Gomez",
      "picture": "https://api.deezer.com/artist/292185/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/f2c427eadb27156eaec66132c4d1b06f/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/f2c427eadb27156eaec66132c4d1b06f/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/f2c427eadb27156eaec66132c4d1b06f/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/f2c427eadb27156eaec66132c4d1b06f/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/292185/top?limit=50",
      "type": "artist"
    },
    {
      "id": "1020109",
      "name": "The Black Eyed Peas",
      "picture": "https://api.deezer.com/artist/1020109/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/98269f898d9a37dabb2b73e04efc1405/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/98269f898d9a37dabb2b73e04efc1405/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/98269f898d9a37dabb2b73e04efc1405/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/98269f898d9a37dabb2b73e04efc1405/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/1020109/top?limit=50",
      "type": "artist"
    },
    {
      "id": "288166",
      "name": "Justin Bieber",
      "picture": "https://api.deezer.com/artist/288166/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/3e2ffac57dbc16621978cd175aa3878b/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/3e2ffac57dbc16621978cd175aa3878b/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/3e2ffac57dbc16621978cd175aa3878b/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/3e2ffac57dbc16621978cd175aa3878b/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/288166/top?limit=50",
      "type": "artist"
    },
    {
      "id": "5200025",
      "name": "Lizzo",
      "picture": "https://api.deezer.com/artist/5200025/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/1d267492794be64043b69d139ad2bf0f/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/1d267492794be64043b69d139ad2bf0f/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/1d267492794be64043b69d139ad2bf0f/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/1d267492794be64043b69d139ad2bf0f/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/5200025/top?limit=50",
      "type": "artist"
    },
    {
      "id": "429675",
      "name": "Bruno Mars",
      "picture": "https://api.deezer.com/artist/429675/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/25d38ffc3fd6a36ac71a08ff6ed90fa2/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/25d38ffc3fd6a36ac71a08ff6ed90fa2/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/25d38ffc3fd6a36ac71a08ff6ed90fa2/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/25d38ffc3fd6a36ac71a08ff6ed90fa2/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/429675/top?limit=50",
      "type": "artist"
    },
    {
      "id": "1097709",
      "name": "Sam Smith",
      "picture": "https://api.deezer.com/artist/1097709/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/d42e724e123eb5c97865bc117c936d38/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/d42e724e123eb5c97865bc117c936d38/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/d42e724e123eb5c97865bc117c936d38/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/d42e724e123eb5c97865bc117c936d38/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/1097709/top?limit=50",
      "type": "artist"
    },
    {
      "id": "8706544",
      "name": "Dua Lipa",
      "picture": "https://api.deezer.com/artist/8706544/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/e6a04d735093a46dcc8be197681d1199/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/e6a04d735093a46dcc8be197681d1199/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/e6a04d735093a46dcc8be197681d1199/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/e6a04d735093a46dcc8be197681d1199/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/8706544/top?limit=50",
      "type": "artist"
    },
    {
      "id": "4038501",
      "name": "Piso 21",
      "picture": "https://api.deezer.com/artist/4038501/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/b3a816277ad579dee4409166fb56bebd/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/b3a816277ad579dee4409166fb56bebd/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/b3a816277ad579dee4409166fb56bebd/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/b3a816277ad579dee4409166fb56bebd/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/4038501/top?limit=50",
      "type": "artist"
    },
    {
      "id": "5266156",
      "name": "Sebastian Yatra",
      "picture": "https://api.deezer.com/artist/5266156/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/e46c5bee56d1ca6fed8242f7a691adb5/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/e46c5bee56d1ca6fed8242f7a691adb5/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/e46c5bee56d1ca6fed8242f7a691adb5/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/e46c5bee56d1ca6fed8242f7a691adb5/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/5266156/top?limit=50",
      "type": "artist"
    },
    {
      "id": "58568762",
      "name": "Camilo",
      "picture": "https://api.deezer.com/artist/58568762/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/98ee32149f8dd52bef31c3137ac87f00/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/98ee32149f8dd52bef31c3137ac87f00/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/98ee32149f8dd52bef31c3137ac87f00/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/98ee32149f8dd52bef31c3137ac87f00/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/58568762/top?limit=50",
      "type": "artist"
    },
    {
      "id": "294464",
      "name": "Pedro Capó",
      "picture": "https://api.deezer.com/artist/294464/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/669b88b80000654ed36b963603835666/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/669b88b80000654ed36b963603835666/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/669b88b80000654ed36b963603835666/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/669b88b80000654ed36b963603835666/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/294464/top?limit=50",
      "type": "artist"
    },
    {
      "id": "160",
      "name": "Shakira",
      "picture": "https://api.deezer.com/artist/160/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/866b0be8692efb8619b8f2177511de46/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/866b0be8692efb8619b8f2177511de46/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/866b0be8692efb8619b8f2177511de46/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/866b0be8692efb8619b8f2177511de46/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/160/top?limit=50",
      "type": "artist"
    },
    {
      "id": "75798",
      "name": "Adele",
      "picture": "https://api.deezer.com/artist/75798/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/22c83631d238c4e21800a75a79c54c61/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/22c83631d238c4e21800a75a79c54c61/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/22c83631d238c4e21800a75a79c54c61/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/22c83631d238c4e21800a75a79c54c61/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/75798/top?limit=50",
      "type": "artist"
    },
    {
      "id": "4476",
      "name": "Reik",
      "picture": "https://api.deezer.com/artist/4476/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/0cd77fefa2ac531a63dee2b928cddb02/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/0cd77fefa2ac531a63dee2b928cddb02/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/0cd77fefa2ac531a63dee2b928cddb02/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/0cd77fefa2ac531a63dee2b928cddb02/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/4476/top?limit=50",
      "type": "artist"
    },
    {
      "id": "69925",
      "name": "P!nk",
      "picture": "https://api.deezer.com/artist/69925/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/874ede04c616a86dcaf793aa3283ca63/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/874ede04c616a86dcaf793aa3283ca63/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/874ede04c616a86dcaf793aa3283ca63/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/874ede04c616a86dcaf793aa3283ca63/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/69925/top?limit=50",
      "type": "artist"
    },
    {
      "id": "144227",
      "name": "Katy Perry",
      "picture": "https://api.deezer.com/artist/144227/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/24fe428816bfe09b9a9d6e3da68bf908/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/24fe428816bfe09b9a9d6e3da68bf908/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/24fe428816bfe09b9a9d6e3da68bf908/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/24fe428816bfe09b9a9d6e3da68bf908/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/144227/top?limit=50",
      "type": "artist"
    },
    {
      "id": "1362735",
      "name": "Charlie Puth",
      "picture": "https://api.deezer.com/artist/1362735/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/022e555a35b43f854aaabc7edb296d6e/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/022e555a35b43f854aaabc7edb296d6e/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/022e555a35b43f854aaabc7edb296d6e/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/022e555a35b43f854aaabc7edb296d6e/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/1362735/top?limit=50",
      "type": "artist"
    },
    {
      "id": "1547598",
      "name": "Benny Blanco",
      "picture": "https://api.deezer.com/artist/1547598/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/6a8be9b6c1561e7cfa5f5e773792bb5c/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/6a8be9b6c1561e7cfa5f5e773792bb5c/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/6a8be9b6c1561e7cfa5f5e773792bb5c/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/6a8be9b6c1561e7cfa5f5e773792bb5c/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/1547598/top?limit=50",
      "type": "artist"
    },
    {
      "id": "413",
      "name": "Elton John",
      "picture": "https://api.deezer.com/artist/413/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/86e18a448593a4c28ef0a75d2ad888ec/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/86e18a448593a4c28ef0a75d2ad888ec/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/86e18a448593a4c28ef0a75d2ad888ec/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/86e18a448593a4c28ef0a75d2ad888ec/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/413/top?limit=50",
      "type": "artist"
    },
    {
      "id": "3469",
      "name": "Sia",
      "picture": "https://api.deezer.com/artist/3469/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/636f9751c8c0f987d856d40f12693eb5/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/636f9751c8c0f987d856d40f12693eb5/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/636f9751c8c0f987d856d40f12693eb5/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/636f9751c8c0f987d856d40f12693eb5/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/3469/top?limit=50",
      "type": "artist"
    },
    {
      "id": "6707",
      "name": "Luis Fonsi",
      "picture": "https://api.deezer.com/artist/6707/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/21c903882e293460b4e096dbac44d17b/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/21c903882e293460b4e096dbac44d17b/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/21c903882e293460b4e096dbac44d17b/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/21c903882e293460b4e096dbac44d17b/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/6707/top?limit=50",
      "type": "artist"
    },
    {
      "id": "10189722",
      "name": "Mau y Ricky",
      "picture": "https://api.deezer.com/artist/10189722/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/d59fc82176881a8551c9fb8d4ed2c613/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/d59fc82176881a8551c9fb8d4ed2c613/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/d59fc82176881a8551c9fb8d4ed2c613/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/d59fc82176881a8551c9fb8d4ed2c613/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/10189722/top?limit=50",
      "type": "artist"
    },
    {
      "id": "1878",
      "name": "Prince",
      "picture": "https://api.deezer.com/artist/1878/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/85eec086152fb01d873ccdb0810e2660/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/85eec086152fb01d873ccdb0810e2660/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/85eec086152fb01d873ccdb0810e2660/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/85eec086152fb01d873ccdb0810e2660/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/1878/top?limit=50",
      "type": "artist"
    },
    {
      "id": "6123",
      "name": "Luis Miguel",
      "picture": "https://api.deezer.com/artist/6123/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/3ca0f91336e087b11fe0d51fec0cdc79/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/3ca0f91336e087b11fe0d51fec0cdc79/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/3ca0f91336e087b11fe0d51fec0cdc79/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/3ca0f91336e087b11fe0d51fec0cdc79/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/6123/top?limit=50",
      "type": "artist"
    },
    {
      "id": "5484712",
      "name": "Bazzi",
      "picture": "https://api.deezer.com/artist/5484712/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/caa9f35182af03bd3fd6c27585f2234b/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/caa9f35182af03bd3fd6c27585f2234b/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/caa9f35182af03bd3fd6c27585f2234b/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/caa9f35182af03bd3fd6c27585f2234b/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/5484712/top?limit=50",
      "type": "artist"
    },
    {
      "id": "776",
      "name": "Pitbull",
      "picture": "https://api.deezer.com/artist/776/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/f755bcad19948129e670598a52d3f874/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/f755bcad19948129e670598a52d3f874/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/f755bcad19948129e670598a52d3f874/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/f755bcad19948129e670598a52d3f874/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/776/top?limit=50",
      "type": "artist"
    },
    {
      "id": "249599",
      "name": "Jason Derulo",
      "picture": "https://api.deezer.com/artist/249599/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/955a40075bfd5d5501f266bd0cc79381/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/955a40075bfd5d5501f266bd0cc79381/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/955a40075bfd5d5501f266bd0cc79381/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/955a40075bfd5d5501f266bd0cc79381/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/249599/top?limit=50",
      "type": "artist"
    },
    {
      "id": "4492087",
      "name": "Bebe Rexha",
      "picture": "https://api.deezer.com/artist/4492087/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/c32f7d77f4b027384f92b5825cd7e7d6/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/c32f7d77f4b027384f92b5825cd7e7d6/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/c32f7d77f4b027384f92b5825cd7e7d6/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/c32f7d77f4b027384f92b5825cd7e7d6/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/4492087/top?limit=50",
      "type": "artist"
    },
    {
      "id": "65",
      "name": "Mariah Carey",
      "picture": "https://api.deezer.com/artist/65/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/be9cdac3dc07bd13a1903b973cc2a8ac/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/be9cdac3dc07bd13a1903b973cc2a8ac/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/be9cdac3dc07bd13a1903b973cc2a8ac/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/be9cdac3dc07bd13a1903b973cc2a8ac/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/65/top?limit=50",
      "type": "artist"
    },
    {
      "id": "9096522",
      "name": "Trevor Daniel",
      "picture": "https://api.deezer.com/artist/9096522/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist//56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist//250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist//500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist//1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/9096522/top?limit=50",
      "type": "artist"
    },
    {
      "id": "4938",
      "name": "Ricardo Arjona",
      "picture": "https://api.deezer.com/artist/4938/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/8ca4c0010df3251ec46279db5b9da3f7/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/8ca4c0010df3251ec46279db5b9da3f7/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/8ca4c0010df3251ec46279db5b9da3f7/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/8ca4c0010df3251ec46279db5b9da3f7/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/4938/top?limit=50",
      "type": "artist"
    },
    {
      "id": "125372",
      "name": "DaVido",
      "picture": "https://api.deezer.com/artist/125372/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/712f71a2a10dc7c9e0f32dde114ed6b6/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/712f71a2a10dc7c9e0f32dde114ed6b6/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/712f71a2a10dc7c9e0f32dde114ed6b6/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/712f71a2a10dc7c9e0f32dde114ed6b6/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/125372/top?limit=50",
      "type": "artist"
    },
    {
      "id": "311820",
      "name": "Ellie Goulding",
      "picture": "https://api.deezer.com/artist/311820/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/5c824c50f3d8befaac53c64abc7416f8/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/5c824c50f3d8befaac53c64abc7416f8/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/5c824c50f3d8befaac53c64abc7416f8/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/5c824c50f3d8befaac53c64abc7416f8/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/311820/top?limit=50",
      "type": "artist"
    },
    {
      "id": "15888",
      "name": "Jonas Brothers",
      "picture": "https://api.deezer.com/artist/15888/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/b44f810dbc8e290925514360ffb21882/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/b44f810dbc8e290925514360ffb21882/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/b44f810dbc8e290925514360ffb21882/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/b44f810dbc8e290925514360ffb21882/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/15888/top?limit=50",
      "type": "artist"
    },
    {
      "id": "45",
      "name": "Jennifer Lopez",
      "picture": "https://api.deezer.com/artist/45/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/2fa1c95948cf111e38aed58b64b49556/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/2fa1c95948cf111e38aed58b64b49556/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/2fa1c95948cf111e38aed58b64b49556/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/2fa1c95948cf111e38aed58b64b49556/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/45/top?limit=50",
      "type": "artist"
    },
    {
      "id": "4698748",
      "name": "Becky G",
      "picture": "https://api.deezer.com/artist/4698748/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/501dbf7039c422d3a28a11f95397a4fc/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/501dbf7039c422d3a28a11f95397a4fc/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/501dbf7039c422d3a28a11f95397a4fc/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/501dbf7039c422d3a28a11f95397a4fc/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/4698748/top?limit=50",
      "type": "artist"
    },
    {
      "id": "290",
      "name": "Madonna",
      "picture": "https://api.deezer.com/artist/290/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/c5f0fb78a2d918126211b2d0b33ff36a/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/c5f0fb78a2d918126211b2d0b33ff36a/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/c5f0fb78a2d918126211b2d0b33ff36a/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/c5f0fb78a2d918126211b2d0b33ff36a/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/290/top?limit=50",
      "type": "artist"
    },
    {
      "id": "116",
      "name": "Christina Aguilera",
      "picture": "https://api.deezer.com/artist/116/image",
      "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/4d4ff99030e08a33f1d1dca470ea3faf/56x56-000000-80-0-0.jpg",
      "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/4d4ff99030e08a33f1d1dca470ea3faf/250x250-000000-80-0-0.jpg",
      "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/4d4ff99030e08a33f1d1dca470ea3faf/500x500-000000-80-0-0.jpg",
      "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/4d4ff99030e08a33f1d1dca470ea3faf/1000x1000-000000-80-0-0.jpg",
      "radio": true,
      "tracklist": "https://api.deezer.com/artist/116/top?limit=50",
      "type": "artist"
    }
  ]
}
 */