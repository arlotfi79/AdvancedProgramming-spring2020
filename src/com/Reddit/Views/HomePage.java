package com.Reddit.Views;

import com.Reddit.ConsoleColor;
import com.Reddit.Models.PostManagement.Post;
import com.Reddit.Models.PostManagement.PostContent;
import com.Reddit.Models.UserManagement.*;

import java.util.List;
import java.util.Scanner;

public class HomePage {

    private Scanner scanner = new Scanner(System.in);

    private User currentUser;
    private HomePage(User currentUser) {
        this.currentUser = currentUser;
    }


    //************************************************** Methods *****************************************************//

    // $ Creating HomePage Without Using The Constructor
    public static HomePage createHomePage(User user){
        return new HomePage(user);
    }

    // 1. Select a Post
    private void selectPost() throws UserNotFound, RoomNotFound, UserIsAlreadyARoomMember, LimitedAccess, UserAlreadyExists, UserIsNotARoomMember, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        List<Post> posts = Post.sortHomePagePosts(currentUser);
        System.out.print("\tChoose a Post: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        Post post = posts.get(index-1);

        PostPage postPage = PostPage.createPostPage(currentUser, post);
        postPage.run();
    }

    // 2. Open UserPage
    private void openUserPage() throws UserNotFound, RoomNotFound, UserAlreadyExists, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {

        System.out.print("Enter the Username Of the User You Want to search for: ");
        String username = scanner.nextLine();

        User user = User.searchUserByUsername(username);

        UserPage userPage = UserPage.createUserPage(currentUser, user);
        userPage.run();
    }

    // 3. Open SubRedditPage
    private void openSubRedditPage() throws RoomNotFound, EmptyMessageData, UserAlreadyExists, UserAlreadyHasARelType, EmptyList, UserIsNotARoomMember, UserNotFound, RoomAlreadyExists, UserIsAlreadyARoomMember, LimitedAccess {

        System.out.print("\tEnter the SubReddit ID Of the Room You Want to search for: ");
        String roomId = scanner.nextLine();

        Room subReddit = SubReddit.findSubReddit(roomId);

        SubRedditPage subRedditPage = SubRedditPage.createSubRedditPage(currentUser,subReddit);
        subRedditPage.run();
    }
    // 4. Open Messaging Page
    private void openMessagingPage() throws EmptyMessageData, UserAlreadyExists, UserAlreadyHasARelType, RoomNotFound, EmptyList, UserIsNotARoomMember, UserNotFound, UserIsAlreadyARoomMember, LimitedAccess, RoomAlreadyExists {
        MessagingPage messagingPage = MessagingPage.createMessagingPage(currentUser);
        messagingPage.run();
    }

    // 5. Open MyPage
    private void openMyPage() throws UserIsAlreadyARoomMember, RoomNotFound, UserAlreadyExists, LimitedAccess, UserIsNotARoomMember, UserNotFound, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        MyPage myPage = MyPage.createMyPage(currentUser);
        myPage.run();
    }

    // 6. Logout
    private void logOut() throws UserNotFound, UserAlreadyExists, RoomNotFound, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        SignUpLoginPage signUpLoginPage = SignUpLoginPage.createSignUpLoginPage();
        signUpLoginPage.run();
    }

    // 7. Night Mood
    private void turnOnOffNightMood() {
        if (SignUpLoginPage.nightMood){
            System.out.println(ConsoleColor.YELLOW_BACKGROUND+ ConsoleColor.BLACK_BOLD_BRIGHT);
        }
        else {
            System.out.println(ConsoleColor.BLACK_BACKGROUND_BRIGHT + ConsoleColor.WHITE_BOLD_BRIGHT);
        }
    }

    // 8. HomePage Menu Method
    public void run() throws UserNotFound, UserAlreadyExists, RoomNotFound, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {

        System.out.println("************************************************************************************** HomePage ****************************************************************************************************");
        System.out.println("\t\uF05D HomePage For: " + currentUser.getName() + " " + currentUser.getLastName() + "\n");
        Post.showHomePagePosts(currentUser);
        System.out.print("\t\uF0001. Select Post\t\uE8D42. Open a User's Page\t3. Open a SubReddit's Page\t\uF0314. Messenger\t5.Open MyPage\t\uF3B16. Log Out");
        if (!SignUpLoginPage.nightMood){
            SignUpLoginPage.nightMood = true;
            System.out.println("\t\uF1267. Turn On Light Mood");
        }
        else{
            SignUpLoginPage.nightMood = false;
            System.out.println("\t\uF1277. Turn On Night Mood");
        }

        System.out.print("\tChoose a Number :");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                selectPost();
                break;
            case 2:
                openUserPage();
                break;
            case 3:
                openSubRedditPage();
                break;
            case 4:
                openMessagingPage();
                break;
            case 5:
                openMyPage();
                break;
            case 6:
                logOut();
                break;
            case 7:
                turnOnOffNightMood();
                run();
                break;
            default:
                run();
                break;
        }

    }
}