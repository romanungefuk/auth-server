package com.equp.back.backend.exception;

public class MoodNotFoundException extends RuntimeException{
    public MoodNotFoundException(String message) {
        super(message);
    }
}