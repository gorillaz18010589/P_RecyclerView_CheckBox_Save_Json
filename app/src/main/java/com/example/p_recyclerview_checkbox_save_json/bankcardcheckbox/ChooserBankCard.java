package com.example.p_recyclerview_checkbox_save_json.bankcardcheckbox;

public class ChooserBankCard {
    private String title;
    private int id;

    public ChooserBankCard(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public ChooserBankCard() {
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
