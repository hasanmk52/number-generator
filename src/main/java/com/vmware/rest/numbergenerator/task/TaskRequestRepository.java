package com.vmware.rest.numbergenerator.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRequestRepository extends JpaRepository<TaskRequest, Long> {

    @Query("SELECT tr FROM TaskRequest tr WHERE tr.requestId = ?1")
    TaskRequest findByRequestId(String requestId);

}
