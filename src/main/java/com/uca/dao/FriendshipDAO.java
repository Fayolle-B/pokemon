package java.com.uca.dao;

import java.com.uca.core.UserCore;
import java.com.uca.entity.FriendShipEntity;
import java.com.uca.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides methods to interact with UserDAO and perform Create/Read/Update/Delete operations w/ userEntity
 */
public class FriendshipDAO extends  _Generic<FriendShipEntity>{


    /**
     * Returns a list of all friends of a user.
     * @param user  the user
     * @return an ArrayList of UserEntity objects
     */
    public ArrayList<UserEntity> getAllFriends(UserEntity user) {
        ArrayList<UserEntity> friends = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM friends where id1 =? or id2=? ORDER BY ID1 ASC;");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                friends.add(UserCore.getUserByID(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }


    /**
     * Creates a new FriendShipEntity in the database.
     * @param obj the FriendShipEntity to be created
     * @return the newly created FriendShipEntity object
     */
    @Override
    public FriendShipEntity create(FriendShipEntity obj) {
        try {
            PreparedStatement statement =  this.connect.prepareStatement("INSERT INTO friends (id1, id2) VALUES(?, ?);");
            statement.setInt(1,obj.getId1());
            statement.setInt(2, obj.getId2());
            statement.executeUpdate();
        }catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a FriendShipEntity from the database.
     * @param friendShip the FriendShipEntity to be deleted
     */
    @Override
    public void delete(FriendShipEntity friendShip) {
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("DELETE FROM friends WHERE id1 = ? OR id2 = ?");
            preparedStatement.setInt(1, friendShip.getId1());
            preparedStatement.setInt(2, friendShip.getId2());
            preparedStatement.executeUpdate();
        }catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
