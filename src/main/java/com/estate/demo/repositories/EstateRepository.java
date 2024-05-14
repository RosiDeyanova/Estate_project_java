package com.estate.demo.repositories;

import com.estate.demo.models.Broker;
import com.estate.demo.models.Customer;
import com.estate.demo.models.Estate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface EstateRepository extends JpaRepository<Estate, UUID>, PagingAndSortingRepository<Estate, UUID> {


    public Page<Estate> findAllByNameContainingIgnoreCase(String estateName, Pageable pageable);
    public Page<Estate> findAllByBroker(Broker broker, Pageable pageable);
    public List<Estate> findAllByBroker(Broker broker);
    public Page<Estate> findAllByBrokerAndNameContainingIgnoreCase(Broker broker, String searchTerm,Pageable pageable);
    public Estate findEstateById(UUID id);
    public Page<Estate> findAllByCustomerAndNameContainingIgnoreCase(Customer customer, String searchTerm, Pageable pageable);
    public Page<Estate> findAllByCustomer(Customer customer, Pageable pageable);
    public Boolean existsEstateByIdAndCustomersLikedContains(UUID id, Customer customer);
    public void deleteById(UUID id);
    public boolean existsById(UUID id);

}
