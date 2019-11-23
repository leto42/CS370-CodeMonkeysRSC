package com.example.cs370_codemonkeysrsc.model;

public class YouTubeModel {

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    private String videoID;
}

// sample of JSON object

/*
"items" : {
  {
  "kind": "youtube#searchResult",
  "etag": etag,

  Using this section for videoId

  "id": {
    "kind": string,
    "videoId": string,
    "channelId": string,
    "playlistId": string
  },


  "snippet": {
    "publishedAt": datetime,
    "channelId": string,
    "title": string,
    "description": string,
    "thumbnails": {
      (key): {
        "url": string,
        "width": unsigned integer,
        "height": unsigned integer
      }
    },
    "channelTitle": string,
    "liveBroadcastContent": string
  }
}
 */
