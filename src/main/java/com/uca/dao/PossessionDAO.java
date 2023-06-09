package com.uca.dao;

import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides methods to interact with Possessionn database.
 */
public class PossessionDAO extends _Generic<PossessionEntity>{


    /**
     * Creates a new PossessionEntity in the database. (Write in the database)
     * @param obj the PossessionEntity to be created
     * @return the newly created PossessionEntity object
     */
    @Override
    public PossessionEntity create(PossessionEntity obj) {
        try {
            PreparedStatement statement =  this.connect.prepareStatement("INSERT INTO possession (level,numPkmn, AQUIREDATE,LOSEDATE, owner_id ) VALUES(?,?,?,?,?);");
            statement.setInt(1, obj.getLevel());
            statement.setLong(2, obj.getPokemon().getId());
            statement.setDate(3,  java.sql.Date.valueOf(obj.getDateAqui()));
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


    /**
     * Deletes a PossessionEntity from the database.
     * @param obj the PossessionEntity to be deleted
     */
    @Override
    public void delete(PossessionEntity obj) {
        return ;

    }


    /**
     * Updates a PossessionEntity in the database.
     * @param obj the PossessionEntity to be updated
     * @return the updated PossessionEntity object
     * @throws SQLException if the update fails
     * <p> Update the possession in the database by using the id of the  given possession</p>
     */
    public PossessionEntity update(PossessionEntity obj) throws  SQLException{
        PreparedStatement preparedStatement = this.connect.prepareStatement("UPDATE POSSESSION SET LOSEDATE =?, LEVEL = ? where IDPOSS=?");
        if(obj.getDatePerte()!=null)preparedStatement.setDate(1,java.sql.Date.valueOf(obj.getDatePerte()));
        else preparedStatement.setDate(1,null);

        preparedStatement.setInt(2,obj.getLevel());
        preparedStatement.setInt(3,obj.getIdPos());
        preparedStatement.executeUpdate();
        return obj;
    }

    /**
     * retrieve a possession by its id
     * @param id the id of the possession
     * @return the possession
     * @throws SQLException if the request fails
     */
    public PossessionEntity getPossessionById(int id) throws SQLException {
        PossessionEntity possessionEntity = new PossessionEntity();
        PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT IDPOSS, NUMPKMN, LEVEL, AQUIREDATE, LOSEDATE, OWNER_ID from POSSESSION where IDPOSS = ?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()){
            throw new SQLException("Can't resolve any possession for this ID");
        }
        possessionEntity.setIdPos(id);
        possessionEntity.setLevel(resultSet.getInt("LEVEL"));
        possessionEntity.setDateAqui((resultSet.getDate("AQUIREDATE")).toLocalDate());
        try {
            possessionEntity.setDatePerte(resultSet.getDate("LOSEDATE").toLocalDate());
        }catch (NullPointerException e){
            possessionEntity.setDatePerte(null);
        }
        possessionEntity.setOwner(new UserDAO().getUserById(resultSet.getInt("OWNER_ID")));
        possessionEntity.setPokemon(new PokemonDAO().requestAPIFromId( resultSet.getInt("NUMPKMN")));

        return possessionEntity;

    }

    /**
     * retrieve all the possessions
     * Display all the existing possesion in the database in the console
     */
    public void getAllPossessions()
    {
        System.out.println("On va essayer de lire toutes les possessionss");
        try {
            PreparedStatement prep = this.connect.prepareStatement("SELECT * from possession");
            ResultSet res = prep.executeQuery();
            while (res.next()){
                System.out.println( "id poss : "+res.getInt("idposs")+ "\t\n \t");
            }
            System.out.println("Fini");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * retrieve all the possessions of a user
     * @param user the user
     * @return the list of the possessions of the user
     */
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
                entity.setDateAqui(resultSet.getDate("AQUIREDATE").toLocalDate());
                entity.setPokemon(new PokemonDAO().requestAPIFromId(resultSet.getInt("numPkmn")));
                java.sql.Date sqlDate = resultSet.getDate("LOSEDATE");
                if (sqlDate==null){
                    entity.setDatePerte(null);
                }else {
                    entity.setDatePerte( (sqlDate).toLocalDate());
                }
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entities;
    }
}
