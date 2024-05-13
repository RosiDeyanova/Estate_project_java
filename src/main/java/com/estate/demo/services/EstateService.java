package com.estate.demo.services;

import com.estate.demo.mappers.EstateMapper;
import com.estate.demo.models.Broker;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.BrokerRepository;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.viewModels.EstateViewModel;
import com.estate.demo.viewModels.PageData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EstateService {
    private final EstateRepository estateRepository;
    private final EstateMapper estateMapper;
    private final BrokerRepository brokerRepository;
    private final FileUploadService fileUploadService;

    public PageData<EstateViewModel> getAllEstatesOfBrokerWithSearch(Integer page, String searchTerm, UUID brokerId){
        Pageable pageable = PageRequest.of(page, 12);
        Broker broker = brokerRepository.findBrokerById(brokerId);
        Page<Estate> estatesOfBroker = estateRepository.findAllByBroker(broker, pageable);
        if(searchTerm != null)
        {
            estatesOfBroker = estateRepository.findAllByBrokerAndNameContainingIgnoreCase(broker, searchTerm, pageable);
        }
        int totalPages = estatesOfBroker.getTotalPages();

        List<Integer> pagenumbers = new ArrayList<Integer>();
        for (int i = 1; i <= totalPages; i++) {
            pagenumbers.add(i);
        }

        List<EstateViewModel> pageBrokersVm = new ArrayList<>();
        for (int i = 0; i < estatesOfBroker.getContent().size(); i++) {
            EstateViewModel estateVm = estateMapper.EstateToEstateVM(estatesOfBroker.getContent().get(i));
            pageBrokersVm.add(estateVm);
        }

        PageData<EstateViewModel> pageData = new PageData<>();
        pageData.vmData = pageBrokersVm;
        pageData.currentPageNumber = page;
        pageData.pageNumbers = totalPages;
        return pageData;
    }

    public void editEstate (EstateViewModel estateVM, UUID id)
    {
        Estate estate = estateRepository.findEstateById(id);
        if (estateVM.getName() != null) {
            estate.setName(estateVM.getName());
        }
        if (estateVM.getDescription() != null) {
            estate.setDescription(estateVM.getDescription());
        }
        if (estateVM.getPrice() != null) {
            estate.setPrice(estateVM.getPrice());
        }
        if (estateVM.getSize() != null) {
            estate.setSize(estateVM.getSize());
        }
        if (estateVM.getName() != null) {
            estate.setImageName(estateVM.getName() + ".jpg");
        }

        if (estateVM.getFile().getSize() != 0){
        try {
            fileUploadService.saveFile(estateVM.getFile(), estate.getImageName(), "C:" + File.separator + "SpringBoot Projets" + File.separator + "estate project" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }}

        estateRepository.save(estate);
    }
}
