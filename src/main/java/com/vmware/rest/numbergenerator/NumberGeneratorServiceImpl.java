package com.vmware.rest.numbergenerator;

import com.vmware.rest.numbergenerator.request.GenerateRequestDto;
import com.vmware.rest.numbergenerator.task.Task;
import com.vmware.rest.numbergenerator.task.TaskExecutor;
import com.vmware.rest.numbergenerator.task.TaskRequestRepository;
import com.vmware.rest.numbergenerator.task.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class NumberGeneratorServiceImpl implements NumberGeneratorService {

    @Autowired
    private TaskRequestRepository taskRequestRepository;

    @Override
    public String generateSequence(GenerateRequestDto request) {
        String requestId = UUID.randomUUID().toString();
        TaskRequest taskRequest = new TaskRequest(requestId, TaskRequest.Status.IN_PROGRESS);
        Set<Task> tasks = new HashSet<>();
        tasks.add(new Task(request.getGoal(), request.getStep()));
        taskRequest.setTasks(tasks);
        try {
            taskRequest = taskRequestRepository.save(taskRequest);
            long startBound = (new Random().nextInt(25 - 10) + 10) * 1000;
            TaskExecutor taskExecutor = new TaskExecutor(taskRequest, taskRequestRepository);
            new Timer().schedule(taskExecutor, startBound);
        } catch (Exception e) {
            e.printStackTrace();
            taskRequest.setStatus(TaskRequest.Status.ERROR);
            taskRequest.setErrorMessage(e.getMessage());
            taskRequest.setUpdateDate(LocalDateTime.now());
            taskRequestRepository.save(taskRequest);
        }
        return requestId;
    }

    @Override
    public String getSequenceStatus(String uuid) {
        TaskRequest taskRequest = taskRequestRepository.findByRequestId(uuid);
        if(taskRequest == null) {
            throw new IllegalArgumentException("Invalid UUID sent. Please send a valid one.");
        }
        return taskRequest.getStatus().toString();
    }

    @Override
    public List<String> getSequenceResult(String uuid) {
        TaskRequest taskRequest = taskRequestRepository.findByRequestId(uuid);
        if(taskRequest == null) {
            throw new IllegalArgumentException("Invalid UUID sent. Please send a valid one.");
        }
        if(taskRequest.isCompleted()) {
            return taskRequest.getTasks().stream().map(Task::getResult).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public String bulkGenerateSequence(List<GenerateRequestDto> requestList) {
        String requestId = UUID.randomUUID().toString();
        //Run concurrently
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        TaskRequest taskRequest = new TaskRequest(requestId, TaskRequest.Status.IN_PROGRESS);
        Set<Task> tasks = new HashSet<>();
        requestList.forEach(request -> tasks.add(new Task(request.getGoal(), request.getStep())));
        taskRequest.setTasks(tasks);
        taskRequest = taskRequestRepository.save(taskRequest);
        TaskExecutor taskExecutor = new TaskExecutor(taskRequest, taskRequestRepository);
        long startBound = (new Random().nextInt(25 - 10) + 10);
        scheduledThreadPool.schedule(taskExecutor, startBound, TimeUnit.SECONDS);
        scheduledThreadPool.shutdown();
        return requestId;
    }
}
