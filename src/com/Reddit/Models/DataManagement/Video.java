package com.Reddit.Models.DataManagement;

import com.Reddit.Models.MessageManagement.MessageData;

public class Video implements MessageData {

    private String videoUrl;

    public Video(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String show() {
        return videoUrl;
    }
}
