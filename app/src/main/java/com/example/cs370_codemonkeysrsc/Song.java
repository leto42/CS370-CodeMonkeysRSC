package com.example.cs370_codemonkeysrsc;

public class Song {
    private int id;
    private boolean readable;
    private String title;
    private String title_short;
    private String title_version;
    private int duration;
    private int rank;
    private boolean explicit_lyrics;
    private int explicit_content_lyrics;
    private int explicit_content_cover;
    private String preview;
    /*
    public class contributors {
        private int id;
        private String name;
        private String link;
        private String share;
        private String picture;
        private String picture_small;
        private String picture_medium;
        private String picture_big;
        private String picture_XL;
        private boolean radio;
        private String tracklist;
        private String type;
        private String role;

        public int getId() {return id;}
        public String getName() {return name;}
        public String getLink() {return link;}
        public String getShare() {return share;}
        public String getPicture() {return picture;}
        public String getPicture_medium() {return picture_medium;}
        public String getPicture_big() {return picture_big;}
        public String getPicture_XL() {return picture_XL;}
        public boolean isRadio() {return radio;}
        public String getTracklist() {return tracklist;}
        public String getType() {return type;}
        public String getRole() {return role;}
    }
    public class artist {
        private int id;
        private String name;
        private String tracklist;
        private String type;

        public int getId() {return id;}
        public String getName() {return name;}
        public String getTracklist() {return tracklist;}
        public String getType() {return type;}
    }
    public class album {
        private int id;
        private String title;
        private String cover;
        private String cover_small;
        private String cover_medium;
        private String cover_big;
        private String cover_XL;
        private String tracklist;
        private String type;

        public int getId() {return id;}
        public String getTitle() {return title;}
        public String getCover() {return cover;}
        public String getCover_small() {return cover_small;}
        public String getCover_medium() {return cover_medium;}
        public String getCover_big() {return cover_big;}
        public String getCover_XL() {return cover_XL;}
        public String getTracklist() {return tracklist;}
        public String getType() {return type;}
    }
    */

    /* rest of variables from above.
    private int id;
    private boolean readable;
    private String title;
    private String title_short;
    private String title_version;
    private int duration;
    private int rank;
    private boolean explicit_lyrics;
    private int explicit_content_lyrics;
    private int explicit_content_cover;
    private String preview;
    private String type;
     */
    private String type;


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public boolean isReadable() {return readable;}
    public void setReadable(boolean readable) {this.readable = readable;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getTitle_short() {return title_short;}
    public void setTitle_short(String title_short) {this.title_short = title_short;}

    public String getTitle_version() {return title_version;}
    public void setTitle_version(String title_version) {this.title_version = title_version;}

    public int getDuration() {return duration;}
    public void setDuration(int duration) {this.duration = duration;}

    public int getRank() {return rank;}
    public void setRank(int rank) {this.rank = rank;}

    public boolean isExplicit_lyrics() {return explicit_lyrics;}
    public void setExplicit_lyrics(boolean explicit_lyrics) {this.explicit_lyrics = explicit_lyrics;}

    public int getExplicit_content_lyrics() {return explicit_content_lyrics;}
    public void setExplicit_content_lyrics(int explicit_content_lyrics) {this.explicit_content_lyrics = explicit_content_lyrics;}

    public int getExplicit_content_cover() {return explicit_content_cover;}
    public void setExplicit_content_cover(int explicit_content_cover) {this.explicit_content_cover = explicit_content_cover;}

    public String getPreview() {return preview;}
    public void setPreview(String preview) {this.preview = preview;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

}

/*
    Sample song JSON object:
    {
      "id": "139470659",
      "readable": true,
      "title": "Shape of You",
      "title_short": "Shape of You",
      "title_version": "",
      "link": "https://www.deezer.com/track/139470659",
      "duration": "233",
      "rank": "1000000",
      "explicit_lyrics": false,
      "explicit_content_lyrics": 6,
      "explicit_content_cover": 2,
      "preview": "https://cdns-preview-d.dzcdn.net/stream/c-d8f5b81a6243ddfa4c97b9a4c86a82fa-4.mp3",
      "contributors": [
        {
          "id": 384236,
          "name": "Ed Sheeran",
          "link": "https://www.deezer.com/artist/384236",
          "share": "https://www.deezer.com/artist/384236?utm_source=deezer&utm_content=artist-384236&utm_term=0_1574663565&utm_medium=web",
          "picture": "https://api.deezer.com/artist/384236/image",
          "picture_small": "https://e-cdns-images.dzcdn.net/images/artist/2a03fcb312d1fe3825f81a94c5d70741/56x56-000000-80-0-0.jpg",
          "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/2a03fcb312d1fe3825f81a94c5d70741/250x250-000000-80-0-0.jpg",
          "picture_big": "https://e-cdns-images.dzcdn.net/images/artist/2a03fcb312d1fe3825f81a94c5d70741/500x500-000000-80-0-0.jpg",
          "picture_xl": "https://e-cdns-images.dzcdn.net/images/artist/2a03fcb312d1fe3825f81a94c5d70741/1000x1000-000000-80-0-0.jpg",
          "radio": true,
          "tracklist": "https://api.deezer.com/artist/384236/top?limit=50",
          "type": "artist",
          "role": "Main"
        }
      ],
      "artist": {
        "id": "384236",
        "name": "Ed Sheeran",
        "tracklist": "https://api.deezer.com/artist/384236/top?limit=50",
        "type": "artist"
      },
      "album": {
        "id": "14996073",
        "title": "Shape of You",
        "cover": "https://api.deezer.com/album/14996073/image",
        "cover_small": "https://e-cdns-images.dzcdn.net/images/cover/107c2b43f10c249077c1f7618563bb63/56x56-000000-80-0-0.jpg",
        "cover_medium": "https://e-cdns-images.dzcdn.net/images/cover/107c2b43f10c249077c1f7618563bb63/250x250-000000-80-0-0.jpg",
        "cover_big": "https://e-cdns-images.dzcdn.net/images/cover/107c2b43f10c249077c1f7618563bb63/500x500-000000-80-0-0.jpg",
        "cover_xl": "https://e-cdns-images.dzcdn.net/images/cover/107c2b43f10c249077c1f7618563bb63/1000x1000-000000-80-0-0.jpg",
        "tracklist": "https://api.deezer.com/album/14996073/tracks",
        "type": "album"
      },
      "type": "track"
    },
 */