package org.generation.italy.banca.java_base_integrazioni.serializzazione.json;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

public class AppTest {

  @Test
  public void shouldSerialize() throws JsonProcessingException {

    Person person = new Person("Peter", 180, 75);

    Car ferrari = new Car("Red", 400, 500);
    Car porsche = new Car("Green", 600, 350);

    person.getCars().add(ferrari);
    person.getCars().add(porsche);

    String jsonString = new ObjectMapper().writeValueAsString(person);

    System.out.println(jsonString);

    assertTrue(jsonString != null && !jsonString.isEmpty());

  }
  
  
  @Test
  public void shouldDeserialize() throws JsonMappingException, JsonProcessingException {

    String jsonString = "{\"name\":\"Peter\",\"height\":180,\"weight\":75,\"cars\":[{\"color\":\"Red\",\"maxSpeed\":400,\"weightCapacity\":500},{\"color\":\"Green\",\"maxSpeed\":600,\"weightCapacity\":350}]}";

    Person person = new ObjectMapper().readValue(jsonString, Person.class);

    System.out.println(person.toString());

    //assertNotNull(person);
    assertTrue(person.getCars().size() > 1);

  }

}