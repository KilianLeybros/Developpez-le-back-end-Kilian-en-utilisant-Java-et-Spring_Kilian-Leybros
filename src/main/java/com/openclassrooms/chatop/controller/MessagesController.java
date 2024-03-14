package com.openclassrooms.chatop.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/messages")
@RestController
public class MessagesController {

    @PostMapping
    public String message(@RequestBody String body){
        return body;
    }



}
