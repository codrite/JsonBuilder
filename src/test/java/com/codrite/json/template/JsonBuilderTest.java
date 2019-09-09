package com.codrite.json.template;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class JsonBuilderTest {

    @Test
    public void assert_that_attribute_name_is_rendered_with_value() throws IOException {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAge(20);

        Address address = new Address();
        address.setLine1("25 Platiff Rd");
        address.setLine2("Canary Wharf");
        address.setCity("London");
        address.setState("London");
        address.setZip("EADFE");
        address.setCountry("UK");

        person.setAddress(address);

        String json = new String(Files.readAllBytes(Paths.get(new ClassPathResource("test-template.json").getURI())));
        JsonBuilder jsonBuilder = new JsonBuilder();
        String finalOutput = jsonBuilder.build(json, Collections.singleton(person));
        System.out.println(finalOutput);
    }

}
