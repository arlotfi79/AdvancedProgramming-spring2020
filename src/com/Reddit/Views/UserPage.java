package com.Reddit.Views;

import com.Reddit.ConsoleColor;
import com.Reddit.Models.PostManagement.Post;
import com.Reddit.Models.UserManagement.*;

import java.util.List;
import java.util.Scanner;

public class UserPage {

    private Scanner scanner = new Scanner(System.in);

    private User currentUser;
    private User userWeAreVisiting;

    private UserPage(User currentUser, User userWeAreVisiting) {
        this.currentUser = currentUser;
        this.userWeAreVisiting = userWeAreVisiting;
    }

    //************************************************** Methods *****************************************************//

    // $ Creating UserPage Without Using The Constructor
    public static UserPage createUserPage(User currentUser, User userWeAreVisiting) {
        return new UserPage(currentUser, userWeAreVisiting);
    }

    // 1. UnFollow / Follow
    private void followOrUnFollow() throws UserIsAlreadyARoomMember, UserNotFound, LimitedAccess, UserIsNotARoomMember {
        System.out.print("\t1.Follow\t2.UnFollow");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                Room direct = Direct.createDirect(userWeAreVisiting);
                direct.addUser(currentUser, userWeAreVisiting.getUsername());
                break;
            case 2:
                Room direct2 = Direct.createDirect(userWeAreVisiting);
                direct2.removeUser(currentUser, userWeAreVisiting.getUsername());
                break;
            default:
                break;
        }
    }

    // 2. Open Post Page
    private void openPostPage() throws UserIsAlreadyARoomMember, RoomNotFound, LimitedAccess, UserAlreadyExists, UserIsNotARoomMember, UserNotFound, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        List<Post> posts = Post.sortUserPagePosts(currentUser);
        System.out.print("\tChoose a Post: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        Post post = posts.get(index - 1);

        PostPage postPage = PostPage.createPostPage(currentUser, post);
        postPage.run();
    }

    // 3. Back To HomePage
    private void backToHomePage() throws RoomNotFound, UserNotFound, UserAlreadyExists, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        HomePage homePage = HomePage.createHomePage(currentUser);
        homePage.run();
    }

    // 4. Night Mood
    private void turnOnOffNightMood() {
        if (SignUpLoginPage.nightMood) {
            System.out.println(ConsoleColor.YELLOW_BACKGROUND + ConsoleColor.BLACK_BOLD_BRIGHT);
        } else {
            System.out.println(ConsoleColor.BLACK_BACKGROUND_BRIGHT + ConsoleColor.WHITE_BOLD_BRIGHT);
        }
    }

    // 5. UserPage Menu Method
    public void run() throws RoomNotFound, UserNotFound, UserAlreadyExists, LimitedAccess, UserIsAlreadyARoomMember, UserIsNotARoomMember, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {

        System.out.println("************************************************************************************** UserPage ****************************************************************************************************");
        System.out.println("\t\uF05D UserPage For: " + userWeAreVisiting.getName() + " " + userWeAreVisiting.getLastName() + "\n");
        Post.showPostsForUserPage(userWeAreVisiting);
        if (Post.sortUserPagePosts(currentUser).isEmpty()){
            System.out.println("\t\t\t!!!User Has No Posts!!!\n");
        }
        System.out.print("\t1.Follow / UnFollow\t\uF0002. Select Post\t\uF3B13. Back To HomePage");
        if (!SignUpLoginPage.nightMood){
            SignUpLoginPage.nightMood = true;
            System.out.println("\t\uF1274. Turn On Light Mood");
        }
        else{
            SignUpLoginPage.nightMood = false;
            System.out.println("\t\uF1264. Turn On Night Mood");
        }

        System.out.print("\tChoose a Number :");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                followOrUnFollow();
                run();
                break;
            case 2:
                openPostPage();
                break;
            case 3:
                backToHomePage();
                break;
            case 4:
                turnOnOffNightMood();
                run();
                break;
            default:
                run();
                break;
        }

    }
}
