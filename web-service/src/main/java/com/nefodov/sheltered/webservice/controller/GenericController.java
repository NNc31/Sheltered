package com.nefodov.sheltered.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web-service")
public class GenericController {

    public GenericController() {

    }

    @RequestMapping("/")
    public String home() {
        return "forward:/web-service/shelter/home";
    }

    @RequestMapping("/api-info")
    public String apiInfo() {
        return "api-info";
    }

    @RequestMapping("/project-info")
    public String projectInfo() {
        return "project-info";
    }

}
