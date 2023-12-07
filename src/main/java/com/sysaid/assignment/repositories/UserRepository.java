package com.sysaid.assignment.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.sysaid.assignment.domain.User;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);
}
