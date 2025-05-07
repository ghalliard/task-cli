package com.gmhdev.taskcli.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import com.gmhdev.taskcli.exception.BusinessException;
import com.gmhdev.taskcli.models.Task;
import com.gmhdev.taskcli.service.FileManagerService;
import com.gmhdev.taskcli.service.TaskService;

import static com.gmhdev.taskcli.models.Constant.TaskStatus;

public class TaskServiceImpl implements TaskService{
    private final FileManagerService fileManagerService;
    private final List<Task> tasks;

    public TaskServiceImpl(){
        fileManagerService = new FileManagerServiceImpl();
        tasks = fileManagerService.obtenerDatos();
    }

    @Override
    public Task addTask(String taskName) {
        long id = this.fileManagerService.obtenerToken() + 1;
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task((long) id, taskName, TaskStatus.TO_DO.getDescription(), now, now);
        tasks.add(task);
        this.fileManagerService.guardarDatos(tasks);
        this.fileManagerService.guardarToken(id);
        return task;
    }

    @Override
    public Task updateTaskName(long id, String newTaskName) {
        int index = IntStream.range(0, tasks.size())
            .filter(indice -> tasks.get(indice).getId() == id)
            .findFirst()
            .orElseThrow(() -> new BusinessException("ID no encontrado"));

        Task taskToUpdate = tasks.get(index);
        taskToUpdate.setDescription(newTaskName);
        taskToUpdate.setUpdatedAt(LocalDateTime.now());

        tasks.set(index, taskToUpdate);
        this.fileManagerService.guardarDatos(tasks);
        return taskToUpdate;
    }

    @Override
    public void deleteTask(long id) {
        int index = IntStream.range(0, tasks.size())
            .filter(indice -> tasks.get(indice).getId() == id)
            .findFirst()
            .orElseThrow(() -> new BusinessException("ID no encontrado"));
        
        tasks.remove(index);

        this.fileManagerService.guardarDatos(tasks);
    }

}
