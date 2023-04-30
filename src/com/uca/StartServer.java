package com.uca;

import com.uca.controller.profileController;
import com.uca.controller.tradesController;
import com.uca.core.*;
import com.uca.dao.UserDAO;
import com.uca.dao._Initializer;
import com.uca.entity.UserEntity;
import com.uca.gui.UserGUI;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import static spark.Spark.*;

public class StartServer {

    public static void main(String[] args) {
        //Configure Spark
        staticFiles.location("/static/");
        port(8081);

        _Initializer.Init();

        //Defining our routes
        get("/users", (req, res) -> {
            return UserGUI.getAllUsers();
        });
        get("/", (req, res) -> {
            if(SessionManager.isConnected(req, res)){
                res.redirect("/myProfile");
            };
            res.redirect("accueil.html");
            return null;
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
            String firstname = req.queryParams("firstname");
            String lastname = req.queryParams("lastname");
            String login = req.queryParams("login");
            String pwd = req.queryParams("password");
            String email = req.queryParams("email");
            UserCore.newUser(firstname,lastname,login,pwd,email);
            res.redirect("/users");
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
            return false;
        }));

        get("/profile/:userid/add/:pkmnid",((request, response) -> {
            PossessionCore.addPossession(UserCore.getUserFromId(Integer.parseInt(request.params(":userid"))), Integer.parseInt(request.params(":pkmnid")),0);
            response.redirect("/profile/"+request.params("userid"));
            return null;
        } ));
        get("/logOut", ((request, response) -> {
            HttpSession httpSession = request.session().raw();
            httpSession.invalidate();
            response.redirect("/");


            return null;
        } ));

        profileController.profileRoute();

        tradesController.tradesRoutes();

    }

}
