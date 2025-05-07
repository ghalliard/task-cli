package com.gmhdev.taskcli.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gmhdev.taskcli.exception.BusinessException;
import com.gmhdev.taskcli.models.Task;
import com.gmhdev.taskcli.service.FileManagerService;

public class FileManagerServiceImpl implements FileManagerService{
    private static final String USER_HOME = System.getProperty("user.home");
    private final File taskFile = new File(USER_HOME + "/data/tasks.json");
    private final File tokenFile = new File(USER_HOME + "/data/token.txt");
    private final ObjectMapper objectMapper;

    public FileManagerServiceImpl(){
        String encodedString = Base64.getEncoder().encodeToString("0".getBytes());
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try{
            File mainDir = this.taskFile.getParentFile();

            if(!mainDir.exists()){
                mainDir.mkdir();

                this.taskFile.createNewFile();

                FileWriter writer = new FileWriter(taskFile, false);
                writer.write("[]");
                writer.close();

                this.tokenFile.createNewFile();

                FileWriter writer2 = new FileWriter(tokenFile, false);
                writer2.write(encodedString);
                writer2.close();
            }
        } catch(IOException exception){
            System.out.println("Ocurrio un error al iniciar el file manager: " + exception.getMessage());
        }
        
    }

    @Override
    public List<Task> obtenerDatos() throws BusinessException {
        try{
            byte[] fileBytes = Files.readAllBytes(taskFile.toPath());
            String jsonString = new String(fileBytes);
            List<Task> tasks = objectMapper.readValue(jsonString, new TypeReference<List<Task>>() {});
            return tasks;
        } catch(Exception ex){
            throw new BusinessException("Error al obtener datos", ex);
        }
    }

    @Override
    public void guardarDatos(List<Task> tasks) {
        try{
            String json = this.objectMapper.writeValueAsString(tasks);
            FileWriter writer = new FileWriter(taskFile, false);
            writer.write(json);
            writer.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
    }

    @Override
    public void guardarToken(long token) {
        try{
            String tokenString = String.valueOf(token);
            String encodedToken = Base64.getEncoder().encodeToString(tokenString.getBytes());
            FileWriter writer = new FileWriter(tokenFile, false);
            writer.write(encodedToken);
            writer.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public long obtenerToken() {
        try{
            byte[] fileBytes = Files.readAllBytes(tokenFile.toPath());
            byte[] decodedBytes = Base64.getDecoder().decode(new String(fileBytes).getBytes());
            String decodedString = new String(decodedBytes);
            return Long.valueOf(decodedString);
        } catch(Exception ex){
            throw new BusinessException("Error al obtener datos", ex);
        }
    }

}
