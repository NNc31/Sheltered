package ua.edu.sumdu.nefodov.sheltered.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GenericController {

    public GenericController() {

    }

    @RequestMapping("/")
    public String home() {
        return "forward:/shelter/home";
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
