package com.example.testbot.bot;

public enum Commands {
    START("Старт"),
    ALMATY("Алматы"),
    OSKEMEN("Усть-Каменогорск"),
    URZHAR("Урджар"),
    DOWNLOAD("download");

    private String value;

    Commands(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
