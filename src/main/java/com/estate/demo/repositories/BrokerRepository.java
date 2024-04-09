package com.estate.demo.repositories;

import com.estate.demo.models.Broker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrokerRepository extends JpaRepository<Broker, UUID> {
}
