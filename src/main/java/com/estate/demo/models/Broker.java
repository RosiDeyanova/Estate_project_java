package com.estate.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    @OneToMany(mappedBy = "broker", fetch = FetchType.EAGER)
    //because of fetch = FetchType.EAGER it is not fetching anything
    private Set<Estate> estates = new HashSet<>();
}
