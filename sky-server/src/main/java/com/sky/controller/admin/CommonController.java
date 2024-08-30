package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/admin/common")
@Api(tags = "后台管理-通用")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public Result<String> upload(MultipartFile file) {
        log.info("upload file: {}", file);
        try {
            String fileName = file.getOriginalFilename();
            String extName = fileName.substring(fileName.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extName;
            String filePath=aliOssUtil.upload( file.getBytes(), newFilename);
            return Result.success(filePath);
        } catch (Exception e) {
            log.error("upload file error", e);
            return Result.error("上传文件失败");
        }
    }
}
