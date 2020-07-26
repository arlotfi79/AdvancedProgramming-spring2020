package com.Reddit.Models.PostManagement;

import com.Reddit.Models.UserManagement.LimitedAccess;
import com.Reddit.Models.UserManagement.User;

import java.util.ArrayList;
import java.util.List;

public class Comment extends PostInteraction {

    private PostContent postContent;

    public Comment(PostContent postContent, PostInteractionToUser postInteractionToUser) {
        super(postInteractionToUser);
        this.postContent = postContent;
        this.commentToCommentRels = new ArrayList<>();
    }

    // ******************************************* Connections *******************************************************//

    private List<CommentToCommentRel> commentToCommentRels;

    // ******************************************* Getters & Setters **************************************************//

    public List<CommentToCommentRel> getCommentToCommentRels() {
        return commentToCommentRels;
    }

    public void setCommentToCommentRels(List<CommentToCommentRel> commentToCommentRels) {
        this.commentToCommentRels = commentToCommentRels;
    }

    public PostContent getPostContent() {
        return postContent;
    }

    public void setPostContent(PostContent postContent) {
        this.postContent = postContent;
    }

    //************************************************* Methods *****************************************************//

    // 1. Show Comments
    public void showComments(){
        System.out.print(getPostInteractionToUser().getName() + " " + getPostInteractionToUser().getLastName() + " Says : " + postContent.show());
    }

    // 2. Comment
    public static Comment comment(User user, Post post, PostContent postContent){

        PostInteractionToUser postInteractionToUser = new PostInteractionToUser(user.getName(),user.getLastName(),user.getProfileImage(),user.getUsername(),user.getPassword(),user.getEmail());
        Comment comment = new Comment(postContent,postInteractionToUser);
        post.getPostInteractions().add(comment);

        return comment;
    }

    // 3. Remove Comment
    public static void removeComment(User user, Post post, int commentChoice) throws LimitedAccess {

        int counter = 1;
        for (PostInteraction postInteraction : post.getPostInteractions()){
            if (postInteraction instanceof Comment){
                if (counter == commentChoice){
                    if (!postInteraction.getPostInteractionToUser().getUsername().equals(user.getUsername())){
                        throw new LimitedAccess("Only The User Who Has Committed The Comment Is Able To Delete the Comment");
                    }
                    else if (postInteraction.getPostInteractionToUser().getUsername().equals(user.getUsername())){
                        post.getPostInteractions().remove(postInteraction);
                        return;
                    }
                }
                counter++;
            }
        }
    }

    // 4. UpVote Comment
    public static void upVoteComment(User user, Post post, int commentChoice){

        int counter = 1;
        Comment comment;
        for (PostInteraction postInteraction : post.getPostInteractions()){
            if (postInteraction instanceof Comment) {
                if (counter == commentChoice) {
                    comment = ((Comment) postInteraction);
                    CommentToCommentRel commentToCommentRel = new CommentToCommentRel(comment, CommentToCommentRelType.UP_VOTE);
                    comment.commentToCommentRels.add(commentToCommentRel);
                    return;
                }
                counter++;
            }
        }
    }

    // 5. DownVote Comment
    public static void downVoteComment(User user, Post post, int commentChoice) throws LimitedAccess {

        boolean hasAccess = false;
        int counter = 1;
        Comment comment;
        for (PostInteraction postInteraction : post.getPostInteractions()){
            if (postInteraction instanceof Comment) {
                if (counter == commentChoice) {
                    comment = ((Comment) postInteraction);

                    for (CommentToCommentRel commentToCommentRel : comment.getCommentToCommentRels()) {
                        if (commentToCommentRel.getCommentToCommentRelType().equals(CommentToCommentRelType.UP_VOTE) && commentToCommentRel.getComment().getPostInteractionToUser().getUsername().equals(user.getUsername())) {
                            commentToCommentRel.setCommentToCommentRelType(CommentToCommentRelType.DOWN_VOTE);
                            hasAccess = true;
                            return;
                        }
                    }
                }
                counter++;
            }
        }

        if (!hasAccess)
            throw new LimitedAccess("Only The User Who Has UpVoted This Comment Is Able To DownVote It");
    }

    // 6. Count UpVote Of Comments
    public static int countCommentUpVotes(Post post, int commentChoice){

        int counter = 1;
        int vote = 0;
        for (PostInteraction postInteraction : post.getPostInteractions()){
            if (postInteraction instanceof Comment) {
                if (counter == commentChoice) {
                    Comment comment = (Comment) postInteraction;
                    for (CommentToCommentRel commentToCommentRel : comment.getCommentToCommentRels()){
                        if (commentToCommentRel.getCommentToCommentRelType().equals(CommentToCommentRelType.UP_VOTE)){
                            vote++;
                        }
                    }
                }
                counter++;
            }
        }

        return vote;
    }

}
