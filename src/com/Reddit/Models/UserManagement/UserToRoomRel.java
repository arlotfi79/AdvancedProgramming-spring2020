package com.Reddit.Models.UserManagement;

public class UserToRoomRel {

    public UserToRoomRel(User user, Room room,UserToRoomRelType userToRoomRelType, UserRelStatusType userRelStatusType) {
        this.user = user;
        this.room = room;
        this.userToRoomRelType = userToRoomRelType;
        this.userRelStatusType = userRelStatusType;
    }

    // ********************************************* Connections *****************************************************//

    private User user;
    private Room room; // We Had To Accept This Redundant Data Because We Needed To Know The User's Rooms
    private UserToRoomRelType userToRoomRelType;
    private UserRelStatusType userRelStatusType;

    // ******************************************* Getters & Setters **************************************************//

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRelStatusType getUserRelStatusType() {
        return userRelStatusType;
    }

    public void setUserRelStatusType(UserRelStatusType userRelStatusType) {
        this.userRelStatusType = userRelStatusType;
    }

    public UserToRoomRelType getUserToRoomRelType() {
        return userToRoomRelType;
    }

    public void setUserToRoomRelType(UserToRoomRelType userToRoomRelType) {
        this.userToRoomRelType = userToRoomRelType;
    }
}
