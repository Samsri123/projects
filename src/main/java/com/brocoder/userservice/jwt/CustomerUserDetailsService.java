package com.brocoder.userservice.jwt;

import com.brocoder.userservice.constants.ErrorCodes;
import com.brocoder.userservice.exception.UserNotFoundException;
import com.brocoder.userservice.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Service
@Log4j2
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    private com.brocoder.userservice.entity.User userDetails;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}",username);
        userDetails = userRepository.findByEmail(username);
        log.info("loadUSersByUSername {}",userDetails);
        if(!Objects.isNull(userDetails)){
            return new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());
        }else{
            throw new UserNotFoundException("User not found.", ErrorCodes.USER_NOT_FOUND);
        }
    }
    public com.brocoder.userservice.entity.User getUserDetails(){
        return userDetails;
    }
}
