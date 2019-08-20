package com.macglin.codeStatistics.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class StaticsController {
    @RequestMapping("/upload")
    public String upload(MultipartFile file, HttpServletRequest request){
        
        return "";
    }
}
