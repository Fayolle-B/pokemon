package com.uca.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.uca.dao.UserDAO;
import com.uca.entity.UserEntity;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtHandler;
import org.eclipse.jetty.util.ajax.JSON;
import org.h2.util.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;
import spark.Spark.*;
import spark.Session.*;

import javax.servlet.SessionCookieConfig;
import javax.servlet.http.HttpSession;

public class SessionManager {
//TODO


    public static void tryToConnect(Request request, Response response) {
        UserEntity user= new UserEntity();
        user.setPwd(request.queryParams("password"));
        user.setEmail(request.queryParams("email"));

        if(UserCore.canBeLoggedIn(user)){
            user= (new UserDAO().getUserByEmail(user.getEmail()));
            HttpSession session = request.session().raw();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30*60);
            response.redirect("succes.html");


        }else{
            System.out.println("User or password incorrect");

            //UserEntity userEntity  = (UserEntity) request.session().raw().getAttribute("user");
        }
    }


}
