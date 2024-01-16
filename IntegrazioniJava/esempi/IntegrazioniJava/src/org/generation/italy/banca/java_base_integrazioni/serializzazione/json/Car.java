package org.generation.italy.banca.java_base_integrazioni.serializzazione.json;


public class Car {

	private String color;
	private Integer maxSpeed;
	private Integer weightCapacity;

	public Car() {
	}

	public Car(String color, Integer maxSpeed, Integer weightCapacity) {
		this.color = color;
		this.maxSpeed = maxSpeed;
		this.weightCapacity = weightCapacity;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getMaxSpeed() {
		return this.maxSpeed;
	}

	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Integer getWeightCapacity() {
		return this.weightCapacity;
	}

	public void setWeightCapacity(Integer weightCapacity) {
		this.weightCapacity = weightCapacity;
	}

	@Override
	public String toString() {
		return "Car [color=" + color + ", maxSpeed=" + maxSpeed + ", weightCapacity=" + weightCapacity + "]";
	}

	
	
}
