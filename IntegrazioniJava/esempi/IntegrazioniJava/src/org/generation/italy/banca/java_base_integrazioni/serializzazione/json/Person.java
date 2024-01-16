package org.generation.italy.banca.java_base_integrazioni.serializzazione.json;


import java.util.ArrayList;
import java.util.List;

public class Person {
  private String name;
  private int height;
  private int weight;
  private List<Car> cars = new ArrayList<Car>();

  public Person() {

  }

  public Person(String name, int height, int weight) {
    this.name = name;
    this.height = height;
    this.weight = weight;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHeight() {
    return this.height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWeight() {
    return this.weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public List<Car> getCars() {
    return this.cars;
  }

  public void setCars(List<Car> cars) {
    this.cars = cars;
  }

@Override
public String toString() {
	return "Person [name=" + name + ", height=" + height + ", weight=" + weight + ", cars=" + cars + "]";
}

  
  
}