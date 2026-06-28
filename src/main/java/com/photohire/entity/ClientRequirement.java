package com.photohire.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "client_requirements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequirement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Required camera count is required")
    @PositiveOrZero(message = "Required camera count must be zero or greater")
    private Integer requiredCameraCount;

    @NotNull(message = "Required lens count is required")
    @PositiveOrZero(message = "Required lens count must be zero or greater")
    private Integer requiredLensCount;

    @Column(length = 1000)
    private String specialNotes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}