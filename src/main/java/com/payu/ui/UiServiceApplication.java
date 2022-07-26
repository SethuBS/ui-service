package com.payu.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackageClasses = com.payu.ui.controller.EntryPointController.class)
public class UiServiceApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(UiServiceApplication.class, args);
		openHomePage();
	}

	private static void openHomePage() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8000/books/");
	}

}
