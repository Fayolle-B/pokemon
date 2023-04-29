package com.uca.core;

import com.uca.dao.PossessionDAO;
import com.uca.entity.PokemonEntity;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;

import javax.management.InvalidAttributeValueException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class PossessionCore {
    public static PossessionEntity addPossession(UserEntity user, PokemonEntity pokemon) {
        PossessionEntity possessionEntity = new PossessionEntity();
        possessionEntity.setOwner(user);
        possessionEntity.setDateAqui(new Date());
        possessionEntity.setLevel(0);
        possessionEntity.setIdPos(0);
        possessionEntity.setPokemon(pokemon);
        return new PossessionDAO().create(possessionEntity);
    }

    public static PossessionEntity addPossession(UserEntity user, int pokemonId) {
        PossessionEntity possessionEntity;
        try {
            PokemonEntity pokemon = PokemonCore.getPokemon(pokemonId);
            possessionEntity = PossessionCore.addPossession(user, pokemon);
        } catch (InvalidAttributeValueException e) {
            throw new RuntimeException(e);
        }
        return possessionEntity;
    }

    public static ArrayList<PossessionEntity> possessionOf(UserEntity user) {
        return new PossessionDAO().possessionOf(user);
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
}
