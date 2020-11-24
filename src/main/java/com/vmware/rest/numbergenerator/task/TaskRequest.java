package com.vmware.rest.numbergenerator.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "task_request")
@EqualsAndHashCode(exclude = {"id", "tasks", "create_date", "update_date"})
public class TaskRequest {

    public enum Status {
        IN_PROGRESS, SUCCESS, ERROR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name="request_id", unique = true, nullable = false)
    @Getter @Setter
    private String requestId;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    @Getter @Setter
    private Status status;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "taskRequest", orphanRemoval = true)
    @Getter
    private Set<Task> tasks;

    @Column(name="error_message")
    @Getter @Setter
    private String errorMessage;

    @Column(name="create_date")
    @Getter
    private LocalDateTime createDate;

    @Column(name="update_date")
    @Getter @Setter
    private LocalDateTime updateDate;

    protected TaskRequest() { }

    public TaskRequest(String requestId, Status status) {
        this.requestId = requestId;
        this.status = status;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public void setTasks(final Set<Task> tasks) {
        this.tasks = tasks;
        tasks.forEach(task -> task.setTaskRequest(this));
    }

    public boolean inProgress() {
        return status == Status.IN_PROGRESS;
    }

    public boolean isCompleted() {
        return status == Status.SUCCESS;
    }
}
