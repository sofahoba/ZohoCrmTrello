package com.flakTechTask.zohoCrmTrello.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
