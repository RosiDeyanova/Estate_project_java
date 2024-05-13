package com.estate.demo.repositories;

import com.estate.demo.models.Customer;
import com.estate.demo.models.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    public Customer findCustomerByEmail(String email);
    public Customer findCustomerById(UUID id);
    public Boolean existsCustomerByIdAndEstatesLikedContaining(UUID id, Estate estate);
}
