package com.sysaid.assignment.repositories;
import com.sysaid.assignment.domain.*;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    /**
     * all the repository calles, most get a user object to get each user tasks
     * @param user
     * @return
     */
    List<Task> findByUserAndCompletedFalse(User user);
    List<Task> findByUser(User user);
    List<Task> findByUserAndCompletedTrue(User user);
    List<Task> findByUserAndWishlistFalse(User user);
    List<Task> findByUserAndWishlistTrue(User user);
    List<Task> findByUserAndCompletedFalseAndType(User user, String type);
    Task findByKey(String key);
    void deleteByKey(String key);
}
