 package com.uca.gui;

import com.uca.core.PossessionCore;
import com.uca.core.UserCore;
import com.uca.dao.PokemonDAO;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Provides methods to generate HTML templates with user-related data
 * using the FreeMarker template engine. ( les "Vues" )
 */
public class UserGUI {



    /**
     * Generates an HTML template with a table containing information about all users
     * in the database.
     *
     * @return the generated HTML template as a string
     * @throws IOException if an I/O error occurs while processing the FreeMarker template
     * @throws TemplateException if an error occurs while processing the FreeMarker template
     */
    public static String getAllUsers() throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Map<String, Object> input = new HashMap<>();

        input.put("users", UserCore.getAllUsers());

        Writer output = new StringWriter();
        Template template = configuration.getTemplate("users/users.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);

        return output.toString();
    }



    public static String displayProfile(int id) throws IOException, TemplateException{
        Configuration configuration = _FreeMarkerInitializer.getContext();
        Map<String,Object>input = new HashMap<>();
        UserEntity userEntity=UserCore.getUserFromId(id);

        input.put("user",userEntity);
        ArrayList<PossessionEntity> possessions=null;
        try {
            possessions= PossessionCore.possessionOf(userEntity);
            System.out.println(possessions);
        }catch(Exception e){
            System.err.println("Cannot retrieve the possession list, printing the Stack Trace ");
            e.printStackTrace();
        }
        if (possessions == null) throw new AssertionError();
        input.put("possessions", possessions);
        System.out.println("Le pseudo est : "+ userEntity.getLogin());
        input.put("numberOfPossessions",possessions.size());
        Writer output = new StringWriter();
        Template template = configuration.getTemplate("profile/profile.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input,output);
        for(PossessionEntity p:possessions){
            System.out.println(new PokemonDAO().requestAPIFromId(p.getNumPok()).getPokemonName());
        }


        return output.toString();

    }






}
