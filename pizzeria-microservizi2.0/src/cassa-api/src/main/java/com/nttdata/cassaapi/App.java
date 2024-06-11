package com.nttdata.cassaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication( scanBasePackages = {"com.nttdata.cassaapi"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}