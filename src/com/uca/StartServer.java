package com.uca;

import com.uca.controller.PexController;
import com.uca.controller.profileController;
import com.uca.controller.tradesController;
import com.uca.core.*;
import com.uca.dao._Initializer;
import com.uca.entity.UserEntity;
import com.uca.exception.BadEmailException;
import com.uca.gui.ErrorPagesGui;
import com.uca.gui.UserGUI;
import com.uca.gui._FreeMarkerInitializer;
import spark.Service;
import spark.Spark;

import javax.servlet.http.HttpSession;

import java.nio.file.Files;
import java.nio.file.Paths;

import static spark.Spark.*;

public class StartServer {

    public static void main(String[] args) {
        //Configure Spark
        staticFiles.location("/static/");
        port(8081);

        _Initializer.Init();

        //Defining our routes
        get("/profile/all", (req, res) -> {
            return UserGUI.getAllUsers();
        });
        get("/", (req, res) -> {
            if(SessionManager.isConnected(req, res)){
                res.redirect("/myProfile");
                return null;
            };
            String message = req.cookie("error");
            System.out.println("le message : "+ message);
            return UserGUI.getHomePage(message);
        });
        get("/myProfile", (request, response) -> {
            if(request.session(false)!=null){
                response.redirect("/profile/"+((UserEntity)(request.session(false).attribute("user"))).getId());
            }
            else {
                halt(401,"Il vous faut créer un compte pour accéder à votre profile");
                response.redirect("/");
            }
            return null;
        } );
        post("/register",(req,res) -> {

            //TODO : create a template for the register page, so we can display error message
            String firstname = req.queryParams("firstname");
            String lastname = req.queryParams("lastname");
            String login = req.queryParams("login");
            String pwd = req.queryParams("password");
            String email = req.queryParams("email");
            try {
                UserCore.newUser(firstname, lastname, login, pwd, email);
            }catch (BadEmailException e){
                res.redirect("/register");
            }
            SessionManager.tryToConnect(req,res);
            res.redirect("/");
            return null;
        });
        get("/register", (req, res)->{
            res.redirect("register.html");
            return null;
        });


        get("/login", ((request, response) -> {
            response.redirect("login.html");
            return  null;
        }));

        post("/login", ((request, response) -> {
            if(SessionManager.tryToConnect(request,response))response.redirect("/");
            return "Utilisateur ou mot de passe incorrect";
        }));

        get("/profile/:userid/add/:pkmnid",((request, response) -> {
            PossessionCore.addPossession(UserCore.getUserFromId(Integer.parseInt(request.params(":userid"))), Integer.parseInt(request.params(":pkmnid")),0);
            response.redirect("/profile/"+request.params("userid"));
            return null;
        } ));
        get("/logout", ((request, response) -> {
            HttpSession httpSession = request.session().raw();
            httpSession.invalidate();
            response.redirect("/");


            return null;
        } ));

        get("/stop", ((request, response) -> {
            Spark.stop();
            return  null;
        }));
        profileController.profileRoute();
        PexController.pexRoute();

        tradesController.tradesRoutes();



        notFound((req, res) -> {
            res.status(404);
            return ErrorPagesGui.notFound();

        });
        //exception(); //TODO: use this to handle exception the wright way
        Spark.internalServerError((request, response) ->{
            response.status(500);
            return  ErrorPagesGui.internalServerError();
        });
    }

}
