package com.Reddit.Models.UserManagement;

public class RoomAlreadyExists extends Exception {
    public RoomAlreadyExists(String message) {
        super(message);
    }
}
