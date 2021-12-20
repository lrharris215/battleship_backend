package com.battleship.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = {"http://localhost:3000","https://main.d11avqshcwqpec.amplifyapp.com"} )
public class WelcomeController {
    @GetMapping("/")
    public @ResponseBody
    String welcome() {
        return "Welcome to Battleship";
    }
}
