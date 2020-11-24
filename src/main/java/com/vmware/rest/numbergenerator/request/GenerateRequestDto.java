package com.vmware.rest.numbergenerator.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenerateRequestDto {

    private Integer goal;
    private Integer step;

    protected GenerateRequestDto() {

    }
}
