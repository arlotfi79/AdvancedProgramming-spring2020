package com.Reddit.Views;

import com.Reddit.ConsoleColor;
import com.Reddit.Models.UserManagement.*;

import java.util.Scanner;

public class SignUpLoginPage {

    private Scanner scanner = new Scanner(System.in);

    private SignUpLoginPage() {}

    // $ Create SignUp Or Login Page Without Using The Constructor
    public static SignUpLoginPage createSignUpLoginPage() {
        return new SignUpLoginPage();
    }

    //************************************************* Methods *****************************************************//

    // 1.Login
    private void login() throws UserNotFound, UserAlreadyExists, RoomNotFound, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        System.out.print("\tUsername Or Email : ");
        String usernameOrEmail = scanner.nextLine();
        System.out.print("\tPassword : ");
        String password = scanner.nextLine();

        User user = User.login(usernameOrEmail, password);
        HomePage homePage = HomePage.createHomePage(user);
        homePage.run();
    }

    // 2.Sign Up
    private void signUp() throws UserAlreadyExists, UserNotFound, RoomNotFound, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        System.out.print("\tName : ");
        String name = scanner.nextLine();
        System.out.print("\tLastName : ");
        String lastName = scanner.nextLine();
        System.out.print("\tProfile Image URL (Optional) : ");
        String profile = scanner.nextLine();
        ProfileImage profileImage = new ProfileImage(profile);
        System.out.print("\tUsername : ");
        String username = scanner.nextLine();
        System.out.print("\tPassword : ");
        String password = scanner.nextLine();
        System.out.print("\tEmail : ");
        String email = scanner.nextLine();

        User user = User.signUp(name, lastName, profileImage, username, password, email);
        Room myDirect = Direct.createDirect(user);
        HomePage homePage = HomePage.createHomePage(user);
        homePage.run();
    }

    public static boolean nightMood = false;
    // 3. Night Mood
    private void turnOnOffNightMood() {
        if (!nightMood){
            System.out.println(ConsoleColor.YELLOW_BACKGROUND + ConsoleColor.BLACK_BOLD_BRIGHT);
        }
        else {
            System.out.println(ConsoleColor.BLACK_BACKGROUND_BRIGHT + ConsoleColor.WHITE_BOLD_BRIGHT);
        }
    }

    // 4. SignUp / Login Menu Method
    public void run() throws UserAlreadyExists, UserNotFound, RoomNotFound, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {

        System.out.println("************************************************************************************** Welcome To Reddit *******************************************************************************************");
        System.out.print("\t\uF0D81.Login\t\uF021 2.SignUp\t\uF3B13.Exit");
        if (!nightMood){
            nightMood = true;
            System.out.println("\t\uF1274. Turn On Light Mood");
        }
        else{
            nightMood = false;
            System.out.println("\t\uF1264. Turn On Night Mood");
        }

        System.out.print("\tChoose a Number :");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                signUp();
                break;
            case 3:
                return;
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