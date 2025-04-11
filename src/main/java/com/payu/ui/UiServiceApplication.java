package com.payu.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.payu.ui")
public class UiServiceApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(UiServiceApplication.class, args);
		openHomePage();
	}

	private static void openHomePage() throws IOException {
        String url = "http://localhost:8000/books/";
        String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";

        Runtime rt = Runtime.getRuntime();
        rt.exec(new String[]{chromePath, url});
    }

}
