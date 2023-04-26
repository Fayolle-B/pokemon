package com.uca.core;

import com.uca.dao.PossessionDAO;
import com.uca.entity.PokemonEntity;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.Date;

public class PossessionCore {
    public static PossessionEntity addOwnership(UserEntity user, PokemonEntity pokemon) {
        PossessionEntity possessionEntity = new PossessionEntity();
        possessionEntity.setOwner(user);
        possessionEntity.setDateAqui(new Date());
        possessionEntity.setLevel(0);
        possessionEntity.setIdPos(0);
        possessionEntity.setNumPok(pokemon.getId());
        return new PossessionDAO().create(possessionEntity);
    }

    public static PossessionEntity addOwnership(UserEntity user, int pokemonId) {
        PossessionEntity possessionEntity;
        try {
            PokemonEntity pokemon = PokemonCore.getPokemon(pokemonId);
            possessionEntity = PossessionCore.addOwnership(user, pokemon);
        } catch (InvalidAttributeValueException e) {
            throw new RuntimeException(e);
        }
        return possessionEntity;
    }

    public static ArrayList<PossessionEntity> possessionOf(UserEntity user) {
        ArrayList<PossessionEntity> possessionEntities = new PossessionDAO().possessionOf(user);
        return possessionEntities;
    }

    public static void getAllOwnership() {
        new PossessionDAO().getAllOwnership();
    }

}
