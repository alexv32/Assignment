package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.*;
import com.sysaid.assignment.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

/**
 * implementation of the task service interface
 */
@Service
public class TaskServiceImpl implements  ITaskService{

    @Value("${external.boredapi.baseURL}")
    private String baseUrl;

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,UserRepository userRepository){
        this.taskRepository=taskRepository;
        this.userRepository=userRepository;
    }
    /**
     * creating a random task to  show to the user
     */
    public ResponseEntity<Task> getRandomTask() {
        String endpointUrl = String.format("%s/activity", baseUrl);

        RestTemplate template = new RestTemplate();
        ResponseEntity<Task> responseEntity = template.getForEntity(endpointUrl, Task.class);

        return responseEntity;
    }
    
    /**
     * Gets and returns the uncompleted tasks for the user, capped at 10 as requested
     * If a type is sent it returns only tasks of that type
     * @param username,type
     * @return
     * @throws CustomException exception when there are no tasks of that type
     */
    public ResponseEntity<List<Task>> getUncompletedTask(String username, String type) throws CustomException{
        User user=userRepository.findByUsername(username);
        List<Task> incompleteTasks= new ArrayList<>();
        if (type==null) {
            incompleteTasks=taskRepository.findByUserAndCompletedFalse(user);
        }else{
            incompleteTasks=taskRepository.findByUserAndCompletedFalseAndType(user,type);
        }
        
        if(incompleteTasks.isEmpty()){
            throw new CustomException("No Uncompleted Tasks of that type");
        }else if(incompleteTasks.size()>=10){
            return new ResponseEntity<>(incompleteTasks.subList(0, 10),HttpStatus.OK);
        }
        
        return new ResponseEntity<>(incompleteTasks,HttpStatus.OK);
    }

    /**
     * Creating 10 random tasks from the api to add for the user, used in the initializtion process
     * @param username
     * @return
     */
    public List<Task> createTasksFromApi(String username){
        String endpointUrl = String.format("http://www.boredapi.com/api/activity/");
        List<Task> newTasks=new ArrayList<>();
        User user = userRepository.findByUsername(username);
        for(int i=0;i<10;i++){
                ResponseEntity<Task> responseEntity =new RestTemplate().getForEntity(endpointUrl, Task.class);
                Task task= new Task(responseEntity.getBody());
                task.setUser(user);
                newTasks.add(task);
            }
        
        taskRepository.saveAll(newTasks);
        return newTasks;
    }

    /**
     * gets a username and pulls from the database all completed tasks for the user
     * @param username
     * @return all completed tasks
     * @throws CustomException when no tasks are found
     */
    public ResponseEntity<List<Task>> getCompletedTask(String username) throws CustomException {
        User user= userRepository.findByUsername(username);
        List<Task> completeTasks=taskRepository.findByUserAndCompletedTrue(user);
        if(completeTasks.isEmpty()){
            throw new CustomException("No Completed Tasks");
        }
        return new ResponseEntity<>(completeTasks,HttpStatus.OK);
    }

    /**
     * gets a username and looks for him in the database, returning all that users wihslisted tasks
     * @param username
     * @return all wishlisted tasks
     * @throws CustomException
     */
    public ResponseEntity<List<Task>> getWishlistedTask(String username) throws CustomException {
        User user= userRepository.findByUsername(username);
        List<Task> wishlistedTasks=taskRepository.findByUserAndWishlistTrue(user);
        if(wishlistedTasks.isEmpty()){
            throw new CustomException("No Wishlisted Tasks");
        }
        return new ResponseEntity<>(wishlistedTasks,HttpStatus.OK);
    }

    /**
     * gets a user and returns unwishlisted tasks for that user
     * @param username
     * @return returns all tasks that are unwishlisted
     * @throws CustomException
     */
    public ResponseEntity<List<Task>> getUnWishlistedTask(String username) throws CustomException {
        User user= userRepository.findByUsername(username);
        List<Task> unwishlistedTasks=taskRepository.findByUserAndWishlistFalse(user);
        if(unwishlistedTasks.isEmpty()){
            throw new CustomException("No Unwishlisted Tasks");
        }
        return new ResponseEntity<>(unwishlistedTasks,HttpStatus.OK);
    }

    /**
     * gets a username and returns all that user tasks
     * @param username
     * @return all user tasks
     * @throws CustomException
     */
    public ResponseEntity<List<Task>> getAllTasks(String username) throws CustomException{
        User user= userRepository.findByUsername(username);
        List<Task> allTasks=taskRepository.findByUser(user);
        if(allTasks.isEmpty()){
            throw new CustomException("No Tasks");
        }
        return new ResponseEntity<>(allTasks,HttpStatus.OK);
    }

    /**
     * gets a username and creates a new task for him, tasks are automatically not completed not wishlisted and rated 0
     * @param username
     */
    public void createTask(String username){
        User user= userRepository.findByUsername(username);
        String endpointUrl = String.format("%s/activity", baseUrl);

        ResponseEntity<Task> responseEntity =new RestTemplate().getForEntity(endpointUrl, Task.class);
        Task task= new Task(responseEntity.getBody());
        task.setUser(user);
        taskRepository.save(task);

    }

    /**
     * Gets a username and a specific task key and deletes that task for the user
     * @param username,key
     */
    @Transactional
    public void deleteTask(String username,String key){
        User user= userRepository.findByUsername(username);
        Task task= taskRepository.findByKey(key);
        if(task!=null && task.getUser().equals(user))
            taskRepository.deleteByKey(key);
        
    }

    /**
     * get a user and key and complete that specific task
     * @param username,key
     */
    public void completeTask(String username, String key){
        System.out.println("key");
        Task task= taskRepository.findByKey(key);
        task.setCompleted(true);
        task.setRate(2);
        taskRepository.save(task);
    }
    
    /**
     * get a user and key and wishlist that specific task
     * @param username,key
     */
    public void wishlistTask(String username, String key){
        Task task = taskRepository.findByKey(key);
        task.setWishlist(true);
        task.setRate(1);
        taskRepository.save(task);
    }

    /**
     * Gets a username, finds all his tasks and send to another method to rate them,
     * Returns a sorted list for tasks, and returns the correct index by the given logic
     * @param username
     * @return the task by the given logic
     * @throws CustomException
     */
    public ResponseEntity<Task> getTasksByRating(String username) throws CustomException{
        User user= userRepository.findByUsername(username);
        List<Task> allTasks=sortTasksByRating(taskRepository.findByUser(user));
        if(allTasks.isEmpty()){
            throw new CustomException("No Tasks");
        }
        double random=Math.random()*100;
        if (random < 20 && allTasks.size()>1) {
            return new ResponseEntity<>(allTasks.get(1),HttpStatus.OK);
        }else if(random < 40 && allTasks.size()>2) {
            return new ResponseEntity<>(allTasks.get(2),HttpStatus.OK);
        }else if(random < 50 && allTasks.size()>3) {
            return new ResponseEntity<>(allTasks.get(3),HttpStatus.OK);
        }else if(random < 55 && allTasks.size()>4) {
            return new ResponseEntity<>(allTasks.get(4),HttpStatus.OK);
        }else if(random < 60 && allTasks.size()>5) {
            return new ResponseEntity<>(allTasks.get(5),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(allTasks.get(new Random().nextInt(allTasks.size())),HttpStatus.OK);
        }
    }
        
    /**
     * 
     * @param allTasks
     * @return sorted tasks from the highest to the lowers rating
     */
    private List<Task> sortTasksByRating(List<Task> allTasks){

        Collections.sort(allTasks, (task1, task2) -> Integer.compare(task2.getRate(), task1.getRate()));
        return allTasks;
    }
    
}
