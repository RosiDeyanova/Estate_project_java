package com.estate.demo.repositories;

import com.estate.demo.enums.EstateStatus;
import com.estate.demo.models.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

public interface EstateRepository extends JpaRepository<Estate, UUID>, PagingAndSortingRepository<Estate, UUID> {

//    public List<Estate> findAllByNameContainingIgnoreCaseAndStatusGreaterThan(String name, EstateStatus status);
}
