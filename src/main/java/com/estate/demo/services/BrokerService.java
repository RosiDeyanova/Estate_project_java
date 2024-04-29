package com.estate.demo.services;

import com.estate.demo.mappers.BrokerMapper;
import com.estate.demo.models.Broker;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.BrokerRepository;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.viewModels.BrokerViewModel;
import com.estate.demo.viewModels.EstateViewModel;
import com.estate.demo.viewModels.PageData;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BrokerService {

    private final BrokerRepository brokerRepository;
    private final BrokerMapper brokerMapper;

    @Transactional
    public PageData<BrokerViewModel> getAllBrokers(Integer page) {
        Pageable pageable = PageRequest.of(page, 12);
        Page<Broker> pageEstates = brokerRepository.findAll(pageable);
        int totalPages = pageEstates.getTotalPages();

        List<Integer>pagenumbers = new ArrayList<Integer>();
        for (int i = 1; i <= totalPages; i++) {
            pagenumbers.add(i);
        }

        List<BrokerViewModel> pageBrokersVm = new ArrayList<>();
        for (int i = 0; i < pageEstates.getContent().size(); i++) {
            BrokerViewModel estateVm = brokerMapper.BrokerToBrokerVM(pageEstates.getContent().get(i));
            pageBrokersVm.add(estateVm);
        }
        //pageEstatesVm.reversed();
        //getting the last added estates first - not working

        PageData<BrokerViewModel> pageData = new PageData<>();
        pageData.vmData = pageBrokersVm;
        pageData.currentPageNumber = page;
        pageData.pageNumbers = totalPages;
        return pageData;
    }
}
