package com.Reddit.Models.UserManagement;

import com.Reddit.Models.MessageManagement.Messagable;

import java.util.ArrayList;
import java.util.List;

public abstract class Room implements Messagable {

    private static List<Room> roomDataBase = new ArrayList<>();

    private String roomName;
    private String roomId;
    private String description;
    private ProfileImage profileImage;

    public Room(String roomName, String roomId, String description, ProfileImage profileImage) {
        this.roomName = roomName;
        this.roomId = roomId;
        this.description = description;
        this.profileImage = profileImage;
        this.userToRoomRels = new ArrayList<>();
    }

    // ******************************************* Connections *******************************************************//

    private List<UserToRoomRel> userToRoomRels;

    // ******************************************* Getters & Setters **************************************************//


    public static List<Room> getRoomDataBase() {
        return roomDataBase;
    }

    public static void setRoomDataBase(List<Room> roomDataBase) {
        Room.roomDataBase = roomDataBase;
    }

    public List<UserToRoomRel> getUserToRoomRels() {
        return userToRoomRels;
    }

    public void setUserToRoomRels(List<UserToRoomRel> userToRoomRels) {
        this.userToRoomRels = userToRoomRels;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    //************************************************* Methods *****************************************************//

    // 1. Show Rooms That User
    public List<Room> showUserRooms(User currentUser) {

        List<Room> userRooms = new ArrayList<>();

         for (UserToRoomRel userToRoomRel : userToRoomRels) {
            if (userToRoomRel.getUser().equals(currentUser)) {
                userRooms.add(userToRoomRel.getRoom());
            }
        }

         return userRooms;
    }

    // 2. Add Member
    public abstract void addUser(User currentUser, String username) throws UserNotFound, UserIsAlreadyARoomMember, LimitedAccess;

    // 3. Remove Member
    public abstract void removeUser(User currentUser, String username) throws UserNotFound, LimitedAccess, UserIsNotARoomMember;

}
