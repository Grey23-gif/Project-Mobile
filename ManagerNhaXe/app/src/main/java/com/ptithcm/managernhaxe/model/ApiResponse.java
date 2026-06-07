package com.ptithcm.managernhaxe.model;

public class ApiResponse<T> {

    public boolean success;
    public String message;
    public String token;
    public T data;

}