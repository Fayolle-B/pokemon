package com.uca.dao;


import com.uca.entity.PokemonEntity;

import java.io.IOException;
import java.net.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PokemonDAO {
    public PokemonDAO() {
    }

    public  PokemonEntity requestAPIFromId(long pokemonId) {
        // Choose a random Pokémon ID between 1 and 1008 (the number of Pokemon currently in the PokeAPI)

        // Construct the URL for the Pokémon API
        String pokemonUrl = "https://pokeapi.co/api/v2/pokemon-species/" + pokemonId;

        // Make a GET request to the Pokemon API
        URL url = null;
        try {
            url = URI.create(pokemonUrl).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;
        try {
            assert url != null;
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            assert connection != null;
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("Accept", "application/json");

        // Read the response from the API and convert it to a JSON node
        ObjectMapper mapper = new ObjectMapper();
        JsonNode pokemonNode = null;
        try {
            pokemonNode = mapper.readTree(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Extract the Pokemon name and ID from the JSON response
        assert pokemonNode != null;
        String pokemonName = pokemonNode.get("names").get(4).get("name").asText();
        int pokemonNumber = pokemonNode.get("id").asInt();
        String img = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonId + ".png";
        //String imc = pokemonNode.get("sprites").asText();


        // Return a "rogue" Pokemon (level 1, no owner, not registered in the database)
        System.out.println(pokemonName);
        System.out.println(pokemonNumber);
        System.out.println(img);

        return new PokemonEntity(pokemonName, pokemonNumber, img);

    }


}
