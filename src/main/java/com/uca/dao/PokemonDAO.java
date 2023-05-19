package com.uca.dao;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uca.entity.PokemonEntity;

import java.io.IOException;
import java.net.*;


/**
 * Provides methods to interact with the PokeAPI.
 */
public class PokemonDAO {
    public PokemonDAO() {
    }

    /**
     * Retrieves a PokemonEntity from the PokeAPI.
     * @param pokemonId the ID of the Pokemon to retrieve
     * @return the PokemonEntity object corresponding to the given ID.
     */
    public PokemonEntity requestAPIFromId(long pokemonId) {

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



        return new PokemonEntity(pokemonName, pokemonNumber, img);

    }


}

