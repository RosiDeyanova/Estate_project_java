package com.estate.demo.repositories;

import com.estate.demo.models.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstateRepository extends JpaRepository<Estate, UUID> {
}
