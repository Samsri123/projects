package com.brocoder.userservice.service;

import com.brocoder.userservice.constants.ErrorCodes;
import com.brocoder.userservice.constants.SecurityConstants;
import com.brocoder.userservice.entity.User;
import com.brocoder.userservice.exception.CustomException;
import com.brocoder.userservice.exception.JwtException;
import com.brocoder.userservice.jwt.CustomerUserDetailsService;
import com.brocoder.userservice.jwt.JwtFilter;
import com.brocoder.userservice.jwt.JwtUtil;
import com.brocoder.userservice.model.UserRequest;
import com.brocoder.userservice.model.UserResponse;
import com.brocoder.userservice.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.brocoder.userservice.constants.SecurityConstants.ACCESS_DENIED_MESSAGE;

@Service
@Log4j2
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public Long signUp(UserRequest userRequest) {
        log.info("Inside signUp {}",userRequest);
        User newUser;
        if(checkUserExists(userRequest.getEmailID())) {
            newUser = User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmailID())
                    .password(bcryptPasswordEncoder.encode(userRequest.getPassword()))
                    .phone(userRequest.getPhone())
                    .work(userRequest.getWork())
                    .role("user")
                    .status("hold")
                    .build();
            userRepository.save(newUser);
        }else{
            throw new CustomException("User already exists with the given E-mail :"+userRequest.getEmailID(), ErrorCodes.USER_ALREADY_EXISTS);
        }

            return newUser.getId();

    }

    public boolean checkUserExists(String email) {
        log.info("Inside checkUserExists {}",email);
       User user = userRepository.findByEmail(email);
        return Objects.isNull(user);
    }

    @Override
    public String login(Map<String, String> requestMap) {
        log.info("Inside login{} ",requestMap);
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    throw new JwtException("login successful",jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(),customerUserDetailsService.getUserDetails().getRole()),customerUserDetailsService.getUserDetails().getRole());
                } else {
                    throw  new CustomException("wait for the admin approval","WAIT_FOR_APPROVAL");
                }

            }else {
                throw new CustomException("Bad Credentials","INVALID_CREDENTIALS");
            }

        
    }

    @Override
    public List<UserResponse> getAllUsers() {
        if(jwtFilter.isAdmin()) {
            List<User> users = userRepository.findAll();
            List<UserResponse> userResponseList = new ArrayList<>();

            Spliterator<User> spl = users.spliterator();
            spl.forEachRemaining((user) -> {
                userResponseList.add(UserResponse.builder()
                        .name(user.getName())
                        .emailID(user.getEmail())
                        .phone(user.getPhone())
                        .work(user.getWork())
                        .build());
            });
            return userResponseList;
        }else{
            throw new CustomException(ACCESS_DENIED_MESSAGE,"UNAUTHORIZED");
        }
    }

    @Override
    public String deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
        return "User deletetd with email: "+email;
    }
}
