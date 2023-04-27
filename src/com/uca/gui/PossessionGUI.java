package com.uca.gui;

import com.uca.core.PossessionCore;
import com.uca.entity.PossessionEntity;
import com.uca.entity.UserEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PossessionGUI {




    public static String displayFromId(int id) throws IOException, TemplateException {
        Configuration configuration = _FreeMarkerInitializer.getContext();
        Map<String, Object> input = new HashMap<>();
        PossessionEntity possessionEntity= PossessionCore.getPossessionById(id);
        input.put("possession", possessionEntity);
        Writer output = new StringWriter();
        Template template = configuration.getTemplate("possession.ftl");
        template.setOutputEncoding("UTF-8");
        template.process(input, output);
        return output.toString();
    }

}
