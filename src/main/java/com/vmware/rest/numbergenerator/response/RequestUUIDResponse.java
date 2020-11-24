package com.vmware.rest.numbergenerator.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestUUIDResponse {
    private String task;

    protected RequestUUIDResponse() { }
}