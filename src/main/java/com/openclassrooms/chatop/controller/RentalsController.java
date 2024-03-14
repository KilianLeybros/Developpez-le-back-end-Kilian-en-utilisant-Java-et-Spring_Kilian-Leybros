package com.openclassrooms.chatop.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/rentals")
@RestController
public class RentalsController {

    @GetMapping
    public String getAll(){
        return "Rentals";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable(value = "id") Long id) {
        return "Rental" + id;
    }

    @PostMapping
    public String create(@RequestBody String body){
        return body;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable(value= "id") Long id, @RequestBody String body){
        return "Update";
    }
}
