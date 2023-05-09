package com.uca.dao;

import java.sql.*;

public class _Initializer {

    public static void Init(){
        Connection connection = _Connector.getInstance();

        try {
            PreparedStatement stat2;
            PreparedStatement statement;
            PreparedStatement statement_Friends;
            PreparedStatement statement_Trade;


            //Init articles table
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users(Firstname varchar(100), LastName varchar(100), id int primary key auto_increment, login varchar(100), pwd varchar(100), points int , email varchar(100) UNIQUE ,DateConnexion date); ");
            statement.execute();            //Init users table
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS possession(idPoss int PRIMARY KEY AUTO_INCREMENT,numPkmn long NOT NULL ,level int, aquireDate DATE NOT NULL, loseDate DATE, owner_id int, FOREIGN KEY (owner_id) REFERENCES  users(id)); ");
            statement.execute();

            statement_Trade= connection.prepareStatement("CREATE TABLE IF NOT EXISTS trades(id int PRIMARY KEY  AUTO_INCREMENT, appOwnID int , recOwnID int, submitDate  Date , acceptDate Date , status varchar(100), FOREIGN KEY (appOwnID) References  POSSESSION(IDPOSS))");
            statement_Trade.execute();



            statement_Friends = connection.prepareStatement("CREATE TABLE IF NOT EXISTS friends (id1 int , id2 int , FOREIGN KEY (id1) REFERENCES users(id), FOREIGN KEY (id2) REFERENCES  users(id), PRIMARY KEY (id1,id2)); ");
            statement_Friends.executeUpdate();
            /*
            stat2 = connection.prepareStatement("INSERT INTO users(Firstname, lastname,login,pwd, points, email,DateConnexion) VALUES(?,?,?,?,?,?,?);");
            stat2.setString(1, "Truc");
            stat2.setString(2, "Muillerez");
            stat2.setString(3,"Truc");
            stat2.setString(4,"1234");
            stat2.setInt(5,0);
            stat2.setDate(7,new java.sql.Date(System.currentTimeMillis()));
            stat2.setString(6,"truc@gmail.com");
            stat2.executeUpdate();
*/



        } catch (Exception e){
            throw new RuntimeException("could not create database !");
        }
    }
}
