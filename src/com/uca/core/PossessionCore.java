package com.uca.core;

import com.uca.dao.PossessionDAO;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;

public class PossessionCore {
    public static PossessionEntity create(UserEntity user, int pkmn){
        PossessionEntity possessionEntity = new PossessionEntity();
        possessionEntity.setOwner(user);
        possessionEntity.setDateAqui(new Date());
        possessionEntity.setLevel(0);
        possessionEntity.setIdPos(0);
        possessionEntity.setNumPok(pkmn);
        return  new PossessionDAO().create(possessionEntity);
    }

    public static ArrayList<PossessionEntity> possessionOf(UserEntity user) {
        ArrayList<PossessionEntity>  possessionEntities= new PossessionDAO().possessionOf(user);
        return possessionEntities;
    }

    public static void getAllOwnership(){
        new PossessionDAO().getAllOwnership();
    }

}
