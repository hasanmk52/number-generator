package com.vmware.rest.numbergenerator.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BulkResultResponse {

    private List<String> results;

    protected BulkResultResponse() { }
}
