package com.estate.demo.mappers;


import com.estate.demo.enums.EstateStatus;
import com.estate.demo.models.Estate;
import com.estate.demo.services.FileUploadService;
import com.estate.demo.viewModels.EstateViewModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class EstateMapperImpl implements EstateMapper
{
    private final FileUploadService fileUploadService;
    @Override
    public Estate EstateVMToEstate(EstateViewModel estateViewModel) {
        Estate estate = new Estate();
        estate.setName(estateViewModel.getName());
        estate.setDescription((estateViewModel.getDescription()));
        estate.setPrice(estateViewModel.getPrice());
        estate.setSize(estateViewModel.getSize());
        estate.setImageName(estateViewModel.getName()+".jpg");

        try {
            fileUploadService.saveFile(estateViewModel.getFile(), estate.getImageName(), "C:" + File.separator + "SpringBoot Projets" + File.separator + "estate project" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return estate;
    }

    @Override
    public EstateViewModel EstateToEstateVM(Estate estate) {
        EstateViewModel estateVM = new EstateViewModel();
        estateVM.setId(estate.getId());
        estateVM.setName(estate.getName());
        estateVM.setDescription(estate.getDescription());
        estateVM.setPrice(estate.getPrice());
        estateVM.setSize(estate.getSize());
        estateVM.setImageName(estate.getImageName());

        if(estate.getStatus() == EstateStatus.Available) {
            estateVM.setStatusClass("w3-green");
            estateVM.setStatus("Available");
        }
        else if(estate.getStatus() == EstateStatus.Hot) {
            estateVM.setStatusClass("w3-orange");
            estateVM.setStatus("Hot");
        }
        else {
            estateVM.setStatusClass("w3-gray");
            estateVM.setStatus("Unavailable");
        }

        return estateVM;
    }
}
