package com.photohire.dto.response;

import com.photohire.enums.EquipmentType;
import lombok.Data;

@Data
public class PhotographerEquipmentResponse {

    private Long id;

    private EquipmentType equipmentType;

    private String brand;

    private String model;
}