package com.Reddit.Models.UserManagement;

import com.Reddit.Models.MessageManagement.Messagable;
import com.Reddit.Models.PostManagement.Post;
import com.Reddit.Models.PostManagement.PostContent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SubReddit extends Room implements Messagable {

    private SubReddit(String roomName, String roomId, String description, ProfileImage profileImage) {
        super(roomName, roomId, description, profileImage);
        this.subRedditToPosts = new ArrayList<>();
    }

    // *********************************************** Connections ***************************************************//

    private List<SubRedditToPost> subRedditToPosts;

    // ******************************************* Getters & Setters *************************************************//


    public List<SubRedditToPost> getSubRedditToPosts() {
        return subRedditToPosts;
    }

    public void setSubRedditToPosts(List<SubRedditToPost> subRedditToPosts) {
        this.subRedditToPosts = subRedditToPosts;
    }

    //************************************************* Methods *****************************************************//

    // 1. Create SubReddit
    public static Room createSubReddit(User user, String roomName, String roomId, String description, ProfileImage profileImage) throws RoomAlreadyExists {

        for (Room room : Room.getRoomDataBase()) {
            if (room instanceof SubReddit) {
                if (room.getRoomId().equals(roomId)) {
                    throw new RoomAlreadyExists(" SubReddit With This ID Already Exists");
                }
            }
        }

        Room subReddit = new SubReddit(roomName, roomId, description, profileImage);
        UserToRoomRel userToRoomRel = new UserToRoomRel(user, subReddit, UserToRoomRelType.ADMIN, UserRelStatusType.IN_CONTACT);

        subReddit.getUserToRoomRels().add(userToRoomRel);
        Room.getRoomDataBase().add(subReddit);

        return subReddit;
    }

    // 2. Find subReddit
    public static Room findSubReddit(String subRedditId) throws RoomNotFound {

        for (Room room : Room.getRoomDataBase()) {
            if (room instanceof SubReddit) {
                if (room.getRoomId().equals(subRedditId)) {
                    return room;
                }
            }
        }

        throw new RoomNotFound(" SubReddit Not Found");
    }

    // 3. Add User
    @Override
    public void addUser(User currentUser, String username) throws UserNotFound, UserIsAlreadyARoomMember, LimitedAccess {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()) {
            if (userToRoomRel.getUser().equals(user)) {
                throw new UserIsAlreadyARoomMember("User Is Already In The Group");
            }
            if (userToRoomRel.getUser().equals(currentUser) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER)) {
                throw new LimitedAccess(" Only Admins Can Add Member To The Group");
            }
        }

        UserToRoomRel userToRoomRel = new UserToRoomRel(user, this, UserToRoomRelType.MEMBER, UserRelStatusType.IN_CONTACT);
        this.getUserToRoomRels().add(userToRoomRel);
    }

    // 4. Remove User
    @Override
    public void removeUser(User currentUser, String username) throws UserNotFound, LimitedAccess {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()) {
            if (userToRoomRel.getUser().equals(currentUser) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER)) {
                throw new LimitedAccess(" Only Admins Can Remove Member To The SubReddit");
            }
            if (userToRoomRel.getUser().equals(user)) {
                this.getUserToRoomRels().remove(userToRoomRel);
            }
        }
    }

    // 5. Ban a User From SubReddit
    public void banUser(User currentUser, String username) throws UserNotFound, LimitedAccess {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()) {
            if (userToRoomRel.getUser().equals(currentUser) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER)) {
                throw new LimitedAccess(" Only Admins Can Ban a Member From SubReddit");
            }
            if (userToRoomRel.getUser().equals(user) && userToRoomRel.getUserRelStatusType().equals(UserRelStatusType.IN_CONTACT)) {
                userToRoomRel.setUserRelStatusType(UserRelStatusType.BLOCKED_OR_BANNED);
            }
        }
    }

    // 6. Make a Post
    public void post(User currentUser, PostContent title, PostContent postContent) throws LimitedAccess {

        for (Room room : getRoomDataBase()){
            if (room instanceof SubReddit){
                if (room.getRoomId().equals(this.getRoomId())){
                    for (UserToRoomRel userToRoomRel : getUserToRoomRels()){
                        if (userToRoomRel.getUser().equals(currentUser)){
                            if (userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.ADMIN)){
                                SubRedditToPost post = new SubRedditToPost(title, postContent);
                                subRedditToPosts.add(post);
                            }
                            else if (userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER))
                                throw new LimitedAccess("You Are Not Eligible To Send Posts To This SubReddit");
                        }
                    }
                }
            }
        }
    }

    // 7. Remove Post
    public void removePost(User currentUser, Post post) throws LimitedAccess {
        for (Room room : getRoomDataBase()){
            if (room instanceof SubReddit){
                if (room.getRoomId().equals(this.getRoomId())){
                    for (UserToRoomRel userToRoomRel : getUserToRoomRels()){
                        if (userToRoomRel.getUser().equals(currentUser)){
                            if (userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.ADMIN)){
                                subRedditToPosts.remove(post);
                            }
                            else if (userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER))
                                throw new LimitedAccess("You Are Not Eligible To Remove Posts From This SubReddit");
                        }
                    }
                }
            }
        }
    }

    // 8. join SubReddit
    public void joinSubReddit(User currentUser) throws UserIsAlreadyARoomMember {

        for (Room room : getRoomDataBase()){
            if (room instanceof SubReddit){
                if (room.getRoomId().equals(this.getRoomId())){
                    for (UserToRoomRel userToRoomRel : getUserToRoomRels()) {
                        if (userToRoomRel.getUser().equals(currentUser)) {
                            throw new UserIsAlreadyARoomMember("You Are Already a Member Of This SubReddit");
                        }
                    }
                }
            }
        }

        UserToRoomRel userToRoomRel = new UserToRoomRel(currentUser, this, UserToRoomRelType.MEMBER, UserRelStatusType.IN_CONTACT);
        getUserToRoomRels().add(userToRoomRel);
    }

    // 9. Leave SubReddit
    public void leaveSubReddit(User currentUser) throws UserIsNotARoomMember {

        boolean isMember = true;
        for (Room room : getRoomDataBase()){
            if (room instanceof SubReddit){
                if (room.getRoomId().equals(this.getRoomId())){
                    for (UserToRoomRel userToRoomRel : getUserToRoomRels()) {
                        if (userToRoomRel.getUser().equals(currentUser)) {
                            getUserToRoomRels().add(userToRoomRel);
                            isMember = false;
                        }
                    }
                }
            }
        }

        if (!isMember){
            throw new UserIsNotARoomMember("SubReddit Is Not In Your Room List To Leave It");
        }
    }

    // 10. Check If The User Who Is Visiting The SubReddit Is Admin Or Not
    public boolean isAdmin(User currentUser){
        for (UserToRoomRel userToRoomRel : getUserToRoomRels()){
            if (userToRoomRel.getUser().equals(currentUser)){
                if (userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.ADMIN)){
                    return true;
                }
            }
        }

        return false;
    }

}