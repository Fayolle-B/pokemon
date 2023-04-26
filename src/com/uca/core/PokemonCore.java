package com.uca.core;

import com.fasterxml.jackson.databind.DatabindException;
import com.uca.dao.PokemonDAO;
import com.uca.entity.PokemonEntity;

import javax.management.InvalidAttributeValueException;
import java.security.InvalidKeyException;
import java.util.zip.DataFormatException;

public class PokemonCore {
    public static PokemonEntity getPokemon(long id) throws InvalidAttributeValueException {
        if(id<0 || id> PokemonEntity.MAX_POKEMON_ID)throw  new InvalidAttributeValueException();
        return new PokemonDAO().requestAPIFromId(id);

    }

}
