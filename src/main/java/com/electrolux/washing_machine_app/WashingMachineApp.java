package com.electrolux.washing_machine_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.config.EnableStateMachine;

@SpringBootApplication
@EnableStateMachine
public class WashingMachineApp {

    public static void main(String[] args) {
        SpringApplication.run(WashingMachineApp.class, args);
    }

}
