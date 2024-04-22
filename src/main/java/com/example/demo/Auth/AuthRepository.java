package com.example.demo.Auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Repository;

import com.example.demo.Person.Person;


@Repository
public class AuthRepository  {
    private List<Person> persons ;

    public AuthRepository() {
       this.persons =  new ArrayList<>();
    }


    public void save(Person person) {
        persons.add(person);
    }

    public Person findByUserName(String name) {
        for (Person person : persons) {
            if (person.getName().equals(name)) {
                return person;
            } 
        }
        return null;
    }

  

}
