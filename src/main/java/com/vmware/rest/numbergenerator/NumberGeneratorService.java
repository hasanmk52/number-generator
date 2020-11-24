package com.vmware.rest.numbergenerator;

import com.vmware.rest.numbergenerator.request.GenerateRequestDto;

import java.util.List;

public interface NumberGeneratorService {

    String generateSequence(GenerateRequestDto request);

    String getSequenceStatus(String uuid);

    List<String> getSequenceResult(String uuid);

    String bulkGenerateSequence(List<GenerateRequestDto> requestList);

}
