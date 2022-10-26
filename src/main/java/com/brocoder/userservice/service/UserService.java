package com.brocoder.userservice.service;

import com.brocoder.userservice.entity.User;
import com.brocoder.userservice.model.UserRequest;
import com.brocoder.userservice.model.UserResponse;

import java.util.List;
import java.util.Map;


public interface UserService {
    Long signUp(UserRequest userRequest);

    boolean checkUserExists(String email);

    String login(Map<String, String> requestMap);

    List<UserResponse> getAllUsers();

    String deleteUserByEmail(String email);
}
