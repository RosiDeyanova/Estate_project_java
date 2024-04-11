package com.estate.demo.mappers;


import com.estate.demo.enums.EstateStatus;
import com.estate.demo.models.Estate;
import com.estate.demo.viewModels.EstateViewModel;
import org.springframework.stereotype.Service;

@Service
public class EstateMapperImpl implements EstateMapper
{
    @Override
    public Estate EstateVMToEstate(EstateViewModel estateViewModel) {
        Estate estate = new Estate();
        estate.setName(estateViewModel.getName());
        estate.setDescription((estateViewModel.getDescription()));
        estate.setPrice(estateViewModel.getPrice());
        estate.setSize(estateViewModel.getSize());

        return null;
    }

    @Override
    public EstateViewModel EstateToEstateVM(Estate estate) {
        EstateViewModel estateVM = new EstateViewModel();
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
            estateVM.setStatusClass("w3-green");
            estateVM.setStatus("Unavailable");
        }

        return estateVM;
    }
}
