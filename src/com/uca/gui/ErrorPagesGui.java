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

        try {

            template = configuration.getTemplate("ErrorPages/lost.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        template.setOutputEncoding("UTF-8");
        try {
            template.process(null, output);
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

        try {
            template = configuration.getTemplate("ErrorPages/internalError.ftl");
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
            template=configuration.getTemplate("ErrorPages/loginError.ftl");
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
