package com.uca.dao;

import com.uca.core.UserCore;
import com.uca.entity.FriendShipEntity;
import com.uca.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendshipDAO extends  _Generic<FriendShipEntity>{

    public ArrayList<UserEntity> getAllFriends(UserEntity user) {
        ArrayList<UserEntity> friends = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM friends where id1 =? or id2=? ORDER BY id ASC;");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                friends.add(UserCore.getUserFromId(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

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
