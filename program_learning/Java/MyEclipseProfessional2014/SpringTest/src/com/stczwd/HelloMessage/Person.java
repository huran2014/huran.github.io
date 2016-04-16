package com.stczwd.HelloMessage;

public class Person {
	private HelloMessage helloMessage;

	public HelloMessage getHelloMessage() {
		return helloMessage;
	}

	public void setHelloMessage(HelloMessage helloMessage) {
		this.helloMessage = helloMessage;
	}
	
	public String sayHello() {
		return this.helloMessage.sayhello();
	}
}
