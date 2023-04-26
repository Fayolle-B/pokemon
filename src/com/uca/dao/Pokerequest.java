package com.uca.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * @deprecated Use @ref PokemonDAO instead
 */

public class Pokerequest {
    /**
     the base of all pokeapi request
     **/
    public static URL BASE_URL;

    static {
        try {

            BASE_URL = new URI("https://pokeapi.co/api/v2/pokemon/").toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String request(long pkmnNumber) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URI(BASE_URL+String.valueOf(pkmnNumber)).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }
}
