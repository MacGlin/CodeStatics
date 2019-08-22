package com.macglin.codeStatistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Controller
public class FileUploadaController {

    //代码总行数
    int totalNum;
    //注释行数
    int commentNum;
    //空行
    int emptyNum;
    //实际代码行数
    int codeNum;
    //上传文件路径
    String nFilePath = null;
    @RequestMapping("/")
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
            Path path = Paths.get("D:\\fileUpload/" + file.getOriginalFilename());
            Files.write(path,bytes);
            nFilePath = "D:\\fileUpload\\" + file.getOriginalFilename();
            String message = "解析成功";
            model.addAttribute("message",message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/index";
    }

    @PostMapping("/parse")
    public String parse(Model model){
        File nFile = new File(nFilePath);
        try {
            BufferedReader bf = new BufferedReader(new FileReader(nFile));
            Stream<String> lines = bf.lines();
            lines.forEach(s -> {
                if (s.contains(";")){
                    String[] lineItem = s.split(";");
                    for (String item : lineItem) {
                        lineParse(item);
                    }
                }
                lineParse(s);
            });
            model.addAttribute("result","代码总行数为" + totalNum +
                    "注释行数为" + commentNum +
                    "空行数为" + emptyNum +
                    "实际代码行数为" + codeNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    /**
     * 解析以"；"分割后的行
     * @param ss
     */
    public void lineParse(String ss){
        totalNum++;
        ss = ss.trim();
        if (ss.startsWith("//")||ss.startsWith("/*")||ss.startsWith("*")){
            commentNum++;
        }else if (StringUtils.isBlank(ss)){
            emptyNum++;
        }else {
            codeNum++;
        }
    }
}
