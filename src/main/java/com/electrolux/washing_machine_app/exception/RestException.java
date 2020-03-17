package com.electrolux.washing_machine_app.exception;

public class RestException extends Exception {
    public RestException(String errorMessage) {
        super(errorMessage);
    }
}
