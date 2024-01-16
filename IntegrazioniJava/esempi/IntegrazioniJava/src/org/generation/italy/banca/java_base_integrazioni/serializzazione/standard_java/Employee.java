package org.generation.italy.banca.java_base_integrazioni.serializzazione.standard_java;

public class Employee implements java.io.Serializable {
   public String name;
   public String address;
   public int SSN;
   public int number;
   
   
   @Override
	public String toString() {
		return "Employee [name=" + name + ", address=" + address + ", SSN=" + SSN + ", number=" + number + "]";
	}



   public void mailCheck() {
      System.out.println("Mailing a check to " + name + " " + address);
   }
}
