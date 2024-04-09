package com.estate.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    @ManyToMany
    @JoinTable(name = "estate_customer", joinColumns = @JoinColumn(name="estate_id"),inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Estate> estates = new HashSet<>();
}
