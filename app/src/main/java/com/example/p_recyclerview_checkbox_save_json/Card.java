package com.example.p_recyclerview_checkbox_save_json;

public class Card {
    String title;
    boolean isSeleted;

    public Card(String title, boolean isSeleted) {
        this.title = title;
        this.isSeleted = isSeleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }
}
