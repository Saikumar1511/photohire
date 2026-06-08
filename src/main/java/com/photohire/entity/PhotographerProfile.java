package com.photohire.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "photographer_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @Column(length = 1000)
    private String bio;

    private BigDecimal dailyRate;

    private Integer yearsOfExperience;

    private Boolean available;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
}
