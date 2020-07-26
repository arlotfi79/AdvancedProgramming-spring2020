package com.Reddit.Models.PostManagement;

import com.Reddit.Models.UserManagement.User;

public class Vote extends PostInteraction {

    public Vote(PostInteractionToUser postInteractionToUser) {
        super(postInteractionToUser);
    }

    //************************************************* Methods *****************************************************//

    // 1. Up Vote
    public static Vote upVote(User user,Post post){

        PostInteractionToUser postInteractionToUser = new PostInteractionToUser(user.getName(),user.getLastName(),user.getProfileImage(),user.getUsername(),user.getPassword(),user.getEmail());
        Vote vote = new Vote(postInteractionToUser);
        post.addInteraction(vote);

        return vote;
    }

    // 2. Down Vote
    public static void downVote(User user, Post post){

        for (PostInteraction postInteraction : post.getPostInteractions()){
            if (postInteraction instanceof Vote){
                if (postInteraction.getPostInteractionToUser().getUsername().equals(user.getUsername())){
                    post.getPostInteractions().remove(postInteraction);
                    return;
                }
            }
        }
    }
}
