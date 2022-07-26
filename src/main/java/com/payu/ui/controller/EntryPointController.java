package com.payu.ui.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntryPointController {


    @GetMapping("/books")
    public String index(){

        return "index";
    }


}
