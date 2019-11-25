package com.example.cs370_codemonkeysrsc.utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.cs370_codemonkeysrsc.model.YouTubeModel;

public class YouTubeParser {
    // TAG is used in logging, to help trace where output comes from
    private static final String TAG = "YouTubeParser";

    /**
     * Returns a list of recipes that match a search term
     * @param json String json representing the server's response
     * @return list of items (as YouTubeModels)
     */
    public static List<YouTubeModel> getMatches(String json) {

        List<YouTubeModel> modelList = new ArrayList<>();

        try {
            /* Want videoId
        "video"
            "items" : [
              {
              "kind": "youtube#searchResult",
              "etag": etag,
              "id": {
                "kind": string,
                "videoId": string,
                "channelId": string,
                "playlistId": string
              }
                ...
                ]
             */
            // a single JSONObject representing the whole response
            JSONObject response = new JSONObject(json);
            // get entire JSON response
            JSONArray videoArray = response.getJSONArray("videos");
            JSONObject firstObject = videoArray.getJSONObject(0);
            // get item array and object
            JSONArray itemArray = firstObject.getJSONArray("items");
            JSONObject itemObject = itemArray.getJSONObject(0);
            // get id array and object
            JSONArray idArray = itemObject.getJSONArray("id");
            JSONObject idObject = idArray.getJSONObject(0);
            // extract videoId
            String VideoID = idObject.getString("videoId");

            YouTubeModel youtubeModel = new YouTubeModel();
            youtubeModel.setVideoID(VideoID);
            modelList.add(youtubeModel);

        } catch (JSONException e) {
            Log.e(TAG, "getMatches: error parsing JSON", e);
        }

        return modelList;
    }
}
