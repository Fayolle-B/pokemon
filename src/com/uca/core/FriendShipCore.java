package com.uca.core;

import com.uca.dao.FriendshipDAO;
import com.uca.entity.FriendShipEntity;
import com.uca.entity.UserEntity;

import java.util.ArrayList;


/**
 * The Class FriendShipCore is the core class for the friendship entity.
 * It contains all the methods that are used to manipulate the friendship entity.
 * (We don't use it for now)
 */
public class FriendShipCore {
    /**
     * The method getAllFriends returns all the friends of a user.
     * @param user the user for which we want to get the friends.
     * @return an ArrayList of UserEntity containing all the friends of the user.
     */
    public static ArrayList<UserEntity> getAllFriends(UserEntity user) {
        return new FriendshipDAO().getAllFriends(user);
    }

    /**
     * The method deleteFriendShip deletes a friendship.
     * @param friendShip the friendship to delete.
     *                   (We don't use it for now)
     *
     */
    public static void deleteFriendShip(FriendShipEntity friendShip){
        new FriendshipDAO().delete(friendShip);
    }


}
