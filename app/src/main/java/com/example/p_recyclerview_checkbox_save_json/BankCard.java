package com.example.p_recyclerview_checkbox_save_json;

public class BankCard {
    private String title;
    private int id;

    public BankCard(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public BankCard() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
