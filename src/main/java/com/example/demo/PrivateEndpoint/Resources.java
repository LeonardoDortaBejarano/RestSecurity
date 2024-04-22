package com.example.demo.PrivateEndpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/resources")
public class Resources {
    @PostMapping()
    public String getPrivateResoruces() {
        return "this is private..";
    }
    

}
