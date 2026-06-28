package com.photohire.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientRequirementRequest {

    @NotNull(message = "Required camera count is required")
    @PositiveOrZero
    private Integer requiredCameraCount;

    @NotNull(message = "Required lens count is required")
    @PositiveOrZero
    private Integer requiredLensCount;

    private String specialNotes;
}