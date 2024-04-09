package com.estate.demo.models;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private String propertyName;
    private String description;
    private Double price;
    private Integer size;
    private String realtorEmail;
    private String imageName;

    @ManyToMany(mappedBy = "estates")
    private Set<Customer> customers = new HashSet<>();


}
