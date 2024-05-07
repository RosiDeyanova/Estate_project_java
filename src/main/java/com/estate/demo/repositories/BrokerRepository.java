package com.estate.demo.repositories;

import com.estate.demo.models.Broker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BrokerRepository extends JpaRepository<Broker, UUID> {
    public Page<Broker> findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);
}
