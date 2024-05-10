package com.estate.demo.services;

import com.estate.demo.mappers.CustomerMapper;
import com.estate.demo.mappers.EstateMapper;
import com.estate.demo.models.Broker;
import com.estate.demo.models.Customer;
import com.estate.demo.models.Estate;
import com.estate.demo.repositories.CustomerRepository;
import com.estate.demo.repositories.EstateRepository;
import com.estate.demo.viewModels.BrokerViewModel;
import com.estate.demo.viewModels.EstateViewModel;
import com.estate.demo.viewModels.PageData;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EstateRepository estateRepository;
    private final EstateMapper estateMapper;

    @Transactional
    public PageData<EstateViewModel> getAllCustomerEstates(UUID customerId, Integer page, String searchTerm) {
        Pageable pageable = PageRequest.of(page, 12);
        Customer customer = customerRepository.findCustomerById(customerId);
        Page<Estate> pageEstates = estateRepository.findAllByCustomerAndNameContainingIgnoreCase(customer,searchTerm,pageable);
        if(searchTerm == null || searchTerm.isEmpty())
        {
            pageEstates = estateRepository.findAllByCustomer(customer,pageable);
        }
        int totalPages = pageEstates.getTotalPages();

        List<Integer> pagenumbers = new ArrayList<Integer>();
        for (int i = 1; i <= totalPages; i++) {
            pagenumbers.add(i);
        }

        List<EstateViewModel> pageBrokersVm = new ArrayList<>();
        for (int i = 0; i < pageEstates.getContent().size(); i++) {
            EstateViewModel estateVm = estateMapper.EstateToEstateVM(pageEstates.getContent().get(i));
            pageBrokersVm.add(estateVm);
        }

        PageData<EstateViewModel> pageData = new PageData<>();
        pageData.vmData = pageBrokersVm;
        pageData.currentPageNumber = page;
        pageData.pageNumbers = totalPages;
        return pageData;
    }
}
