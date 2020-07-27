package com.Reddit.Views;

import com.Reddit.ConsoleColor;
import com.Reddit.Models.PostManagement.Post;
import com.Reddit.Models.PostManagement.PostContent;
import com.Reddit.Models.UserManagement.*;

import java.util.List;
import java.util.Scanner;

public class SubRedditPage {

    private Scanner scanner = new Scanner(System.in);

    private User currentUser;
    private Room subReddit;

    public SubRedditPage(User currentUser, Room subReddit) {
        this.currentUser = currentUser;
        this.subReddit = subReddit;
    }

    //************************************************** Methods *****************************************************//

    // $ Creating SubRedditPage Without Using The Constructor
    public static SubRedditPage createSubRedditPage(User user,Room subReddit){
        return new SubRedditPage(user,subReddit);
    }

    // 1. Select Post
    private void openPostPage() throws UserIsAlreadyARoomMember, RoomNotFound, LimitedAccess, UserAlreadyExists, UserIsNotARoomMember, UserNotFound, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        List<Post> posts = Post.sortSubRedditPagePosts(subReddit);
        System.out.print("\tChoose a Post: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        Post post = posts.get(index - 1);

        PostPage postPage = PostPage.createPostPage(currentUser, post);
        postPage.run();
    }

    // 2. Follow / UnFollow
    private void followOrUnFollow() throws RoomNotFound, UserIsAlreadyARoomMember, UserIsNotARoomMember {
        System.out.println("\t1.Follow\t2.UnFollow");
        System.out.print("\tChoose a Number : ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                ((SubReddit) subReddit).joinSubReddit(currentUser);
                break;
            case 2:
                ((SubReddit) subReddit).leaveSubReddit(currentUser);
                break;
            default:
                break;
        }
    }

    // 3. Back To HomePage
    private void backToHomePage() throws RoomNotFound, UserNotFound, UserAlreadyExists, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        HomePage homePage = HomePage.createHomePage(currentUser);
        homePage.run();
    }

    // 5. Night Mood
    private void turnOnOffNightMood() {
        if (SignUpLoginPage.nightMood) {
            System.out.println(ConsoleColor.YELLOW_BACKGROUND + ConsoleColor.BLACK_BOLD_BRIGHT);
        } else {
            System.out.println(ConsoleColor.BLACK_BACKGROUND_BRIGHT + ConsoleColor.WHITE_BOLD_BRIGHT);
        }
    }

    // 6. Create Post
    private void createPost() throws LimitedAccess {
        System.out.print("\tPost Title : ");
        String title = scanner.nextLine();
        PostContent postTitle = new PostContent(title);
        System.out.print("\tPost Content : ");
        String content = scanner.nextLine();
        PostContent postContent = new PostContent(content);
        ((SubReddit) subReddit).post(currentUser,postTitle,postContent);
    }

    // 7. Remove Post
    private void removePost() throws LimitedAccess {
        List<Post> posts = Post.sortSubRedditPagePosts(subReddit);
        System.out.print("\tChoose a Post: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        Post post = posts.get(index - 1);

        ((SubReddit) subReddit).removePost(currentUser,post);
    }

    // 8. Add user
    private void addUser() throws LimitedAccess, UserNotFound, UserIsAlreadyARoomMember {
        System.out.print("\tEnter The UserName You Want To Add To SubReddit : ");
        String username = scanner.nextLine();
        subReddit.addUser(currentUser,username);
    }

    // 9. Remove user
    private void removeUser() throws UserIsNotARoomMember, UserNotFound, LimitedAccess {
        System.out.print("\tEnter The UserName You Want To Remove From SubReddit : ");
        String username = scanner.nextLine();
        subReddit.removeUser(currentUser,username);
    }

    // 10. Ban User
    private void banUser() throws UserNotFound, LimitedAccess {
        System.out.print("\tEnter The UserName You Want To Ban From SubReddit : ");
        String username = scanner.nextLine();
        ((SubReddit) subReddit).banUser(currentUser,username);
    }

    // 11. SubRedditPage Menu Method
    public void run() throws EmptyMessageData, UserAlreadyExists, UserAlreadyHasARelType, RoomNotFound, EmptyList, UserIsNotARoomMember, UserNotFound, UserIsAlreadyARoomMember, LimitedAccess, RoomAlreadyExists {

        System.out.println("************************************************************************************** SubRedditPage ****************************************************************************************************");
        System.out.println("\tSubReddit Name : " + subReddit.getRoomName());
        Post.showSubRedditPagePosts(subReddit);
        if (Post.sortSubRedditPagePosts(subReddit).isEmpty()){
            System.out.println("\t\t\t!!!The SubReddit Has No Posts!!!\n");
        }
        if (((SubReddit) subReddit).isAdmin(currentUser)){
            System.out.print("\t\uF0211. Create Post\t2. Remove Post\t\uF0003. Select Post\t4. Add User\t5. Remove User\t6. Ban User\t7.Back To HomePage");
            if (!SignUpLoginPage.nightMood){
                SignUpLoginPage.nightMood = true;
                System.out.println("\t\uF1278. Turn On Light Mood");
            }
            else{
                SignUpLoginPage.nightMood = false;
                System.out.println("\t\uF1268. Turn On Night Mood");
            }

            System.out.print("\tChoose a Number : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
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
                    addUser();
                    run();
                    break;
                case 5:
                    removeUser();
                    run();
                    break;
                case 6:
                    banUser();
                    run();
                    break;
                case 7:
                    backToHomePage();
                    break;
                case 8:
                    turnOnOffNightMood();
                    run();
                    break;
                default:
                    run();
                    break;
            }

        }
        else {
            System.out.print("\t\uF0001. Select Post\t2. Follow / UnFollow \t3.Back To HomePage");
            if (!SignUpLoginPage.nightMood){
                SignUpLoginPage.nightMood = true;
                System.out.println("\t\uF1274. Turn On Light Mood");
            }
            else{
                SignUpLoginPage.nightMood = false;
                System.out.println("\t\uF1265. Turn On Night Mood");
            }
        }

        System.out.print("\tChoose a Number : ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                openPostPage();
                run();
                break;
            case 2:
                followOrUnFollow();
                run();
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