package com.uca.entity;

public class PokemonEntity {

    public static long MAX_POKEMON_ID = 1008;
    private String name;
    private int id;
    private String iconSource;



    public PokemonEntity(String pokemonName, int pokemonNumber, String img) {
        this.name = pokemonName;
        this.id = pokemonNumber;
        this.iconSource=img;
    }
    public String getName(){
        return this.name;
    }

    public int getId() {
        return id;
    }
}