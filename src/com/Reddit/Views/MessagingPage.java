package com.Reddit.Views;

import com.Reddit.ConsoleColor;
import com.Reddit.Models.DataManagement.File;
import com.Reddit.Models.DataManagement.Image;
import com.Reddit.Models.DataManagement.Text;
import com.Reddit.Models.DataManagement.Video;
import com.Reddit.Models.MessageManagement.AnyThingToMessageRelType;
import com.Reddit.Models.MessageManagement.AnythingToMessageRel;
import com.Reddit.Models.MessageManagement.Message;
import com.Reddit.Models.UserManagement.*;

import java.util.Scanner;

public class MessagingPage {

    private Scanner scanner = new Scanner(System.in);

    private User currentUser;
    private MessagingPage(User currentUser) {
        this.currentUser = currentUser;
    }

    //************************************************** Methods *****************************************************//

    // $ Creating MessagingPage Without Using The Constructor
    public static MessagingPage createMessagingPage(User user){
        return new MessagingPage(user);
    }

    // 1. Show Messages
    private void showMessages() throws RoomNotFound, EmptyList {

        System.out.println("\t1.User\t2.Group");
        System.out.print("\tChoose a Number : ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                System.out.print("\tEnter The Username Of The User You Want To See his/her Messages Sent To You: ");
                String username = scanner.nextLine();
                Room direct = Direct.findDirect(username);
                for (Message message : Message.getMessagesFromMessagable(direct)){
                    System.out.println("\t Sent By: " + message.getMessageSender().getRoomName() + " At :" + message.getSendTime() + "\n\t\t" + message.getMessageData().show());
                }
                break;
            case 2:
                System.out.print("\tEnter The Username Of The Group You Want To See It's Messages Sent To You: ");
                String groupId = scanner.nextLine();
                Room group = Group.findGroup(groupId);
                for (Message message : Message.getMessagesFromMessagable(group)){
                    System.out.println("\t Sent By: " + message.getMessageSender().getRoomName() + " At :" + message.getSendTime() + "\n\t\t" + message.getMessageData().show());
                }
                break;
            default:
                throw new EmptyList("No Messages Available");
        }
    }

    // 2.Helper1 Add MessageData
    private Message addMessageData() throws EmptyMessageData {
        System.out.println("\t1.File\t2.Image\t3.Video\t4.Text");
        System.out.print("\tWhat Do You Want To Send : ");
        int choice1 = scanner.nextInt();
        scanner.nextLine();

        switch (choice1){
            case 1:
                System.out.print("\tEnter the path : ");
                String path1 = scanner.nextLine();
                File file = new File(path1);
                return new Message(file);
            case 2:
                System.out.print("\tEnter the path : ");
                String path2 = scanner.nextLine();
                Image image = new Image(path2);
                return new Message(image);
            case 3:
                System.out.print("\tEnter the path : ");
                String path3 = scanner.nextLine();
                Video video = new Video(path3);
                return new Message(video);
            case 4:
                System.out.print("\tEnter Yor Text : ");
                String content = scanner.nextLine();
                Text text = new Text(content);
                return new Message(text);
            default:
                throw new EmptyMessageData("No MessageData Has Been Added");
        }
    }

    // 2.Helper2 Add Receiver TODO : BroadCasting Can Be Added
    private void addReceiver(Message message) throws RoomNotFound, UserAlreadyHasARelType, UserIsAlreadyARoomMember, LimitedAccess, EmptyMessageData, UserAlreadyExists, UserIsNotARoomMember, UserNotFound, EmptyList, RoomAlreadyExists {
        System.out.println("\t1.User\t2.Group\t3.BackToMessagingPage");
        System.out.print("\tWhere Do You Want To Send Your Message : ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                System.out.print("\tEnter The Username Of The Person You Want To Send Message To : ");
                String username = scanner.nextLine();
                Room direct = Direct.findDirect(username);
                AnyThingToMessageRelType anyThingToMessageRelType = message.getAnythingToMessageRelType(direct);
                if (anyThingToMessageRelType != null){
                    throw new UserAlreadyHasARelType("User Is Already In The Receiver List");
                }
                AnythingToMessageRel anythingToMessageRel = new AnythingToMessageRel(direct,AnyThingToMessageRelType.RECEIVER);
                message.getAnythingToMessageRels().add(anythingToMessageRel);
                break;
            case 2:
                System.out.print("\tEnter The GroupId Of The Group You Want To Send Message To : ");
                String groupId = scanner.nextLine();
                Room group = Group.findGroup(groupId);
                AnyThingToMessageRelType anyThingToMessageRelType2 = message.getAnythingToMessageRelType(group);
                if (anyThingToMessageRelType2 != null){
                    throw new UserAlreadyHasARelType("Group Is Already In The Receiver List");
                }
                AnythingToMessageRel anythingToMessageRel2 = new AnythingToMessageRel(group,AnyThingToMessageRelType.RECEIVER);
                message.getAnythingToMessageRels().add(anythingToMessageRel2);
                break;
            case 3:
                MessagingPage messagingPage = MessagingPage.createMessagingPage(currentUser);
                messagingPage.run();
            default:
                break;
        }
    }

