package com.brocoder.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtException extends RuntimeException{

    String token;
    String role;
    public  JwtException(String message,String token,String role){
        super(message);this.token=token;
        this.role=role;
    }
}
