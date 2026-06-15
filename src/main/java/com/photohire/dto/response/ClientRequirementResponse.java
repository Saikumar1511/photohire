package com.photohire.dto.response;

import lombok.Data;

@Data
public class ClientRequirementResponse {

    private Long id;

    private Integer numberOfPhotographers;

    private String photographyStyle;
}