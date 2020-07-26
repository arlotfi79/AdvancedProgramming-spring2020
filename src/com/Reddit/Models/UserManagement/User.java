package com.Reddit.Models.UserManagement;

import com.Reddit.Models.PostManagement.Post;
import com.Reddit.Models.PostManagement.PostContent;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class User {

    private static List<User> userDataBase = new ArrayList<>();

    private String name;
    private String lastName;
    private ProfileImage profileImage;
    private String username;
    private String password;
    private String email;

    public User(String name, String lastName,ProfileImage profileImage, String username, String password, String email) {
        this.name = name;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userToPosts = new ArrayList<>();
    }

    // ******************************************* Connections *******************************************************//

    private List<UserToPost> userToPosts;

    // ******************************************* Getters & Setters **************************************************//

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public List<User> getUserDataBase() {
        return userDataBase;
    }

    public void setUserDataBase(List<User> userDataBase) {
        User.userDataBase = userDataBase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserToPost> getUserToPosts() {
        return userToPosts;
    }

    public void setUserToPosts(List<UserToPost> userToPosts) {
        this.userToPosts = userToPosts;
    }

    //************************************************* Methods *****************************************************//

    // 1. Sign Up
    public static User signUp(String name, String lastName, ProfileImage profileImage, String username, String password, String email) throws UserAlreadyExists {

        for (User user : userDataBase){
            if (user.getUsername().equals(username)){
                throw new UserAlreadyExists(" User With This Username Already Exists");
            }
        }

        User user = new User(name, lastName,profileImage, username, password, email);
        userDataBase.add(user);

        return user;
    }

    // 2. Login
    public static User login(String usernameOrEmail, String password) throws UserNotFound {

        for (User user : userDataBase){
            if ((user.getUsername().equals(usernameOrEmail) || user.getEmail().equals(usernameOrEmail)) && user.getPassword().equals(password)){
                return user;
            }
        }

        throw new UserNotFound(" Incorrect Username Or Password");
    }

    // 3. Search User By UserName
    public static User searchUserByUsername(String username) throws UserNotFound {

        for (User user : userDataBase){
            if (user.getUsername().equals(username)){
                return user;
            }
        }

        throw new UserNotFound(" User Not Found");
    }

    // 4. Make a Post
    public void post(PostContent title, PostContent postContent){

        UserToPost post = new UserToPost(title, postContent);

        userToPosts.add(post);
    }

    // 5. Remove a Post
    public void removePost(Post post){
        userToPosts.remove(post);
    }

}