    // 2. Send Message
    private Message sendMessage() throws UserNotFound, RoomNotFound, EmptyMessageData, UserAlreadyHasARelType, LimitedAccess, UserIsAlreadyARoomMember, UserIsNotARoomMember, UserAlreadyExists, EmptyList, RoomAlreadyExists {

        Message message = addMessageData();
        addReceiver(message);
        if (message.getMessageData() == null){
            throw new EmptyMessageData("MessageData Should Be Added");
        }
        else if(message.getAnythingToMessageRels().isEmpty()){
            throw new EmptyList("Message Has No Senders Or Receivers");
        }
        Room direct = Direct.findDirect(currentUser.getUsername());
        AnythingToMessageRel anythingToMessageRel = new AnythingToMessageRel(direct,AnyThingToMessageRelType.SENDER);
        message.getAnythingToMessageRels().add(anythingToMessageRel);
        message.sendMessage();

        return message;
    }

    // 3. Logout
    private void logOut() throws UserNotFound, UserAlreadyExists, RoomNotFound, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        SignUpLoginPage signUpLoginPage = SignUpLoginPage.createSignUpLoginPage();
        signUpLoginPage.run();
    }

    // 5. Back To HomePage
    private void backToHomePage() throws RoomNotFound, UserNotFound, UserAlreadyExists, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, EmptyList, EmptyMessageData, UserAlreadyHasARelType, RoomAlreadyExists {
        HomePage homePage = HomePage.createHomePage(currentUser);
        homePage.run();
    }

    // 6. Night Mood
    private void turnOnOffNightMood() {
        if (SignUpLoginPage.nightMood){
            System.out.println(ConsoleColor.YELLOW_BACKGROUND+ ConsoleColor.BLACK_BOLD_BRIGHT);
        }
        else {
            System.out.println(ConsoleColor.BLACK_BACKGROUND_BRIGHT + ConsoleColor.WHITE_BOLD_BRIGHT);
        }
    }

    // 7. MessagingPage Menu Method TODO : Add An Option To Create Group
    public void run() throws RoomNotFound, UserNotFound, EmptyMessageData, UserAlreadyHasARelType, UserIsNotARoomMember, UserIsAlreadyARoomMember, LimitedAccess, UserAlreadyExists, EmptyList, RoomAlreadyExists {

        System.out.println("************************************************************************************** Messaging Page *****************************************************************************************");
        System.out.println("\t\uF05DMessagingPage For: " + currentUser.getName() + " " + currentUser.getLastName() + "\n");

        // Showing Unseen CurrentUser Messages
        boolean hasMessages = false;
        Room direct = Direct.findDirect(currentUser.getUsername());
        System.out.println("\tNew Messages : ");
        int i = 1;
        for (Message message : Message.getMessagesFromMessagable(direct)){
            if (!message.isSeen()){
                System.out.println("\t" + i + ". Sent By: " + message.getMessageSender().getRoomName() + " At :" + message.getSendTime() + "\n\t\t\uF0C4" + message.getMessageData().show());
                message.setSeen(true);
                i++;
            }
            hasMessages = true;
        }
        if (!hasMessages){
            System.out.println("\t\tNo New Messages!!!");
        }

        System.out.println();
        System.out.print("\t\uF02C 1.Show Messages Of An Specific Group Or Person\t\uEDC6 2.Send Message\t\uF3B1 3.Log Out\t4.Back to HomePage");
        if (!SignUpLoginPage.nightMood){
            SignUpLoginPage.nightMood = true;
            System.out.println("\t\uF1265. Turn On Light Mood");
        }
        else{
            SignUpLoginPage.nightMood = false;
            System.out.println("\t\uF1275. Turn On Night Mood");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("\tChoose a Number :");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                showMessages();
                run();
                break;
            case 2:
                sendMessage();
                run();
                break;
            case 3:
                logOut();
            case 4:
                backToHomePage();
            case 5:
                turnOnOffNightMood();
                run();
                break;
            default:
                run();
                break;
        }

    }
}