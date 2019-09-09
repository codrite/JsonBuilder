package com.codrite.json.template;

import com.codrite.json.template.function.AttributeBuilder;
import net.minidev.json.JSONArray;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonBuilder {

    public <T> String build(String jsonTemplate, String linkTemplate, Set<T> objects) {
        AttributeBuilder<T> attributeBuilder = new AttributeBuilder<>(jsonTemplate);
        Map<String, JSONArray> jsonArrayMap = objects.stream().collect(Collectors.toMap(t -> t.getClass().getSimpleName().toLowerCase(), attributeBuilder));

        for (String key : jsonArrayMap.keySet()) {
            linkTemplate = linkTemplate.replace("@" + key + "@", jsonArrayMap.get(key).toJSONString());
        }

        return linkTemplate;
    }

}
