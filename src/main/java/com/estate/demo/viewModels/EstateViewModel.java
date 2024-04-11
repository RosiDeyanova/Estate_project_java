package com.estate.demo.viewModels;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EstateViewModel {

    private String Name;
    private String description;
    private Double price;
    private Integer size;
    private String realtorEmail;
    private String imageName;
    private String statusClass;
    private String status;
    private MultipartFile file;
}
