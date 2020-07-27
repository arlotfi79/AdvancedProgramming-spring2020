package com.Reddit.Models.PostManagement;

import com.Reddit.Models.UserManagement.ProfileImage;
import com.Reddit.Models.UserManagement.User;

public class PostInteractionToUser extends User {

    public PostInteractionToUser(String name, String lastName, ProfileImage profileImage, String username, String password, String email) {
        super(name, lastName, profileImage, username, password, email);
    }
}
