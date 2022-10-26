package com.brocoder.userservice.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {
    String errorCode;
    public UserNotFoundException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
