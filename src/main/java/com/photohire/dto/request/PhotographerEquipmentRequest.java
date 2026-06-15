package com.photohire.dto.request;

import com.photohire.enums.EquipmentType;
import lombok.Data;

@Data
public class PhotographerEquipmentRequest {

    private Long photographerProfileId;

    private EquipmentType equipmentType;

    private String brand;

    private String model;
}