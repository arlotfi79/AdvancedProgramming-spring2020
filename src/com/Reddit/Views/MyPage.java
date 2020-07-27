package com.Reddit.Views;

import com.Reddit.ConsoleColor;
import com.Reddit.Models.PostManagement.Post;
import com.Reddit.Models.PostManagement.PostContent;
import com.Reddit.Models.UserManagement.*;

import java.util.List;
import java.util.Scanner;

public class MyPage {

    private Scanner scanner = new Scanner(System.in);

    private User currentUser;

    private MyPage(User currentUser) {
        this.currentUser = currentUser;
    }

    //************************************************** Methods *****************************************************//

    // $ Creating MyPage Without Using The Constructor
    public static MyPage createMyPage(User user){
        return new MyPage(user);
    }

    // 1. Create Post
    private void createPost() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\tPost Title : ");
        String title = scanner.nextLine();
        PostContent postTitle = new PostContent(title);
        System.out.print("\tPost Content : ");
        String content = scanner.nextLine();
        PostContent postContent = new PostContent(content);

        currentUser.post(postTitle,postContent);
    }

    // 2. Remove Post
    private void removePost(){
        List<Post> posts = Post.sortUserPagePosts(currentUser);
        System.out.print("\tChoose a Post: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        Post post = posts.get(index - 1);

        currentUser.removePost(post);
    }

    // 3. Open Post Page
    private void openPostPage() throws UserIsAlreadyARoomMember, RoomNotFound, LimitedAccess, UserAlreadyExists, UserIsNotARoomMember, UserNotFound, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        List<Post> posts = Post.sortUserPagePosts(currentUser);
        System.out.print("\tChoose a Post: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        Post post = posts.get(index - 1);

        PostPage postPage = PostPage.createPostPage(currentUser, post);
        postPage.run();
    }

    // 4. Creat SubReddit
    private void createSubReddit() throws RoomAlreadyExists, EmptyMessageData, UserAlreadyExists, UserAlreadyHasARelType, RoomNotFound, EmptyList, UserIsNotARoomMember, UserNotFound, UserIsAlreadyARoomMember, LimitedAccess {
        System.out.print("\tEnter SubReddit Name : ");
        String subRedditName = scanner.nextLine();
        System.out.print("\tEnter SubReddit ID : ");
        String subRedditId = scanner.nextLine();
        System.out.print("\tEnter Description : ");
        String description = scanner.nextLine();
        System.out.print("\tEnter ProfileImage URL : ");
        String path = scanner.nextLine();
        ProfileImage profileImage = new ProfileImage(path);

        Room subReddit = SubReddit.createSubReddit(currentUser,subRedditName,subRedditId,description,profileImage);
        SubRedditPage subRedditPage = SubRedditPage.createSubRedditPage(currentUser,subReddit);
        subRedditPage.run();
    }

    // 6. Back To HomePage
    private void backToHomePage() throws RoomNotFound, UserNotFound, UserAlreadyExists, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        HomePage homePage = HomePage.createHomePage(currentUser);
        homePage.run();
    }

    // 7. Night Mood
    private void turnOnOffNightMood() {
        if (SignUpLoginPage.nightMood) {
            System.out.println(ConsoleColor.YELLOW_BACKGROUND + ConsoleColor.BLACK_BOLD_BRIGHT);
        } else {
            System.out.println(ConsoleColor.BLACK_BACKGROUND_BRIGHT + ConsoleColor.WHITE_BOLD_BRIGHT);
        }
    }

    // 8. MyPage Menu Method
    public void run() throws RoomNotFound, UserNotFound, UserAlreadyExists, LimitedAccess, UserIsAlreadyARoomMember, UserIsNotARoomMember, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {

        System.out.println("************************************************************************************** MyOwnPage ****************************************************************************************************");
        Post.showPostsForUserPage(currentUser);
        if (Post.sortUserPagePosts(currentUser).isEmpty()){
            System.out.println("\t\t\tYou Have No Posts. Try Making One ?!?\n");
        }

        System.out.print("\t\uF0211. Create Post\t2. Remove Post\t\uF0003. Select Post\t4. Create SubReddit\t\uF3B15. Back To HomePage");
        if (!SignUpLoginPage.nightMood){
            SignUpLoginPage.nightMood = true;
            System.out.println("\t\uF1276. Turn On Light Mood");
        }
        else{
            SignUpLoginPage.nightMood = false;
            System.out.println("\t\uF1266. Turn On Night Mood");
        }

        System.out.print("\tChoose a Number :");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                createPost();
                run();
                break;
            case 2:
                removePost();
                run();
                break;
            case 3:
                openPostPage();
                break;
            case 4:
                createSubReddit();
                break;
            case 5:
                backToHomePage();
                break;
            case 6:
                turnOnOffNightMood();
                run();
                break;
            default:
                run();
                break;
        }
    }
}