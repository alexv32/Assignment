package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.Task;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ITaskService {
    public ResponseEntity<Task> getRandomTask();
    public ResponseEntity<List<Task>> getUncompletedTask(String user);

}
