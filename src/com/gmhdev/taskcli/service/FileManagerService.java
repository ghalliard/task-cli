package com.gmhdev.taskcli.service;

import java.util.List;

import com.gmhdev.taskcli.exception.BusinessException;
import com.gmhdev.taskcli.models.Task;

public interface FileManagerService {
    List<Task> obtenerDatos() throws BusinessException;
    void guardarDatos(List<Task> tasks);
    void guardarToken(long token);
    long obtenerToken();
}
