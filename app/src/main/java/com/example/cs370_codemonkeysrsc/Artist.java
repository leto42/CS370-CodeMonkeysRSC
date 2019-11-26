package com.example.cs370_codemonkeysrsc;

public class Artist {
    private int id;
    private String name;
    private String picture;
    private String picture_small;
    private String picture_medium;
    private String picture_big;
    private String picture_xl;
    private boolean radio;
    private String tracklist;
    private String type;

    public int getId() { return id; }
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPicture() {return picture;}
    public void setPicture(String picture) {this.picture = picture;}

    public String getPicture_small() {return picture_small;}
    public void setPicture_small(String picture_small) {this.picture_small = picture_small;}

    public String getPicture_medium() {return picture_medium;}
    public void setPicture_medium(String picture_medium) {this.picture_medium = picture_medium;}

    public String getPicture_big() {return picture_big;}
    public void setPicture_big(String picture_big) {this.picture_big = picture_big;}

    public String getPicture_xl() {return picture_xl;}
    public void setPicture_xl(String picture_xl) {this.picture_xl = picture_xl;}

    public boolean isRadio() {return radio;}
    public void setRadio(boolean radio) {this.radio = radio;}

    public String getTracklist() {return tracklist;}
    public void setTracklist(String tracklist) {this.tracklist = tracklist;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

}

/*
    Sample artist JSON object:
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
    }
 */