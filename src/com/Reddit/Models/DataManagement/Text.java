package com.Reddit.Models.DataManagement;

import com.Reddit.Models.MessageManagement.MessageData;

public class Text implements MessageData {

    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public String show() {
        return text;
    }
}
