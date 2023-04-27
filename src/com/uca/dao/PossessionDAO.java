package com.uca.dao;

import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;
import com.uca.dao.PokemonDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PossessionDAO extends _Generic<PossessionEntity>{


    @Override
    public PossessionEntity create(PossessionEntity obj) {
        try {
            PreparedStatement statement =  this.connect.prepareStatement("INSERT INTO possession (level,numPkmn, AQUIREDATE,LOSEDATE, owner_id ) VALUES(?,?,?,?,?);");
            statement.setInt(1, obj.getLevel());
            statement.setLong(2, obj.getPokemon().getId());
            statement.setDate(3, new java.sql.Date(obj.getDateAqui().getTime()));
            statement.setDate(4, null);
            statement.setInt(5,obj.getOwner().getId());
            statement.executeUpdate();
            System.out.println("ON ajout un pokemon à l'utilisateur n°"+ obj.getOwner().getId());

            //statement.executeUpdate();
        }catch (SQLException error) {
            error.printStackTrace();
        }
        return obj;
    }

    @Override
    public void delete(PossessionEntity obj) {
        return ;

    }


    public PossessionEntity getPossessionById(int id) throws SQLException {
        PossessionEntity possessionEntity = new PossessionEntity();
        PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT IDPOSS, NUMPKMN, LEVEL, DATEACQUISITION, DATEPERTE, OWNER_ID from POSSESSION where IDPOSS = ?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()){
            throw new SQLException("Can't resolve any possession for this ID");
        }
        possessionEntity.setIdPos(id);
        possessionEntity.setLevel(resultSet.getInt("LEVEL"));
        possessionEntity.setDateAqui(new java.util.Date(resultSet.getDate("DATEACQUISITION").getTime()));
        possessionEntity.setDatePerte(new java.util.Date(resultSet.getDate("DATEPERTE").getTime()));
        possessionEntity.setOwner(new UserDAO().getUserById(resultSet.getInt("OWNER_ID")));
        possessionEntity.setPokemon(new PokemonDAO().requestAPIFromId( resultSet.getInt("NUMPKMN")));

        return possessionEntity;

    }
    public void getAllPossessions()
    {
        System.out.println("On va essayer de lire toutes les possessionss");
        try {
            PreparedStatement prep = this.connect.prepareStatement("SELECT * from possession");
            ResultSet res = prep.executeQuery();
            while (res.next()){
                System.out.println( "id poss : "+res.getInt("idposs")+ "\t\n \t");
                System.out.println(res.getInt("owner_id"));
            }
            System.out.println("Fini");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<PossessionEntity> possessionOf(UserEntity user) {
        ArrayList<PossessionEntity> entities = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT IDPOSS, NUMPKMN, LEVEL, AQUIREDATE, LOSEDATE, OWNER_ID from possession where owner_id=?");
            preparedStatement.setInt(1,user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PossessionEntity entity = new PossessionEntity();
                entity.setOwner(user);
                entity.setLevel(resultSet.getInt("level"));
                entity.setIdPos(resultSet.getInt("idposs"));
                entity.setDateAqui(new Date(resultSet.getDate("AQUIREDATE").getTime()));
                entity.setPokemon(new PokemonDAO().requestAPIFromId(resultSet.getInt("numPkmn")));
                java.sql.Date sqlDate = resultSet.getDate("LOSEDATE");
                if (sqlDate==null){
                    entity.setDatePerte(null);
                }else {
                    entity.setDatePerte(new Date((sqlDate).getTime()));
                }

                entities.add(entity);
                System.out.println("On a ajouté");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entities;
    }
}
