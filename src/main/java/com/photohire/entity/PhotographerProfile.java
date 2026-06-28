package com.photohire.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "photographer_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Bio is required")
    @Column(length = 1000)
    private String bio;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal dailyRate;

    @NotNull
    @PositiveOrZero
    private Integer yearsOfExperience;

    @NotNull
    private Boolean available;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

}
