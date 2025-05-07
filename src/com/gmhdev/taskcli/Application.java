package com.gmhdev.taskcli;
import com.gmhdev.taskcli.exception.BusinessException;
import com.gmhdev.taskcli.models.Task;
import com.gmhdev.taskcli.service.TaskService;
import com.gmhdev.taskcli.service.impl.TaskServiceImpl;

public class Application {
    private static final TaskService taskService = new TaskServiceImpl();

    public static void main(String[] args) {
        if(args.length == 0) showHelp();
        else taskAction(args);
    }

    public static void taskAction(String[] args){
        try{
            switch (args[0]) {
                case "add":
                    Task task = taskService.addTask(args[1]);
                    System.out.println("Tarea creada con exito");
                    System.out.println(task);
                    break;
                
                case "update":
                    Task updatedTask = taskService.updateTaskName(Long.valueOf(args[1]), args[2]); 
                    System.out.println("Tarea actualizada con exito");
                    System.out.println(updatedTask);
                    break;
    
                case "delete":
                    taskService.deleteTask(Long.valueOf(args[1]));
                    System.out.println("Tarea borrada con exito");
                    break;
    
                default:
                    showHelp();
            }
        } catch(BusinessException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void showHelp(){
        System.out.println("Debe seleccionar las siguientes opciones:");
        System.out.println("add <nombre_tarea>");
        System.out.println("update <id_tarea> <nuevo_nombre_tarea>");
        System.out.println("delete <id_tarea>");
    }
}
