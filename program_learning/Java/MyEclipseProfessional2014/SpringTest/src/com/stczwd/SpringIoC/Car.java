package com.stczwd.SpringIoC;

public class Car {
	private String brand;
	private String color;
	private String maxSpeed;
	
	public Car() {
		System.out.println("init car!");
	}
	
	public Car(String brand, String color, String maxSpeed) {
		this.brand = brand;
		this.color = color;
		this.maxSpeed = maxSpeed;
	}
	
	public void introduce() {
		System.out.println("brand:"+brand+";\tcolor:"+color+";\tmaxSpeed:"+maxSpeed);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(String maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	
}
