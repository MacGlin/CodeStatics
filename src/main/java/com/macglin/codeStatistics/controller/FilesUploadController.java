package com.macglin.codeStatistics.controller;

import com.macglin.codeStatistics.Util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FilesUploadController {
    //单个文件处理
    @RequestMapping(value = "testUpload",method = RequestMethod.POST)
    public @ResponseBody
    String upload(@RequestParam("file")MultipartFile file, HttpServletRequest request){
        String info = null;
        try {
            info = FileUtil.uploadFile(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    //多文件上传
    @RequestMapping(value = "testUploads",method = RequestMethod.POST)
    @ResponseBody
    public String multipleFileUpload(HttpServletRequest request){
        //获取上传的文件数组
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("files");
        //遍历处理文件
        String info = null;
        for (MultipartFile file:files) {
            try {
                String s = FileUtil.uploadFile(file);
                info = info + "-" + s;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return info;
    }
}
