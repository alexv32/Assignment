package com.sysaid.assignment.controller;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.service.TaskServiceImpl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * the controller is a basic structure and save some time on "dirty" work.
 */

@RestController
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
	 * will return uncompleted tasks for given user
	 * @param user the user which the tasks relevant for
	 * @param type type of the task
	 * @return list uncompleted tasks for the user
	 */
	@GetMapping("/uncompleted-tasks/{user}")
	public ResponseEntity<List<Task>> getUncompletedTasks(@PathVariable ("user") String user, @RequestParam(name = "type",required = false) String type){
		
		return taskService.getUncompletedTask(user);
	}

	@GetMapping("/completed-tasks/{user}")
	public ResponseEntity<List<Task>> getCompletedTasks(@PathVariable ("user") String user){
		return taskService.getCompletedTask(user);
	}

	@GetMapping("/all-tasks/{user}")
	public ResponseEntity<List<Task>> allTasks(@PathVariable ("user") String user){
		return taskService.getAllTasks(user);
	}
	
	@GetMapping("/wishlisted-tasks/{user}")
	public ResponseEntity<List<Task>> getWishlistedTasks(@PathVariable ("user") String user, @RequestParam(name = "type",required = false) String type){
		
		return taskService.getWishlistedTask(user);
	}

	@GetMapping("/unwishlisted-tasks/{user}")
	public ResponseEntity<List<Task>> getUnWishlistedTasks(@PathVariable ("user") String user){
		return taskService.getUnWishlistedTask(user);
	}

	@PutMapping("/all-tasks/{user}")
	public void createTaskForUser(@PathVariable ("user") String user){
		taskService.createTask(user);
	}

	@PutMapping("/uncompleted-tasks/{user}/{task_key}")
	public void updateCompletedTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.completeTask(user, key);
	}
	
	@PutMapping("/unwishlisted-tasks/{user}/{task_key}")
	public void updateWishlistTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.wishlistTask(user, key);
	}

	@DeleteMapping("/all-tasks/{user}/{task_key}")
	public void deleteTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.deleteTask(user, key);
	}

	@DeleteMapping("/uncompleted-tasks/{user}/{task_key}")
	public void deleteIncompleteTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "key",required = true) String key){
		taskService.deleteTask(user, key);
	}

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

	/**
	 * example for simple API use
	 * @return random task of the day
	 */
	@GetMapping("/task-of-the-day")
	public  ResponseEntity<Task> getTaskOfTheDay(){
		//example of service use
		return taskService.getRandomTask();
	}


}

