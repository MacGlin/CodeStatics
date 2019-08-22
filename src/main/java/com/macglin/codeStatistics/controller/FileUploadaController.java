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
            File nFile = new File("D:\\fileUpload\\" + file.getOriginalFilename());
            parse(nFile);
            String message = "解析成功" +
                    "文件名为" + file.getOriginalFilename() +
                    "代码总行数为" + totalNum +
                    "注释行数为" + commentNum +
                    "空行为" + emptyNum +
                    "实际代码行数为" + codeNum;
            model.addAttribute("message",message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/index";
    }

    public String parse(File nFile){
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
