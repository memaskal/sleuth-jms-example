package com.springcloud.springcloudsleuth.issue1937.activator;

import org.springframework.stereotype.Component;

@Component
public class Activator {
    public String request(String message) {
        return message;
    }
}
