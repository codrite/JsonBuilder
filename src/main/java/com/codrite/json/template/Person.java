package com.codrite.json.template;

import lombok.Data;

@Data
public class Person {

    private String firstName;
    private String lastName;
    private Integer age;
    private Address address;

}
