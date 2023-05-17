package com.uca;

import com.uca.controller.PexController;
import com.uca.controller.profileController;
import com.uca.controller.tradesController;
import com.uca.core.*;
import com.uca.dao._Initializer;
import com.uca.entity.UserEntity;
import com.uca.exception.badPseudoException;
import com.uca.exception.FailedLoginException;
import com.uca.exception.NeedToConnectException;
import com.uca.gui.ErrorPagesGui;
import com.uca.gui.UserGUI;
import org.mindrot.jbcrypt.BCrypt;
import spark.Spark;

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
                response.redirect("/profile/"+SessionManager.getConnectedUser(request,response).getId());
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
            String salt = BCrypt.gensalt();
            String pwdHash = BCrypt.hashpw(req.queryParams("password"), salt);
            String email = req.queryParams("email");
            UserEntity user=null;
            try {
                user = UserCore.newUser(firstname, lastname, login, pwdHash, email);
                SessionManager.connect(req.session().raw(),user);
                res.redirect("/");
            }catch (badPseudoException e){
                e.printStackTrace();
                throw new badPseudoException("Pseudo already exist");
            }

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

        post("/login", (request, response) -> {
            if(SessionManager.tryToConnect(request)) {

                response.redirect("/myProfile");
                return null;
            }
            throw   new FailedLoginException();
        });


        get("/profile/:userid/add/:pkmnid",((request, response) -> {
            PossessionCore.addPossession(UserCore.getUserByID(Integer.parseInt(request.params(":userid"))), Integer.parseInt(request.params(":pkmnid")),0);
            response.redirect("/profile/"+request.params("userid"));
            return null;
        } ));
        get("/logout", ((request, response) -> {

            if (SessionManager.isConnected(request, response)) {
                SessionManager.disconnect(request, response);
            }
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

        //handle bad creadentials
        exception(badPseudoException.class, (((exception, request, response) -> {
            exception.printStackTrace();
            response.status(401);
            response.body(exception.getMessage());

        })));

        exception(NeedToConnectException.class, (((exception, request, response) -> {
            System.err.println("Catched NeedToConnectException, display login page");
            response.status(401);
            response.body(ErrorPagesGui.needToConnectError());

        })));

        exception(FailedLoginException.class, (((exception, request, response) -> {
            System.err.println("Catched FailedLoginException, display login error page");
            response.status(401);
            response.body(ErrorPagesGui.loginError());

        })));

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
