package com.macglin.codeStatistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class FileUploadaController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file, Model model){
        if (file.isEmpty()){
            model.addAttribute("message","the file is empty");
            return "uploadStatus";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("E:\\fileUpload/" + file.getOriginalFilename());
            Files.write(path,bytes);
            model.addAttribute("message","success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/uploadStatus";
    }
}
