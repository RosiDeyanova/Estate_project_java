package com.estate.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.REMOVE;

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
    private String password;

    @OneToMany(cascade=REMOVE,mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Estate> estatesBought = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name = "customer_estate",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "estate_id"))
    private Set<Estate> estatesLiked = new HashSet<>();

}
