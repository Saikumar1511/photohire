package com.photohire.dto.request;

import com.photohire.enums.EquipmentType;
import lombok.Data;

@Data
public class BookingEquipmentRequirementRequest {

    private EquipmentType equipmentType;

    private Integer quantity;
}