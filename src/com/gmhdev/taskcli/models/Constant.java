package com.gmhdev.taskcli.models;

public final class Constant {
    private Constant(){}
    
    public enum TaskStatus{
        TO_DO(1, "todo"),
        IN_PROGRESS(2, "in-progress"),
        DONE(3, "done")
        ;

        private Integer code;
        private String description;

        private TaskStatus(int code, String description){
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        
    }
}
