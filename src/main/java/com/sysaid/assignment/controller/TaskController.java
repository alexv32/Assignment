package com.sysaid.assignment.controller;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.service.CustomException;
import com.sysaid.assignment.service.TaskServiceImpl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * the controller is a basic structure and save some time on "dirty" work.
 */

@RestController
@RequestMapping("/api")
public class TaskController {


	private final TaskServiceImpl taskService;

	/**
	 * constructor for dependency injection
	 * @param taskService
	 */
	public TaskController(TaskServiceImpl taskService) {
		this.taskService = taskService;
	}

	/**
	 * will get all users tasks
	 * @param user
	 * @return list of all user tasks
	 * @throws CustomException
	 */
	@GetMapping("/all-tasks/{user}")
	public ResponseEntity<List<Task>> allTasks(@PathVariable ("user") String user) throws CustomException{
		return taskService.getAllTasks(user);
	}

	/**
	 * will return uncompleted tasks for given user, throws exception if empty
	 * @param user the user which the tasks relevant for
	 * @param type type of the task
	 * @return list uncompleted tasks for the user
	 * @throws CustomException
	 */
	@GetMapping("/uncompleted-tasks/{user}")
	public ResponseEntity<List<Task>> getUncompletedTasks(@PathVariable ("user") String user, @RequestParam(name = "type",required = false) String type) throws CustomException{
		
		return taskService.getUncompletedTask(user);
	}

	/**
	 * Will get all user completed tasks, throws exception if empty
	 * @param user
	 * @return list of user completed task
	 * @throws CustomException
	 */
	@GetMapping("/completed-tasks/{user}")
	public ResponseEntity<List<Task>> getCompletedTasks(@PathVariable ("user") String user) throws CustomException{
		return taskService.getCompletedTask(user);
	}

	
	/**
	 * Returns all wishlisted tasks
	 * @param user
	 * @param type
	 * @return
	 * @throws CustomException
	 */
	@GetMapping("/wishlisted-tasks/{user}")
	public ResponseEntity<List<Task>> getWishlistedTasks(@PathVariable ("user") String user, @RequestParam(name = "type",required = false) String type) throws CustomException{
		
		return taskService.getWishlistedTask(user);
	}
	/**
	 * Returns all unwishlisted tasks
	 * @param user
	 * @return
	 * @throws CustomException
	 */
	@GetMapping("/unwishlisted-tasks/{user}")
	public ResponseEntity<List<Task>> getUnWishlistedTasks(@PathVariable ("user") String user) throws CustomException{
		return taskService.getUnWishlistedTask(user);
	}
	/**
	 * Creates a new task for the user,
	 * @param user
	 */
	@PutMapping("/all-tasks/{user}")
	public void createTaskForUser(@PathVariable ("user") String user){
		taskService.createTask(user);
	}
	/**
	 * Set a certain task as completed for the user
	 * @param user
	 * @param key
	 */
	@PutMapping("/uncompleted-tasks/{user}/{task_key}")
	public void updateCompletedTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.completeTask(user, key);
	}
	/**
	 * Wishlist a certain user task
	 * @param user
	 * @param key
	 */
	@PutMapping("/unwishlisted-tasks/{user}/{task_key}")
	public void updateWishlistTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.wishlistTask(user, key);
	}
	
	/**
	 * Delete a certain task
	 * @param user
	 * @param key
	 */
	@DeleteMapping("/all-tasks/{user}/{task_key}")
	public void deleteTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.deleteTask(user, key);
	}

	/**
	 * example for simple API use
	 * @return random task of the day
	 */
	@GetMapping("/task-of-the-day")
	public  ResponseEntity<Task> getTaskOfTheDay(){
		//example of service use
		return taskService.getRandomTask();
	}


	//Probably irrelevant tasks:
	/**
	 * Delete an incomplete task for the user
	 * @param user
	 * @param key
	 */
	@DeleteMapping("/uncompleted-tasks/{user}/{task_key}")
	public void deleteIncompleteTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.deleteTask(user, key);
	}
	/**
	 * delete a completed task for the user
	 * @param user
	 * @param key
	 */
	
	@DeleteMapping("/completed-tasks/{user}/{task_key}")
	public void deleteCompleteTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.deleteTask(user, key);
	}

	@DeleteMapping("/wishlisted-tasks/{user}/{task_key}")
	public void deleteWishlistedTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.deleteTask(user, key);
	}

	@DeleteMapping("/unwishlisted-tasks/{user}/{task_key}")
	public void deleteUnwishlistedTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.deleteTask(user, key);
	} 
	
	

	


}

