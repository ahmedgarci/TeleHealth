package com.example.demo.Photo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.example.demo.GlobalHandler.Exceptions.UnableToReadFile;

import io.micrometer.common.util.StringUtils;

@Service
public class FileUtils {
    
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

}
