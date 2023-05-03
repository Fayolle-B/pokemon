package com.uca.core;

import com.uca.dao.PossessionDAO;
import com.uca.entity.PokemonEntity;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;

import javax.management.InvalidAttributeValueException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PossessionCore {
    public static PossessionEntity addPossession(UserEntity user, PokemonEntity pokemon, int level) {
        PossessionEntity possessionEntity = new PossessionEntity();
        possessionEntity.setOwner(user);
        possessionEntity.setDateAqui(LocalDate.now());
        possessionEntity.setLevel(1);
        possessionEntity.setIdPos(0);
        possessionEntity.setPokemon(pokemon);
        return new PossessionDAO().create(possessionEntity);
    }

    public static PossessionEntity addPossession(UserEntity user, int pokemonId, int level) {
        PossessionEntity possessionEntity;
        try {
            PokemonEntity pokemon = PokemonCore.getPokemon(pokemonId);
            possessionEntity = PossessionCore.addPossession(user, pokemon,level);
        } catch (InvalidAttributeValueException e) {
            throw new RuntimeException(e);
        }
        return possessionEntity;
    }

    public static ArrayList<PossessionEntity> possessionOf(UserEntity user) {
        return new PossessionDAO().possessionOf(user);
    }

    public static ArrayList<PossessionEntity> activPossessionOf(UserEntity user){
        ArrayList<PossessionEntity> possessionEntities = possessionOf(user);
        possessionEntities.removeIf(possessionEntity -> possessionEntity.getDatePerte() != null);
        return possessionEntities;
    }

    public static ArrayList<PossessionEntity> oldPossessionOf(UserEntity user) {
        ArrayList<PossessionEntity> possessionEntities = possessionOf(user);
        possessionEntities.removeIf(possessionEntity -> possessionEntity.getDatePerte()==null);
        return  possessionEntities;
    }

    public static void getAllPossessions() {
        new PossessionDAO().getAllPossessions();
    }

    public static PossessionEntity getPossessionById(int id) throws SQLException {
        try {
            return new PossessionDAO().getPossessionById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot recover any Possession for id="+id);
        }
    }

    public static boolean pexPossession(PossessionEntity possessionEntity) {
        if(possessionEntity.getLevel()==100)return false;
        possessionEntity.setLevel(possessionEntity.getLevel()+1);
        try {
            update(possessionEntity);
        } catch (SQLException e) {
            possessionEntity.setLevel(possessionEntity.getLevel()-1);
            throw new RuntimeException("Unable to update the possession, pex not completed");

        }
        return true;

    }
    public  static PossessionEntity update(PossessionEntity possessionEntity) throws SQLException {
        return new PossessionDAO().update(possessionEntity);
    }
}
