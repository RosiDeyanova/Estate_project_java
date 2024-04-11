package com.estate.demo.repositories;

import com.estate.demo.enums.EstateStatus;
import com.estate.demo.models.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

public interface EstateRepository extends JpaRepository<Estate, UUID> {

//    public List<Estate> findAllByNameContainingIgnoreCaseAndStatusGreaterThan(String name, EstateStatus status);
}
