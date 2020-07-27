package com.Reddit.Views;

import com.Reddit.Models.PostManagement.*;
import com.Reddit.Models.UserManagement.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostPage {

    private Scanner scanner = new Scanner(System.in);

    private List<Integer> commentChoices = new ArrayList<>(); // Storing Choices That Client Makes While Choosing Comments
    private List<Integer> upVotesSortedWithCommentChoices = new ArrayList<>(); // Storing Votes Of The Choices That Client Made From Last List

    private User currentUser;
    private Post post;

    private PostPage(User currentUser, Post post) {
        this.currentUser = currentUser;
        this.post = post;
    }

    //************************************************* Methods *****************************************************//

    // $ Creating PostPage Without Using The Constructor
    public static PostPage createPostPage(User user, Post post) {
        return new PostPage(user, post);
    }

    // 1 UpVote
    private void upVote(){
        Vote.upVote(currentUser,post);
    }

    // 2 DownVote
    private void downVote(){
        Vote.downVote(currentUser,post);
    }

    // 3. Comment
    private void comment(){
        System.out.print("\tEnter Your Comment : ");
        String content = scanner.nextLine();
        PostContent postContent = new PostContent(content);
        Comment.comment(currentUser,post,postContent);
    }

    // 4. Remove Comment
    private void removeComment() throws LimitedAccess, EmptyList {
        if (emptyCommentList)
            throw new EmptyList("No Comments To Remove");
        Scanner scanner = new Scanner(System.in);
        System.out.print("\tChoose a Comment : ");
        int choice = scanner.nextInt();
        Comment.removeComment(currentUser,post,choice);
    }

    // 5. UpVote a Comment
    private void voteComment() throws LimitedAccess, EmptyList {
        if (emptyCommentList)
            throw new EmptyList("No Comments To UpVote");
        System.out.println("\t1.UpVote\t2.DownVote");
        System.out.print("\tChoose a Number : ");
        int choice = scanner.nextInt();
        System.out.print("\tChoose a Comment : ");
        int commentChoice = scanner.nextInt();  // Will Be Used In The PostPAge Menu Method --> Helper (1)
        commentChoices.add(commentChoice);
        switch (choice){
            case 1:
                Comment.upVoteComment(currentUser,post,commentChoice);
                break;
            case 2:
                Comment.downVoteComment(currentUser,post,commentChoice);
                break;
            default:
                break;
        }
    }

    boolean emptyCommentList = false;
    // 7. Back To HomePage
    private void backToHomePage() throws RoomNotFound, UserNotFound, UserAlreadyExists, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        HomePage homePage = HomePage.createHomePage(currentUser);
        homePage.run();
    }

    // 8. PostPage Menu Method
    public void run() throws UserNotFound, RoomNotFound, UserIsAlreadyARoomMember, LimitedAccess, UserIsNotARoomMember, UserAlreadyExists, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {

        System.out.println("************************************************************************************** PostPage ****************************************************************************************************");
        post.showPostWithContent();

        // Helper 1. Adding Votes Of The Comment Choice To Print UpVotes If CommentChoice Is Equal To The Comment Being Printed
        for (int x : commentChoices){
            upVotesSortedWithCommentChoices.add(Comment.countCommentUpVotes(post,x));
        }

        emptyCommentList = false;
        int count = 1;
        System.out.println("Comments: ");
        for (PostInteraction interaction : post.getPostInteractions()) {
            if (interaction instanceof Comment) {
                System.out.print("\t" + count + ". ");
                ((Comment) interaction).showComments();
                if (!upVotesSortedWithCommentChoices.isEmpty() && count == upVotesSortedWithCommentChoices.get(count-1)){
                    System.out.print("\n\t\t" + upVotesSortedWithCommentChoices.get(count-1) + " UpVotes");
                }
                System.out.println("\n");
            }
            count++;
        }
        if (count == 1) {
            System.out.println("\t\t!!! This Post Has No Comments !!!\n");
            emptyCommentList = true;
        }

        // Helper 2. To Check If The Post Has Comments Or Not
        boolean hasCommented = false;
        for (PostInteraction interaction : post.getPostInteractions()) {
            if (interaction instanceof Comment) {
                if (interaction.getPostInteractionToUser().getUsername().equals(currentUser.getUsername())) {
                    hasCommented = true;
                    break;
                }
            }
        }

        // Helper 3. To Check If The Post Is UpVoted Or Not
        boolean hasVoted = false;
        for (PostInteraction interaction : post.getPostInteractions()) {
            if (interaction instanceof Vote) {
                if (interaction.getPostInteractionToUser().getUsername().equals(currentUser.getUsername())) {
                    hasVoted = true;
                    break;
                }
            }
        }


        if (!hasVoted && !hasCommented) {
            System.out.println("\t1.UpVote Post\t2.Comment\t3.Vote comment\t4.Back to HomePage");
        } else if (!hasVoted && hasCommented) {
            System.out.println("\t1.UpVote Post\t2.Comment\t3.Vote comment\t4.Remove Comment\t5.Back to HomePage");
        } else if (hasVoted && !hasCommented) {
            System.out.println("\t1.DownVote Post\t2.Comment\t3.Vote comment\t4.Back to HomePage");
        } else {
            System.out.println("\t1.DownVote Post\t2.Comment\t3.Vote comment\t4.Remove Comment\t5.Back to HomePage");
        }


        System.out.print("\tChoose Your option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                if (!hasVoted) {
                    upVote();
                } else {
                    downVote();
                }
                run();
                break;
            case 2:
                comment();
                run();
                break;
            case 3:
                voteComment();
                run();
                break;
            case 4:
                if (!hasCommented) {
                    backToHomePage();
                } else {
                    removeComment();
                    run();
                }
                break;
            case 5:
                backToHomePage();
                break;
            default:
                run();
                break;
        }
    }
}
