package com.uca.core;

import com.uca.dao.PokemonDAO;
import com.uca.entity.PokemonEntity;

import javax.management.InvalidAttributeValueException;

/**
 * The Class PokemonCore is the core class for the pokemon entity.
 * It contains all the methods that are used to manipulate the pokemon entity.
 */
public class PokemonCore {

    /**
     * The method getPokemon returns a pokemon from its id.
     * @param id the id of the pokemon.
     * @return the pokemon corresponding to the id.
     * @throws InvalidAttributeValueException if the id is not valid.
     */
    public static PokemonEntity getPokemon(long id) throws InvalidAttributeValueException {
        if(id<0 || id> PokemonEntity.MAX_POKEMON_ID)throw  new InvalidAttributeValueException();
        return new PokemonDAO().requestAPIFromId(id);

    }

}
