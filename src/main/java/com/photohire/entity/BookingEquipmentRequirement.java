package com.photohire.entity;
import com.photohire.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_equipment_requirements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEquipmentRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    private String brand;

    private String model;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}