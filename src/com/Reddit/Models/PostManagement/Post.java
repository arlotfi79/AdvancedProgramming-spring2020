package com.Reddit.Models.PostManagement;

import com.Reddit.Models.UserManagement.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Post {

    private PostContent title;
    private PostContent postContent;

    public Post(PostContent title, PostContent postContent) {
        this.title = title;
        this.postContent = postContent;
        this.postInteractions = new ArrayList<>();
    }

    // ******************************************* Connections *******************************************************//

    private List<PostInteraction> postInteractions;

    // ******************************************* Getters & Setters **************************************************//

    public PostContent getTitle() {
        return title;
    }

    public void setTitle(PostContent title) {
        this.title = title;
    }

    public PostContent getPostContent() {
        return postContent;
    }

    public void setPostContent(PostContent postContent) {
        this.postContent = postContent;
    }

    public List<PostInteraction> getPostInteractions() {
        return postInteractions;
    }

    public void setPostInteractions(List<PostInteraction> postInteractions) {
        this.postInteractions = postInteractions;
    }

    //************************************************* Methods *****************************************************//

    // 1. Add Interaction
    public void addInteraction(PostInteraction interaction){
        postInteractions.add(interaction);
    }

    // 2. Show Post OverView
    public void showPostOverView(){

        System.out.println("\tTitle : " + title.show());
        System.out.println("\t\t" + this.countUpVotes() + " UpVote \uF043\t" + this.countComments() + " Comment \uF033 ");
    }

    // 2.1 Show Post With Content
    public void showPostWithContent(){
        System.out.println("\tTitle : " + title.show());
        System.out.println("\tContent: " + postContent.show());
        System.out.println("\t\t" + this.countUpVotes() + " UpVote \uF043\t" + this.countComments() + " Comment \uF033 ");
    }

    // 3. Count Likes
    public int countUpVotes(){

        int count = 0;
        for (PostInteraction interaction: postInteractions){
            if (interaction instanceof Vote){
                ++count;
            }
        }

        return count;
    }

    // 4. Count Comments
    public int countComments(){

        int count = 0;
        for (PostInteraction interaction: postInteractions){
            if (interaction instanceof Comment){
                ++count;
            }
        }

        return count;
    }

    // 5. Sort Posts For HomePage ( User's Or SubReddit Pages That The User Has Followed )
    // Sorted By Like
    public static List<Post> sortHomePagePosts(User currentUser) throws UserNotFound {

        List<Post> homePagePosts = new ArrayList<>(currentUser.getUserToPosts());

        for (Room room : Room.getRoomDataBase()) {
            for (UserToRoomRel userToRoomRel : room.getUserToRoomRels()) {
                if (userToRoomRel.getUser().equals(currentUser)) {
                    if (room instanceof Direct) {
                        User user = User.searchUserByUsername(userToRoomRel.getRoom().getRoomId());
                        homePagePosts.addAll(user.getUserToPosts());
                    }
                    if (room instanceof SubReddit){
                        homePagePosts.addAll(((SubReddit) room).getSubRedditToPosts());
                    }
                }

            }
        }

        homePagePosts.sort(new Comparator<Post>() {
            @Override
            public int compare(Post post1, Post post2) {
                return Integer.compare(post1.countUpVotes(), post2.countUpVotes());
            }
        });

        return homePagePosts;
    }

    // 6. Show Posts For HomePage
    public static void showHomePagePosts(User currentUser) throws UserNotFound {

        List<Post> homePagePosts = sortHomePagePosts(currentUser);

        int postNumber = 1;
        for (Post post : homePagePosts) {
            System.out.print(postNumber + ". ");
            post.showPostOverView();
            System.out.println();
            postNumber++;
        }
    }

    // 7. Sort Posts For The UserPage
    public static List<Post> sortUserPagePosts(User currentUser){

        List<Post> userPagePosts = new ArrayList<>(currentUser.getUserToPosts());

        userPagePosts.sort(new Comparator<Post>() {
            @Override
            public int compare(Post post1, Post post2) {
                return Integer.compare(post1.countUpVotes(), post2.countUpVotes());
            }
        });

        return userPagePosts;
    }

    // 8. Show Posts For The UserPage
    public static void showPostsForUserPage(User currentUser) {

        List<Post> userPagePosts = sortUserPagePosts(currentUser);

        int postNumber = 1;
        for (Post post : userPagePosts) {
            System.out.print(postNumber + ". ");
            post.showPostOverView();
            System.out.println();
            postNumber++;
        }
    }

    // 9. Sort Posts For SubRedditPage
    public static List<Post> sortSubRedditPagePosts(Room subReddit) {

        List<Post> subRedditPagePosts = new ArrayList<>();

        for (Room room : Room.getRoomDataBase()) {
            if (room instanceof SubReddit) {
                if (room.getRoomId().equals(subReddit.getRoomId())) {
                    if (room.getRoomId().equals(subReddit.getRoomId())) {
                        subRedditPagePosts.addAll(((SubReddit) room).getSubRedditToPosts());
                    }
                }
            }
        }

            subRedditPagePosts.sort(new Comparator<Post>() {
                @Override
                public int compare(Post post1, Post post2) {
                    return Integer.compare(post1.countUpVotes(), post2.countUpVotes());
                }
            });

            return subRedditPagePosts;
        }

    // 10. Show Posts For SubRedditPagePosts
    public static void showSubRedditPagePosts(Room subReddit){

        List<Post> subRedditPagePosts = sortSubRedditPagePosts(subReddit);

        int postNumber = 1;
        for (Post post : subRedditPagePosts) {
            System.out.print(postNumber + ". ");
            post.showPostOverView();
            System.out.println();
            postNumber++;
        }
    }

}
