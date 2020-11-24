package com.vmware.rest.numbergenerator;

import com.vmware.rest.numbergenerator.request.GenerateRequestDto;
import com.vmware.rest.numbergenerator.response.RequestUUIDResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NumberGeneratorApplicationTests {

    @Autowired
    private NumberGeneratorController numberGeneratorController;

    private GenerateRequestDto generateRequestDto;
    private GenerateRequestDto generateRequestDto1;

    @BeforeEach
    public void setUp() {
        generateRequestDto = new GenerateRequestDto(10, 2);
        generateRequestDto1 = new GenerateRequestDto(100, 3);
    }

    @Test
    public void testGenerate() {
        RequestUUIDResponse requestUUIDResponse = numberGeneratorController.generateSequence(generateRequestDto);
        assertNotNull(requestUUIDResponse);
    }

    @Test
    public void testTaskStatus() {
        RequestUUIDResponse requestUUIDResponse = numberGeneratorController.generateSequence(generateRequestDto);
        assertNotNull(numberGeneratorController.getTaskStatus(requestUUIDResponse.getTask()));
    }

    @Test
    public void testGetSequenceResult() {
        RequestUUIDResponse requestUUIDResponse = numberGeneratorController.generateSequence(generateRequestDto);
        assertNotNull(numberGeneratorController.getSequenceResult(requestUUIDResponse.getTask(),"get_numlist"));
    }

    @Test
    public void testBulkGenerateSequence() {
        RequestUUIDResponse requestUUIDResponse = numberGeneratorController.bulkGenerateSequence(Arrays.asList(generateRequestDto, generateRequestDto1));
        assertNotNull(numberGeneratorController.getBulkSequenceResult(requestUUIDResponse.getTask(),"get_numlist"));
    }

}
