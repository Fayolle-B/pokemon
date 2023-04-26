package com.uca.core;

import com.uca.dao.FriendshipDAO;
import com.uca.entity.FriendShipEntity;
import com.uca.entity.UserEntity;

import java.util.ArrayList;

public class FriendShipCore {
    public static ArrayList<UserEntity> getAllfreinds(UserEntity user) {
        return new FriendshipDAO().getAllFriends(user);
    }
    public static void deleteFriendShip(FriendShipEntity friendShip){
        new FriendshipDAO().delete(friendShip);
    }


}
