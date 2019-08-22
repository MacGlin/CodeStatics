package com.macglin.codeStatistics.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtil {
    public static String uploadFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String filePath = "D:\\repository\\";
        File targetFiel = new File(filePath);
        //第一步判断文件是否为空
        if (file.isEmpty()){
            return fileName + "文件为空";
        }
        //第二步：判断目录是否存在，不存在，创建目录
        if (!targetFiel.exists()){
            targetFiel.mkdir();
        }
        //第三步：通过输出流讲文件写入文件夹并关闭流
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            outputStream.write(file.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "上传成功";
    }
}
