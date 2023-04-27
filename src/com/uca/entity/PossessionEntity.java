package com.uca.entity;


import java.util.Date;

public  class PossessionEntity {
    public static int nb_point=5;



    private UserEntity owner;
    private int idPos;
    private PokemonEntity pokemon;
    private int level;
    private Date dateAcqui;


    private Date datePerte;


    public PossessionEntity() {
        //Ignored !
    }


    @Override
    public String toString() {
        String stringBuilder = "La possession n°" + idPos + "appartient à " +
                owner.getId() + "et correspond au pokemon " + pokemon.getName() + "\n";
        return stringBuilder;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public int getIdPos(){
        return this.idPos;
    }
    public PokemonEntity getPokemon(){
        return this.pokemon;
    }
    public int getLevel(){
        return this.level;
    }
    public Date getDateAqui(){
        return this.dateAcqui;
    }

    public void setIdPos(int pos){
        this.idPos = pos;
    }
    public void setPokemon(PokemonEntity pok){
        this.pokemon = pok;
    }
    public void setLevel(int lev){
        this.level= lev;
    }
    public void setDateAqui(Date date){
        this.dateAcqui = date;
    }

    public Date getDatePerte() {
        return datePerte;
    }

    public void setDatePerte(Date datePerte) {
        this.datePerte = datePerte;
    }

    public String getDateAcquiAsString(){
        return this.dateAcqui.toString();
    }
}