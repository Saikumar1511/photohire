package com.photohire.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client_requirements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer requiredCameraCount;

    private Integer requiredLensCount;

    @Column(length = 1000)
    private String specialNotes;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}