package com.codrite.json.template.function;

import com.codrite.json.template.Person;
import net.minidev.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AttributeBuilderTest {

    @Test
    public void assert_that_person_attribute_exists() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(new ClassPathResource("test-template.json").getURI())));
        AttributeBuilder<Person> attributeBuilder = new AttributeBuilder<>(json);
        JSONArray jsonAttributes = attributeBuilder.find(json, Person.class.getSimpleName());
        Person person = new Person();
        person.setFirstName("Arnabh");
        attributeBuilder.replaceJsonPath(jsonAttributes, person);
        Assert.assertNotNull(jsonAttributes.toString());
        System.out.println(jsonAttributes.toString());
    }

}
