package com.uca.core;

import com.uca.dao.PokemonDAO;
import com.uca.entity.PokemonEntity;

import javax.management.InvalidAttributeValueException;

public class PokemonCore {
    public static PokemonEntity getPokemon(long id) throws InvalidAttributeValueException {
        if(id<0 || id> PokemonEntity.MAX_POKEMON_ID)throw  new InvalidAttributeValueException();
        return new PokemonDAO().requestAPIFromId(id);

    }

}
