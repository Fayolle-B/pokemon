 package com.uca.gui;

 import com.uca.Pages;
 import com.uca.core.PossessionCore;
 import com.uca.core.TradeCore;
 import com.uca.core.UserCore;
 import com.uca.entity.PossessionEntity;
 import com.uca.entity.TradeStatus;
 import com.uca.entity.UserEntity;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import freemarker.template.TemplateException;

 import java.io.IOException;
 import java.io.StringWriter;
 import java.io.Writer;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
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



    public static String displayOtherProfile(int id, UserEntity connectedUser) throws IOException, TemplateException{
        Configuration configuration = _FreeMarkerInitializer.getContext();
        Map<String,Object>input = new HashMap<>();
        UserEntity userEntity=UserCore.getUserByID(id);

        input.put("user",userEntity);
        ArrayList<PossessionEntity> activePossessions=null;
        try {
            activePossessions= PossessionCore.activPossessionOf(userEntity);
        }catch(Exception e){
            System.err.println("Cannot retrieve the possession list, przinting the Stack Trace ");
            e.printStackTrace();
        }
        if (activePossessions == null) throw new AssertionError();
        input.put("activePossessions", activePossessions);

        System.out.println("Le nombre de points de l'utilsateur connecté : "+ connectedUser.getPoints());
        input.put("connectedUser", connectedUser);
        input.put("numberOfActivePossessions",activePossessions.size());
        input.put("pendingTrades", TradeCore.getTradesOfHavingStatus(userEntity, TradeStatus.PENDING));
        Writer output = new StringWriter();
        Template template = configuration.getTemplate("profile/profile.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input,output);



        return output.toString();

    }

    public static String displayMyProfile(int id) throws IOException, TemplateException {

        Configuration configuration = _FreeMarkerInitializer.getContext();
        Map<String,Object>input = new HashMap<>();
        UserEntity userEntity=UserCore.getUserByID(id);

        input.put("user",userEntity);
        ArrayList<PossessionEntity> activePossessions=null;
        try {
            activePossessions= PossessionCore.activPossessionOf(userEntity);
        }catch(Exception e){
            System.err.println("Cannot retrieve the active possessions list, przinting the Stack Trace ");
            e.printStackTrace();
        }
        if (activePossessions == null) throw new AssertionError();
        input.put("activePossessions", activePossessions);
              ArrayList<PossessionEntity> oldPossessions=null;
        try {
            oldPossessions= PossessionCore.oldPossessionOf(userEntity);
        }catch(Exception e){
            System.err.println("Cannot retrieve the old possessions list, przinting the Stack Trace ");
            e.printStackTrace();
        }
        if (oldPossessions == null) throw new AssertionError();
        input.put("oldPossessions", oldPossessions);


        System.out.println("Le pseudo est : "+ userEntity.getLogin());
        input.put("numberOfPossessions",activePossessions.size());
        input.put("trades", TradeCore.getAllTradesOf(userEntity));
        input.put("connectedUser", userEntity);
        Writer output = new StringWriter();
        Template template = configuration.getTemplate("profile/my.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input,output);


        return  output.toString();
    }




    public static  String displayProfileTrade(UserEntity userEntity){
        Configuration configuration = _FreeMarkerInitializer.getContext();
        Map<String,Object>input = new HashMap<>();
        input.put("user",userEntity);
        try {
            input.put("proposedPendingTrades", userEntity.getProposedPendingTrades());
            input.put("receivedPendingTrades", userEntity.getReceivedPendingTrades());
            input.put("acceptedTrades", TradeCore.getTradesOfHavingStatus(userEntity, TradeStatus.ACCEPTED));
            input.put("refusedTrades", TradeCore.getTradesOfHavingStatus(userEntity, TradeStatus.REFUSED));
        }catch(Exception e){
            e.printStackTrace();
            throw  new RuntimeException("Can't retrieve the complete list of trade of this user");
        }
        Writer output = new StringWriter();
        try {
            Template template = configuration.getTemplate("profile/trades.ftl");
            template.setOutputEncoding("UTF-8");
            template.process(input,output);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }


        return output.toString();


    }

    public static String possessionPicker(UserEntity userEntity, int recipientPossessionID){
        Configuration configuration= _FreeMarkerInitializer.getContext();
        Map<String, Object> input = new HashMap<>();
        ArrayList<PossessionEntity> possessions=null;
        try {
            possessions= userEntity.getAvailableForTrade();
            System.out.println("BZZZZZZZZz"+possessions);
        }catch(Exception e){
            System.err.println("Cannot retrieve the possession list, przinting the Stack Trace ");
            e.printStackTrace();
        }
        input.put("user", userEntity);
        input.put("possessions", possessions);

        input.put("recipientPossession", PossessionCore.getPossessionById(recipientPossessionID));
        Writer output = new StringWriter();
        try {
            Template template;
            if(userEntity.getAvailableForTrade().isEmpty()){
                template=configuration.getTemplate("profile/noPossToTrade.ftl");
            }else template = configuration.getTemplate("profile/possessionPicker.ftl");
            template.setOutputEncoding("UTF-8");
            template.process(input,output);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }


        return output.toString();



    }






    public static String getHomePage(String errorMessage){
        Configuration configuration= _FreeMarkerInitializer.getContext();
        Map<String, Object> input = new HashMap<>();
        List<Pages> links = new ArrayList<>();
        links.add(Pages.REGISTER);
        links.add(Pages.LOGIN);
        input.put("links",links);
        input.put("errorMessage", errorMessage);
        Template template;
        Writer output = new StringWriter();
        try {
            template=configuration.getTemplate("accueil.ftl");
            template.setOutputEncoding("UTF-8");
            template.process(input, output);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }
    public  static String getHomePage(){
        return getHomePage(null);
    }


}
