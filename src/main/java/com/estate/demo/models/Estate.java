package com.estate.demo.models;

import com.estate.demo.enums.EstateStatus;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.REFRESH;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Estate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    @NotBlank
    private String name;
    private String description;
    private Double price;
    private Integer size;
    private String imageName;
    private EstateStatus status = EstateStatus.Available;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Broker broker;

    @ManyToMany(mappedBy = "estatesLiked", fetch = FetchType.EAGER)
    private Set<Customer> customersLiked = new HashSet<>();


}
