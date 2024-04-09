package com.estate.demo.viewModels;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EstateViewModel {

    private String propertyName;
    private String description;
    private String price;
    private String size;
    private String realtorEmail;
    private String imageName;
    private MultipartFile file;
}
