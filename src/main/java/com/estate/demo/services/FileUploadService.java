package com.estate.demo.services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {
    public void saveFile(MultipartFile file, String name, String folderPath) throws IOException {
        // Create the folder if it doesn't exist
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Construct the file path
        String filePath = folderPath + '/' + name;

        // Save the file
        File dest = new File(filePath);
        file.transferTo(dest);
    }
}
