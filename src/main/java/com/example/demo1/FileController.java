package com.example.demo1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/upload")
    public String upload() {
        return "，upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        // 这需要填写一个本地路径
        String filePath ="E://springmvcfile//";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        System.out.println("上传失败");
        return "上传失败！";

    }
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile() throws FileNotFoundException {
        //String fileName = "C:/Users/wds/Desktop/test.txt";
        String fileName = "E:/springmvcfile/1.jpg";
        File file = new File(fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",String.format("attachment;filename=\"%s\"",file.getName()));
        headers.add("Cache-Control","no-cache,no-store,must-revalidate");
        headers.add("Pragma","no-cache");
        headers.add("Expires","0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/text"))
                .body(resource);
        return responseEntity;


    }

}
