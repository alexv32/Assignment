package com.sysaid.assignment.repositories;
import com.sysaid.assignment.domain.*;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUserAndCompletedFalse(User user);
    List<Task> findByUser(User user);
    List<Task> findByUserAndCompletedTrue(User user);
    List<Task> findByUserAndWishlistFalse(User user);
    List<Task> findByUserAndWishlistTrue(User user);
    Task findByKey(String key);
    void deleteByKey(String key);
}
