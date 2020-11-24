package com.vmware.rest.numbergenerator.task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class TaskExecutor extends TimerTask {

    private TaskRequest taskRequest;
    private TaskRequestRepository taskRequestRepository;


    public TaskExecutor(TaskRequest taskRequest, TaskRequestRepository taskRequestRepository) {
        this.taskRequest = taskRequest;
        this.taskRequestRepository = taskRequestRepository;
    }
    public void run() {
        Set<Task> tasks = new HashSet<>();
        for(Task task : taskRequest.getTasks()) {
            String result = "";
            for(int i= task.getGoal(); i >= 0; i -= task.getStep()) {
                result += i+",";
            }
            task.setResult(result.substring(0, result.length() - 1));
            tasks.add(task);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!taskRequest.getTasks().stream().map(Task::getResult).collect(Collectors.toList()).contains(null)) {
            taskRequest.setStatus(TaskRequest.Status.SUCCESS);
            taskRequest.setUpdateDate(LocalDateTime.now());
            taskRequest.setTasks(tasks);
            taskRequestRepository.save(taskRequest);
        }
    }
}
