package com.example.cs370_codemonkeysrsc.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YouTubeAPI {

    private static final String baseApiUrl = "https://www.googleapis.com/youtube/v3/search";
    private static final String YouTube_API_KEY = "AIzaSyA3iS0EIDG1zLdVaJ9fpc-oGtKyksksTNQ";
    private static final String appId = "cs370project-259623";

    // Part is always going to be "snippet"
    private static final String part = "snippet";
    // Only concerned with videos
    private static final String type = "video";
    // Only looking for first result - set max to 1.
    private static final int max_results = 1;

    public YouTubeAPI() {
    }

    // Returns API Key used for youtube.
    public static String getYouTube_API_KEY() {
        return YouTube_API_KEY;
    }

    /**
     * Searches the YouTubeApi search database.
     * @param input the query to search in youtube data
     * @return string: json response containing matches
     */
    public static String searchVideos(String input) {

        // piece together a valid url, starting with the base
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseApiUrl).newBuilder();

        // Example URL
        // https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=how%20to%20prounce%20woodchuck&type=video&key=[API KEY HERE]

        urlBuilder.addQueryParameter("part", part);
        urlBuilder.addQueryParameter("maxResults", String.valueOf(max_results));
        urlBuilder.addQueryParameter("q", input);
        urlBuilder.addQueryParameter("type", type);
        urlBuilder.addQueryParameter("key", YouTube_API_KEY);

        // until it's ready to assemble
        String url = urlBuilder.build().toString();

        return getResponse(url);
    }

    private static String getResponse(String url) {
        // form the Request with the url
        Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient();
        try {
            // ask the server for a response
            Response response = client.newCall(request).execute();
            // `response` also contains metadata: success/fail, travel time, etc.
            // only need the body of the result (as a string)
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
