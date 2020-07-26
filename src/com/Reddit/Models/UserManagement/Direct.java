package com.Reddit.Models.UserManagement;

public class Direct extends Room {

    public Direct(String roomName, String roomId, String description, ProfileImage profileImage) {
        super(roomName, roomId, description, profileImage);
    }

    //************************************************* Methods *****************************************************//

    // 1. Create Direct
    // Attention ---> In UserToRoomRel we can Access The Current User Followings Using ( userToRoomRel.getRoom().getRoomId() )
    public static Room createDirect(User userWeAreVisiting) {

        Room direct = new Direct(userWeAreVisiting.getName(),userWeAreVisiting.getUsername(),"Direct Chat",userWeAreVisiting.getProfileImage());
        Room.getRoomDataBase().add(direct);

        return direct;
    }

    // 2. Adding User For Direct Messaging (Kinda Doing The Same Thing as Follow)
    @Override
    public void addUser(User currentUser, String username) throws UserNotFound, UserIsAlreadyARoomMember {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()) {
            if (userToRoomRel.getRoom().getRoomId().equals(user.getUsername())) {
                throw new UserIsAlreadyARoomMember("User Is Already In Your Contact List");
            }
        }

        UserToRoomRel userToRoomRel = new UserToRoomRel(currentUser,this,UserToRoomRelType.ADMIN,UserRelStatusType.IN_CONTACT);
        this.getUserToRoomRels().add(userToRoomRel);
    }

    // 3. Removing User From Your Direct Messaging Screen (Kinda Doing The Same Thing as UnFollow)
    @Override
    public void removeUser(User currentUser, String username) throws UserNotFound, UserIsNotARoomMember {

        User user = User.searchUserByUsername(username);

        for (UserToRoomRel userToRoomRel : this.getUserToRoomRels()){
            if (userToRoomRel.getUser().equals(user)){
                this.getUserToRoomRels().remove(userToRoomRel);
            }
        }

        throw new UserIsNotARoomMember("User Is Not In Your Contact List");
    }

    // 4. Find Direct
    public static Room findDirect(String username) throws RoomNotFound {
        for (Room room : Room.getRoomDataBase()){
            if (room instanceof Direct){
                if (room.getRoomId().equals(username)){
                    return room;
                }
            }
        }

        throw new RoomNotFound("No Direct Chat With This Username Found");
    }

}
