package com.uca;

import com.uca.core.UserCore;
import com.uca.dao._Initializer;
import com.uca.entity.UserEntity;
import com.uca.gui.UserGUI;

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
            res.redirect("index.html");
            return null;
        });

        post("/register",(req,res) -> {
            String firstname = req.queryParams("firstname");
            String lastname = req.queryParams("lastname");
            String login = req.queryParams("login");
            String pwd = req.queryParams("password");
            String email = req.queryParams("email");
            UserCore.create(firstname,lastname,login,pwd,email);
            res.redirect("/users");
            return null;
        });
        get("/register", (req, res)->{
            res.redirect("register.html");
            return null;
        });

        get("/profile/:id",(req,res)->{
            return UserGUI.displayProfile(Integer.parseInt(req.params(":id")));
        });

        get("/login", ((request, response) -> {
            response.redirect("login.html");
            return  null;
        }));

        post("/login", ((request, response) -> {
            UserEntity user= new UserEntity();
            user.setPwd(request.queryParams("password"));
            user.setEmail(request.queryParams("email"));
            UserCore.canBeLoggedIn(user);
            return null;
        }));


    }

}
