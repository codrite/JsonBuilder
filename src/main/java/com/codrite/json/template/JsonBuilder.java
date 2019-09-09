package com.codrite.json.template;

import com.codrite.json.template.function.AttributeBuilder;

import java.util.Set;

public class JsonBuilder {

    public <T> String build(String jsonTemplate, Set<T> objects) {
        StringBuilder sb = new StringBuilder();
        AttributeBuilder<T> attributeBuilder = new AttributeBuilder<>(jsonTemplate);
        objects.stream().map(attributeBuilder).forEach(sb::append);
        return sb.toString();
    }

}
