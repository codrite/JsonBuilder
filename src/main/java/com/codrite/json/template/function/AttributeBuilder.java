package com.codrite.json.template.function;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

public class AttributeBuilder<T> implements Function<T, JSONArray> {

    protected String jsonTemplate;

    public AttributeBuilder(final String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    @Override
    public JSONArray apply(T t) {
        final String attribute = t.getClass().getSimpleName();
        final JSONArray attributeValue = find(jsonTemplate, attribute);
        replaceJsonPath(attributeValue, t);
        return attributeValue;
    }

    protected JSONArray find(String template, String attribute) {
        return JsonPath.parse(template).read(String.format("$.attributes[?(@.name=='%s')].properties", attribute.toLowerCase()));
    }

    @SuppressWarnings("unchecked")
    protected void replaceJsonPath(JSONArray attribute, T t) {
        Iterator<Object> iterator = attribute.iterator();
        if(iterator.hasNext()) {
            replaceJsonPath((Map<String, Object>) iterator.next(), t);
        }
    }

    @SuppressWarnings("unchecked")
    protected void replaceJsonPath(Map<String, Object> attributes, T t) {
        String asJsonString = toJsonString(t);
        attributes.keySet().forEach(k -> {
            if(attributes.get(k) instanceof String) {
                Object attributeValue = readAttribute(attributes, asJsonString, k);
                if(attributeValue != null)
                    attributes.put(k, attributeValue);
            } else {
                replaceJsonPath((Map<String, Object>) attributes.get(k), t);
            }
        });
    }

    private Object readAttribute(Map<String, Object> attributes, String asJsonString, String k) {
        try {
            return JsonPath.parse(asJsonString).<String>read((String) attributes.get(k));
        } catch (PathNotFoundException pathNotFound) {}
        return null;
    }

    private String toJsonString(T t) {
        try {
            StringWriter stringWriter = new StringWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(stringWriter, t);
            return stringWriter.toString();
        } catch(IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }


}
