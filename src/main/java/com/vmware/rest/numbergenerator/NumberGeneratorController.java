package com.vmware.rest.numbergenerator;

import com.vmware.rest.numbergenerator.request.GenerateRequestDto;
import com.vmware.rest.numbergenerator.response.BulkResultResponse;
import com.vmware.rest.numbergenerator.response.RequestUUIDResponse;
import com.vmware.rest.numbergenerator.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class NumberGeneratorController {

    @Autowired
    private NumberGeneratorService numberGeneratorService;

    @PostMapping(value = "/generate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RequestUUIDResponse generateSequence(@RequestBody GenerateRequestDto request) {
        if(request.getStep() > request.getGoal()) {
            throw new IllegalArgumentException("step value should be less than goal");
        }
        String uuid = numberGeneratorService.generateSequence(request);
        return new RequestUUIDResponse(uuid);
    }

    @GetMapping(value = "/tasks/{taskId}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResultResponse getTaskStatus(@PathVariable String taskId) {
        String status = numberGeneratorService.getSequenceStatus(taskId);
        return new ResultResponse(status);
    }

    @GetMapping(value = "/tasks/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResultResponse getSequenceResult(@PathVariable String taskId, @RequestParam(defaultValue = "get_numlist") String action) {
        String result = numberGeneratorService.getSequenceResult(taskId).stream().findFirst().orElse("");
        return new ResultResponse(result);
    }

    @PostMapping(value = "/bulkGenerate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RequestUUIDResponse bulkGenerateSequence(@RequestBody List<GenerateRequestDto> requestList) {
        String uuid = numberGeneratorService.bulkGenerateSequence(requestList);
        return new RequestUUIDResponse(uuid);
    }

    @GetMapping(value = "/bulk/tasks/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BulkResultResponse getBulkSequenceResult(@PathVariable String taskId, @RequestParam(defaultValue = "get_numlist") String action) {
        return new BulkResultResponse(numberGeneratorService.getSequenceResult(taskId));
    }
}