package com.macglin.codeStatistics.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class StaticsController {

    private static final Logger logger = LoggerFactory.getLogger(StaticsController.class);

    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String test(){
        return "hello";
    }

    @RequestMapping("/uploadtest")
    public void upload(MultipartFile file, HttpServletRequest request){
        try {
            MultipartFile file1 = file;
            //获取文件名
            String fileName = file1.getOriginalFilename();
            //设置保存路径
            String filePath = "D:\\";
            //创建新的文件
            File file2 = new File(filePath,fileName);
            //获取原始文件流
            InputStream inputStream = file1.getInputStream();
            //创建文件输出流
            FileOutputStream outputStream = new FileOutputStream(file2);
            //提交
            IOUtils.copy(inputStream,outputStream);
            inputStream.close();
            outputStream.close();
            logger.info("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return "";
    }

    @RequestMapping("display")
    public String display(){
        return "display";
    }
}
