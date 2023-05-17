package com.uca;

import java.util.ArrayList;
import java.util.Arrays;

public enum Pages {
    LOGIN("/login", "Se connecter", "login"),
    REGISTER("/register", "S'inscrire", "register"),
    MYPROFILE("/myProfile", "Mon profil", "myProfile");

    // Ajoutez d'autres constantes de pages d'erreur avec des attributs si nécessaire

    private  final String uri;
    private final String text;

    private final ArrayList<String> ids;

    Pages(String uri, String text, String... args) {
        this.uri = uri;
        this.text = text;
        this.ids = new ArrayList<>();
        this.ids.addAll(Arrays.asList(args));
    }

    Pages(String uri, String text) {
        this.uri = uri;
        this.text = text;
        this.ids=new ArrayList<>();
    }

    public String getUri() {
        return uri;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getIds() {
        return ids;
    }
}