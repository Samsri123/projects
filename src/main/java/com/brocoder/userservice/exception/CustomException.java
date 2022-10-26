package com.brocoder.userservice.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomException extends RuntimeException{
    String errorCode;
    public CustomException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
