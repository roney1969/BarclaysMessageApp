package com.example;

import com.example.entities.Person;

@SuppressWarnings("unused")
public class TestConstants {
    public static final String EXPECTED_JSON = """
[{"id":1000,"content":"First test message"},{"id":2000,"content":"Second test message"},{"id":3000,"content":"Third test message"},{"id":4000,"content":"Fourth test message"}]""";

    public static final Person BILL = new Person("Bill", "bill@gmail.com");
    public static final Person RUI = new Person("Rui", "rui@gmail.com");
}
