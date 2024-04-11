package com.estate.demo.mappers;
import com.estate.demo.models.Estate;
import com.estate.demo.viewModels.EstateViewModel;

public interface EstateMapper {
    public Estate EstateVMToEstate(EstateViewModel estateViewModel);

    public EstateViewModel EstateToEstateVM(Estate estate);
}
