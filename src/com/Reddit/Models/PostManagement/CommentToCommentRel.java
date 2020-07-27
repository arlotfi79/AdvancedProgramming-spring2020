package com.Reddit.Models.PostManagement;

public class CommentToCommentRel {

    private Comment comment;
    private CommentToCommentRelType commentToCommentRelType;

    public CommentToCommentRel(Comment comment, CommentToCommentRelType commentToCommentRelType) {
        this.comment = comment;
        this.commentToCommentRelType = commentToCommentRelType;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public CommentToCommentRelType getCommentToCommentRelType() {
        return commentToCommentRelType;
    }

    public void setCommentToCommentRelType(CommentToCommentRelType commentToCommentRelType) {
        this.commentToCommentRelType = commentToCommentRelType;
    }
}
