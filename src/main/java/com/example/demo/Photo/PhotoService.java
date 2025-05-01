package com.example.demo.Photo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.GlobalHandler.Exceptions.UnableToReadFile;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhotoService {
    @Value("${application.upload.photos}")
    private String uploadDir;

    public String SaveFile(MultipartFile file,Integer userId){
        return StoreFile(file,userId);
    }
    
    public byte[] readFileFromLocation(String PhotPath){
        if(StringUtils.isBlank(PhotPath)){
            return null;
        }
        try {
            Path path = new File(PhotPath).toPath();
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new UnableToReadFile("cant read file from :"  +PhotPath );
        }
    }


    private String StoreFile(MultipartFile file,Integer user){
        String path = File.separator+user;
        File targetDir = new File(path);
        if(!targetDir.exists()){
            boolean created = targetDir.mkdirs();
            if(!created){
                log.warn("target directory not created while uploading the file");
            }
        }
        String finalFilePath = targetDir+File.separator+ System.currentTimeMillis()+ "."+extractMimeType(file.getOriginalFilename());
        Path filePath = Paths.get(finalFilePath);
        try {
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
            return finalFilePath;
        } catch (Exception e) {
            log.error("file was not saved", e);
        }
        return null;

    }

    private String extractMimeType(String fileName){
        if(fileName.isEmpty() || fileName== null){
            return "";
        }
        int dotIndex = fileName.lastIndexOf(".");
        if(dotIndex == -1){
            return"";
        }
        return fileName.substring(dotIndex+1).toLowerCase();

    }


}
