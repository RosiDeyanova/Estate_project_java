package com.estate.demo.services;

import com.estate.demo.repositories.EstateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EstateServiceImpl implements EstateService{

    private final EstateRepository estateRepository;


}
