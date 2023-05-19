package com.uca.entity;

public class PokemonEntity {

    public static long MAX_POKEMON_ID = 1008;
    private final String name;
    private final int id;


    public PokemonEntity(String pokemonName, int pokemonNumber, String img) {
        this.name = pokemonName;
        this.id = pokemonNumber;
    }
    public String getName(){
        return this.name;
    }

    public int getId() {
        return id;
    }
}