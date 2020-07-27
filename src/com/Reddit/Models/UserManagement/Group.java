package com.Reddit.Models.UserManagement;

import com.Reddit.Models.MessageManagement.Messagable;

public class Group extends Room {

    public Group(String roomName, String roomId, String description, ProfileImage profileImage) {
        super(roomName, roomId, description, profileImage);
    }

    //************************************************* Methods *****************************************************//

    // 1. Create Group
    public static Room createGroup(User user, String roomName, String roomId, String description, ProfileImage profileImage) throws RoomAlreadyExists {

        for (Room room : Room.getRoomDataBase()){
            if (room instanceof Group) {
                if (room.getRoomId().equals(roomId)) {
                    throw new RoomAlreadyExists(" Group With This ID Already Exists");
                }
            }
        }

        Room group = new Group(roomName, roomId, description, profileImage);
        UserToRoomRel userToRoomRel = new UserToRoomRel(user,group,UserToRoomRelType.ADMIN,UserRelStatusType.IN_CONTACT);

        group.getUserToRoomRels().add(userToRoomRel);
        Room.getRoomDataBase().add(group);

        return group;
    }

    // 2. Find Group
    public static Room findGroup(String groupId) throws RoomNotFound {

        for (Room room : Room.getRoomDataBase()){
            if (room instanceof Group) {
                if (room.getRoomId().equals(groupId)) {
                    return room;
                }
            }
        }

        throw new RoomNotFound(" Group Not Found");
    }

    // 3. Add User
    @Override
    public void addUser(User currentUser, String username) throws UserNotFound, UserIsAlreadyARoomMember, LimitedAccess {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()){
            if (userToRoomRel.getUser().equals(user)){
                throw new UserIsAlreadyARoomMember("User Is Already In The Group");
            }
            if (userToRoomRel.getUser().equals(currentUser) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER)){
                throw new LimitedAccess(" Only Admins Can Add Member To The Group");
            }
        }

        UserToRoomRel userToRoomRel = new UserToRoomRel(user,this,UserToRoomRelType.MEMBER,UserRelStatusType.IN_CONTACT);
        this.getUserToRoomRels().add(userToRoomRel);
    }

    // 4. Remove User
    @Override
    public void removeUser(User currentUser, String username) throws UserNotFound, LimitedAccess {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()){
            if (userToRoomRel.getUser().equals(currentUser) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER)){
                throw new LimitedAccess(" Only Admins Can Remove Member To The Group");
            }
            if (userToRoomRel.getUser().equals(user)){
                this.getUserToRoomRels().remove(userToRoomRel);
            }
        }
    }

    // 5. Add Admin
    public void addAdmin(User currentUser, String username) throws UserNotFound, LimitedAccess {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()){
            if (!userToRoomRel.getUser().equals(user)){
                throw new UserNotFound("No Such User Is a Member In The Group");
            }
            if (userToRoomRel.getUser().equals(currentUser) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER)){
                throw new LimitedAccess(" Only Admins Can Add Admins To The Group");
            }
            else if (userToRoomRel.getUser().equals(user) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER)){
                userToRoomRel.setUserToRoomRelType(UserToRoomRelType.ADMIN);
            }
        }
    }

    // 5. remove Admin
    public void removeAdmin(User currentUser, String username) throws UserNotFound, LimitedAccess {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()){
            if (!userToRoomRel.getUser().equals(user)){
                throw new UserNotFound("No Such User Is a Member In The Group");
            }
            if (userToRoomRel.getUser().equals(currentUser) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.MEMBER)){
                throw new LimitedAccess(" Only Admins Can Add Admins To The Group");
            }
            else if (userToRoomRel.getUser().equals(user) && userToRoomRel.getUserToRoomRelType().equals(UserToRoomRelType.ADMIN)){
                userToRoomRel.setUserToRoomRelType(UserToRoomRelType.MEMBER);
            }
        }
    }

    // 6. join SubReddit
    public void joinGroup(User currentUser, String subRedditId) throws UserIsAlreadyARoomMember, RoomNotFound {

        Room group = Group.findGroup(subRedditId);

        for (UserToRoomRel userToRoomRel : group.getUserToRoomRels()){
            if (userToRoomRel.getUser().equals(currentUser)){
                throw new UserIsAlreadyARoomMember("You Are Already a Member Of This Group");
            }
        }

        UserToRoomRel userToRoomRel = new UserToRoomRel(currentUser,this,UserToRoomRelType.MEMBER,UserRelStatusType.IN_CONTACT);
        group.getUserToRoomRels().add(userToRoomRel);
    }

    // 7. join SubReddit
    public void leaveGroup(User currentUser, String subRedditId) throws RoomNotFound {

        Room group = Group.findGroup(subRedditId);

        for (UserToRoomRel userToRoomRel : group.getUserToRoomRels()){
            if (userToRoomRel.getUser().equals(currentUser)){
                group.getUserToRoomRels().remove(userToRoomRel);
            }
        }
    }
}
