package com.uca.entity;


import com.uca.core.TradeCore;

import java.time.LocalDate;
import java.util.ArrayList;

public class PossessionEntity {
    public static int nb_point = 5;


    private UserEntity owner;
    private int idPos;
    private PokemonEntity pokemon;
    private int level;
    private LocalDate dateAcqui;


    private LocalDate datePerte;


    public PossessionEntity() {
        //Ignored !
    }


    @Override
    public String toString() {
        String stringBuilder = "Poss n°" + idPos + " ( " +
                owner.getLogin() + " ) :  " + pokemon.getName() + "\n";
        return stringBuilder;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public int getIdPos() {
        return this.idPos;
    }

    public PokemonEntity getPokemon() {
        return this.pokemon;
    }

    public int getLevel() {
        return this.level;
    }

    public LocalDate getDateAqui() {
        return this.dateAcqui;
    }

    public void setIdPos(int pos) {
        this.idPos = pos;
    }

    public void setPokemon(PokemonEntity pok) {
        this.pokemon = pok;
    }

    public void setLevel(int lev) {
        this.level = lev;
    }

    public void setDateAqui(LocalDate date) {
        this.dateAcqui = date;
    }

    public LocalDate getDatePerte() {
        return datePerte;
    }

    public void setDatePerte(LocalDate datePerte) {
        this.datePerte = datePerte;
    }

    public String getDateAcquiAsString() {
        return this.dateAcqui.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PossessionEntity that)) return false;

        return getIdPos() == that.getIdPos();
    }

    @Override
    public int hashCode() {
        return getIdPos();
    }

    /**
     * know if a user already applied for a trade with this possession
     * @return  a boolean
     */
    public boolean isProposed(){
        ArrayList<TradeEntity> tradeEntityArrayList = TradeCore.getAllTradesOf(this.owner);
        for(TradeEntity tradeEntity : tradeEntityArrayList){
            if(tradeEntity.getApplicantPossession().equals(this)){
                return true;
            }
        }
        return false;
    }

    public void setId(int i) {
    }
}