package com.Reddit.Models.DataManagement;

import com.Reddit.Models.MessageManagement.MessageData;

public class File implements MessageData {

    private String link;

    public File(String link) {
        this.link = link;
    }

    @Override
    public String show() {
        return link;
    }
}
