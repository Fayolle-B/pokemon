package com.uca.entity;

import java.sql.Timestamp;

public class Pokemon {
    private String name;
    private int id;
    private String iconSource;



    public Pokemon(String pokemonName, int pokemonNumber,String img) {
        this.name = pokemonName;
        this.id = pokemonNumber;
        this.iconSource=img;
    }
    public String getPokemonName(){
        return this.name;
    }

}