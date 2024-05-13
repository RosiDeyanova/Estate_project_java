package com.estate.demo.viewModels;

import lombok.Data;

import java.util.UUID;

@Data
public class BrokerViewModel {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Integer estatesCount;
}
