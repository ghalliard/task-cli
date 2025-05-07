package com.gmhdev.taskcli.service;

import com.gmhdev.taskcli.models.Task;

public interface TaskService {
    Task addTask(String taskName);
    Task updateTaskName(long id, String newTaskName);
    void deleteTask(long id);
}
