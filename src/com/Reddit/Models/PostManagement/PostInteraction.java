package com.Reddit.Models.PostManagement;

public abstract class PostInteraction {

    public PostInteraction(PostInteractionToUser postInteractionToUser) {
        this.postInteractionToUser = postInteractionToUser;
    }

    // ******************************************* Connections *******************************************************//

    private PostInteractionToUser postInteractionToUser;

    // ******************************************* Getters & Setters **************************************************//

    public PostInteractionToUser getPostInteractionToUser() {
        return postInteractionToUser;
    }

    public void setPostInteractionToUser(PostInteractionToUser postInteractionToUser) {
        this.postInteractionToUser = postInteractionToUser;
    }
}
