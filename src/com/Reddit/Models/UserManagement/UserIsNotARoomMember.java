package com.Reddit.Models.UserManagement;

public class UserIsNotARoomMember extends Exception {
    public UserIsNotARoomMember(String message) {
        super(message);
    }
}
