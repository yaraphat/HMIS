package com.idb.hmis.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Yasir Araphat
 */
@Component
public class FileService {
    
    private String uploadFolder = null;

    public FileService() {
        String p = System.getProperty("java.class.path");
        uploadFolder = p.substring(0, p.indexOf(";")) + "\\static\\uploads\\";
    }

    public String store(MultipartFile photo) {
        return this.storePhoto(photo, uploadFolder);
    }
//
//    public String store(MultipartFile photo, String folder) {
//        return this.storePhoto(photo, folder);
//    }

    private String storePhoto(MultipartFile photo, String folder) {
        if (photo == null || photo.isEmpty()) {
            return null;
        }
        long time = new Date().getTime();
        try {
            byte[] bytes = photo.getBytes();
            String photoTitle = time + "_" + photo.getOriginalFilename();
            String pathString = folder + photoTitle;
            Path path = Paths.get(pathString);
            Files.write(path, bytes);
            return photoTitle;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean delete(String photoTitle) {
        return this.deletePhoto(photoTitle, uploadFolder);
    }

    public boolean delete(String photoTitle, String folder) {
        return this.deletePhoto(photoTitle, folder);
    }

    private boolean deletePhoto(String photoTitle, String folder) {
        if (photoTitle == null || photoTitle.isEmpty()) {
            return false;
        }
        try {
            Path path = Paths.get(folder + photoTitle);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

}
