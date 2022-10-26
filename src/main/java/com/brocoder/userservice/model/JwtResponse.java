package com.brocoder.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String message;
    private String token;
    private String role;
}
