package com.photohire.dto.request;

import lombok.Data;

@Data
public class ClientRequirementRequest {

    private Integer numberOfPhotographers;

    private String photographyStyle;
}