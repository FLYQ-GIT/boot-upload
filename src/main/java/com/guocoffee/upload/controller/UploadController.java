package com.guocoffee.upload.controller;

import com.guocoffee.upload.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * 图片上传
 */
@Slf4j
@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @Value("${prop.img-folder}")
    private String IMG_FOLDER;


    /**
     * 单文件上传
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/singlefile")
    public R singleFileUpload(MultipartFile file, HttpServletRequest request) {
        if (Objects.isNull(file) || file.isEmpty()) {
            log.error("文件为空");
            return R.error("文件为空，请重新上传");
        }

        // 真实的存储路径
        String fileURL = UPLOAD_FOLDER;
        // 返回的url
        String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +request.getContextPath() + IMG_FOLDER;

        // 按照时间保存图片
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String objectName = sdf.format(new Date());

        fileURL = fileURL + objectName + "/";
        log.info("实际的存储地址：{}",fileURL);

        //获取文件名加后缀 + 并在前面加入时间戳和随机数 避免名字重复
        int rannum= (int)(new Random().nextDouble()*(999-100 + 1))+ 100;
        String l = String.valueOf(System.currentTimeMillis()) + rannum;
        //获取最后一个.的位置
        int lastIndexOf = file.getOriginalFilename().lastIndexOf(".");
        //获取文件的后缀名 .jpg
        String suffix = file.getOriginalFilename().substring(lastIndexOf);

        String fileName = l + suffix;
        log.info("文件名防重复：{}",fileName);


        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileURL + fileName);
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(fileURL));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            log.info("文件写入成功...");

            returnUrl = returnUrl + objectName + "/" + fileName;
            log.info("返回地址：{}",returnUrl);

            return R.ok().put("data",returnUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("后端异常...");
        }
    }
}
