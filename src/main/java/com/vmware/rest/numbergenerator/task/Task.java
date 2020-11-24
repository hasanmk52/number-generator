package com.vmware.rest.numbergenerator.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "task")
@EqualsAndHashCode(exclude = {"id", "taskRequest"})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name ="task_request_id", nullable = false)
    @Getter @Setter
    private TaskRequest taskRequest;

    @Column(name="goal", nullable = false)
    @Getter @Setter
    private Integer goal;

    @Column(name="step", nullable = false)
    @Getter @Setter
    private Integer step;

    @Column(name="result")
    @Getter @Setter
    private String result;

    protected Task() { }

    public Task(Integer goal, Integer step) {
        this.goal = goal;
        this.step = step;
    }
}