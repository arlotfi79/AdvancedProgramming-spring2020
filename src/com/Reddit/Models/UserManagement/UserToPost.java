package com.Reddit.Models.UserManagement;

import com.Reddit.Models.PostManagement.Post;
import com.Reddit.Models.PostManagement.PostContent;

public class UserToPost extends Post {

    public UserToPost(PostContent title, PostContent postContent) {
        super(title, postContent);
    }
}
