package com.brocoder.userservice.repository;

import com.brocoder.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    String FIND_USER_DETAILS="SELECT name,email, phone,work FROM user";
    @Query(value = FIND_USER_DETAILS,nativeQuery = true)
    List<Object> getAllUsers();

    void deleteByEmail(String email);
}
