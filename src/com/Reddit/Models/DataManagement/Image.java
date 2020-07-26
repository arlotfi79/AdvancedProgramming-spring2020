package com.Reddit.Models.DataManagement;

import com.Reddit.Models.MessageManagement.MessageData;

public class Image implements MessageData {

    private String imageUrl;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String show() {
        return imageUrl;
    }
}
