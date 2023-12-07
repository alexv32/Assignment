package com.sysaid.assignment.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sysaid.assignment.domain.User;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u")
    long countUser();
}
