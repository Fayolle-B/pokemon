package com.uca.gui;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.staticFiles;

public class ErrorPagesGui {

    public static String notFound(){
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Writer output = new StringWriter();
        Template template = null;
        Map<String, Object> input = new HashMap<>();
        input.put("message","Page not found");
        input.put("HTTPErrorCode","404");
        input.put("title","Page non trouvée");

        try {

            template = configuration.getTemplate("ErrorPages/ErrorPage.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        template.setOutputEncoding("UTF-8");
        try {
            template.process(input, output);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }
    public static  String internalServerError() {
        Configuration configuration = _FreeMarkerInitializer.getContext();

        Writer output = new StringWriter();
        Template template = null;
        Map<String, Object> input = new HashMap<>();
        input.put("message","Internal Server Error");
        input.put("HTTPErrorCode","500");
        input.put("title","Erreur interne");
        try {
            template = configuration.getTemplate("ErrorPages/ErrorPage.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        template.setOutputEncoding("UTF-8");

        try {
            template.process(input, output);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }

    public static String loginError(){
        Configuration configuration = _FreeMarkerInitializer.getContext();
        Writer writer = new StringWriter();
        Template template=null;
        Map<String, Object> input = new HashMap<>();
        try {
            input.put("message","Login ou mot de passe incorrect");
            input.put("HTTPErrorCode","401");
            input.put("title","Erreur de connexion");
            template=configuration.getTemplate("ErrorPages/ErrorPage.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        template.setOutputEncoding("UTF-8");

        try {
            template.process(input,writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        return  writer.toString();
    }

    public static String needToConnectError(){

        Configuration configuration = _FreeMarkerInitializer.getContext();
        Writer writer = new StringWriter();
        Template template=null;
        Map<String, Object> input = new HashMap<>();
        try {
            input.put("message","Vous devez vous connecter pour accéder à cette page");
            input.put("HTTPErrorCode","401");
            input.put("title","Erreur de connexion");
            template=configuration.getTemplate("ErrorPages/ErrorPage.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        template.setOutputEncoding("UTF-8");

        try {
            template.process(input,writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        return  writer.toString();
    }
}
