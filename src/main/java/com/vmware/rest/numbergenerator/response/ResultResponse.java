package com.vmware.rest.numbergenerator.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponse {

    private String result;

    protected ResultResponse() { }
}
