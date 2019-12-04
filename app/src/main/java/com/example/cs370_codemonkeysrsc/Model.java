package com.example.cs370_codemonkeysrsc;

public class Model {
    private String textLine;
    private int genreID;

    public Model(String textLine, int genreID) {
        this.textLine = textLine;
        this.genreID = genreID;
    }

    public String getTextLine() {
        return textLine;
    }

    public int getID() {
        return genreID;
    }

    public void setTextLine(String textLine) {
        this.textLine = textLine;
    }
}
