package com.photohire.dto.response;

import com.photohire.enums.EquipmentType;
import lombok.Data;

@Data
public class BookingEquipmentRequirementResponse {

    private Long id;

    private EquipmentType equipmentType;

    private Integer quantity;
}