package com.swg.coconuts.messaging.dummy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/*-context.xml");
		applicationContext.start();
		
	}
}
