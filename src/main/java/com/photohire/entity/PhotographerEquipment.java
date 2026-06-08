package com.photohire.entity;
import com.photohire.enums.EquipmentType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photographer_equipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    private String brand;

    private String model;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "photographer_profile_id")
    private PhotographerProfile photographerProfile;
}