package com.Reddit;

import com.Reddit.Models.UserManagement.*;
import com.Reddit.Views.EmptyList;
import com.Reddit.Views.EmptyMessageData;
import com.Reddit.Views.SignUpLoginPage;
import com.Reddit.Views.UserAlreadyHasARelType;

public class Main {

    public static void main(String[] args) throws UserNotFound, UserAlreadyExists, RoomNotFound, UserIsAlreadyARoomMember, LimitedAccess, UserIsNotARoomMember, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        System.out.println(ConsoleColor.YELLOW_BACKGROUND + ConsoleColor.BLACK_BOLD_BRIGHT);
        SignUpLoginPage signUpLoginPage = SignUpLoginPage.createSignUpLoginPage();
        signUpLoginPage.run();
    }
}
