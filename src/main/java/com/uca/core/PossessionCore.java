package java.com.uca.core;


import javax.management.InvalidAttributeValueException;
import java.com.uca.dao.PossessionDAO;
import java.com.uca.entity.PokemonEntity;
import java.com.uca.entity.PossessionEntity;
import java.com.uca.entity.UserEntity;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * The Class PossessionCore is the core class for the possession entity.
 * It contains all the methods that are used to manipulate the possession entity.
 *
 */
public class PossessionCore {

    /**
     * The method addPossession adds a possession to a user.
     * @param user the user to which we want to add the possession.
     * @param pokemon the pokemon to add.
     * @param level the level of the pokemon.
     * @return the possession created.
     */
    public static PossessionEntity addPossession(UserEntity user, PokemonEntity pokemon, int level) {
        PossessionEntity possessionEntity = new PossessionEntity();
        possessionEntity.setOwner(user);
        possessionEntity.setDateAqui(LocalDate.now());
        possessionEntity.setLevel(1);
        possessionEntity.setIdPos(0);
        possessionEntity.setPokemon(pokemon);
        return new PossessionDAO().create(possessionEntity);
    }


    /**
     * The method addPossession adds a possession to a user, by taking the id of the pokemon.
     * @param user the user to which we want to add the possession.
     * @param pokemonId the id of the pokemon to add.
     * @param level the level of the pokemon.
     * @return the possession created.
     */
    public static PossessionEntity addPossession(UserEntity user, int pokemonId, int level) {
        PossessionEntity possessionEntity;
        try {
            PokemonEntity pokemon = PokemonCore.getPokemon(pokemonId);
            possessionEntity = PossessionCore.addPossession(user, pokemon,level);
        } catch (InvalidAttributeValueException e) {
            throw new RuntimeException(e);
        }
        return possessionEntity;
    }


    /**
     * The method possesionOf returns all the possessions of a user.
     * @param user the user for which we want to get the possessions.
     * @return an ArrayList of PossessionEntity containing all the possessions of the user.
     */
    public static ArrayList<PossessionEntity> possessionOf(UserEntity user) {
        return new PossessionDAO().possessionOf(user);
    }


    /**
     * The method activPossessionOf returns all the active possessions of a user.(possessions that are not lost)
     * @param user the user for which we want to get the possessions.
     * @return an ArrayList of PossessionEntity containing all the active possessions of the user.
     */
    public static ArrayList<PossessionEntity> activPossessionOf(UserEntity user){
        ArrayList<PossessionEntity> possessionEntities = possessionOf(user);
        possessionEntities.removeIf(possessionEntity -> possessionEntity.getDatePerte() != null);
        return possessionEntities;
    }



    /**
     * The method oldPossessionOf returns all the old possessions of a user.(possessions that are lost)
     * @param user the user for which we want to get the possessions.
     * @return an ArrayList of PossessionEntity containing all the old possessions of the user.
     */
    public static ArrayList<PossessionEntity> oldPossessionOf(UserEntity user) {
        ArrayList<PossessionEntity> possessionEntities = possessionOf(user);
        possessionEntities.removeIf(possessionEntity -> possessionEntity.getDatePerte()==null);
        return  possessionEntities;
    }


    /**
     * The method getAllPossessions display all the possessions of the database.
     *
     */
    public static void getAllPossessions() {
        new PossessionDAO().getAllPossessions();
    }

    /**
     * getPossessionById returns a possession from its id.
     * @param id the id of the possession.
     * @return the possession corresponding to the id.
     */

    public static PossessionEntity getPossessionById(int id) {
        try {
            return new PossessionDAO().getPossessionById(id);
        } catch (SQLException e) {
            throw new NoSuchElementException("The possession with the id "+id+" does not exist");
        }
    }


    /**
     * pexPossession increases the level of a possession by 1.
     * @param possessionEntity the possession to increase.
     * @return true if the possession has been increased, false otherwise.
     */
    public static boolean pexPossession(PossessionEntity possessionEntity) {
        if(possessionEntity.getLevel()==100)return false;
        possessionEntity.setLevel(possessionEntity.getLevel()+1);
        try {
            update(possessionEntity);
        } catch (SQLException e) {
            possessionEntity.setLevel(possessionEntity.getLevel()-1);
            throw new RuntimeException("Unable to update the possession, pex not completed");

        }
        return true;

    }

    /**
     * update updates a possession in the database from a possessionEntity.
     * @param possessionEntity the possession to update.
     * @return the possession updated.
     * @throws SQLException if the possession is not valid.
     */
    public  static PossessionEntity update(PossessionEntity possessionEntity) throws SQLException {
        return new PossessionDAO().update(possessionEntity);
    }
}
