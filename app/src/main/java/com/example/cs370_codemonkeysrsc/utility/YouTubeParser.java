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
            Log.d(TAG, "Begin to parse JSON");
            // a single JSONObject representing the whole response
            JSONObject response = new JSONObject(json);

            // get item array and object
            JSONObject itemObject = response.getJSONArray("items").getJSONObject(0);
            JSONObject idObject = itemObject.getJSONObject("id");
            String VideoID = idObject.getString("videoId");

            Log.d(TAG, "Finished parsing JSON");
            Log.d(TAG, "videoId: " + VideoID);
            YouTubeModel youtubeModel = new YouTubeModel();
            youtubeModel.setVideoID(VideoID);
            modelList.add(youtubeModel);

        } catch (JSONException e) {
            Log.e(TAG, "getMatches: error parsing JSON", e);
        }

        return modelList;
    }
}
