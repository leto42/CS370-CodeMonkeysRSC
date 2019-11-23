package com.example.cs370_codemonkeysrsc.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cs370_codemonkeysrsc.model.YouTubeModel;
import com.example.cs370_codemonkeysrsc.utility.YouTubeParser;

import java.util.List;


public class VideoSearchAsyncTask extends AsyncTask<String, Void, List<YouTubeModel>> {

// AsyncTask is a templated (generic) class: AsyncTask<PARAMS, PROGRESS, RESULT>

// PARAMS is the datatype that we pass to execute()
//      a String representing a query to search for Videos that use it
//      (from results of Deezer API)
// PROGRESS is the datatype we use to report updates
// RESULT is the datatype that we send back on completion of this task
//      a List of YouTubeModel (List<YouTubeModel>) that represents all the results for the video search


    // instance of a VideoListener (concrete implementation)
    private VideoListener listener;

    // setter
    public void setListener(VideoListener listener) {
        this.listener = listener;
    }


    @Override
    protected List<YouTubeModel> doInBackground(String... params) {
        // runs on a background thread, not blocking main

       String searchTerm = params[0];  // what we're searching for
        Log.d("VideoSearchAsyncTask", "doInBackground: " + searchTerm);

        // get the json response from YouTube Data API
        String responseJson = YouTubeAPI.searchVideos(searchTerm);
        // can't go further until the search comes back with results


        // success? :)
        if (responseJson != null) {
            // get useful data from the response
            return YouTubeParser.getMatches(responseJson);
        }
        // no success :(
        return null;
    }

    @Override
    protected void onPostExecute(List<YouTubeModel> YouTubeModels) {
        // happens after doInBackground, and runs on main thread
        super.onPostExecute(YouTubeModels);
        Log.d("SearchAsyncTask", "onPostExecute: " + YouTubeModels);

        listener.onVideoSearchCallback(YouTubeModels);
    }

    public interface VideoListener {
        void onVideoSearchCallback(List<YouTubeModel> YouTubeModels);
    }
}
