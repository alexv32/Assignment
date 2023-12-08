package com.sysaid.assignment.controller;

import com.sysaid.assignment.domain.Task;
import com.sysaid.assignment.service.CustomException;
import com.sysaid.assignment.service.TaskServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	@Autowired
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
		
		return taskService.getUncompletedTask(user,type);
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
	 * @return all wishlisted tasks
	 * @throws CustomException if there are no tasks
	 */
	@GetMapping("/wishlisted-tasks/{user}")
	public ResponseEntity<List<Task>> getWishlistedTasks(@PathVariable ("user") String user) throws CustomException{
		
		return taskService.getWishlistedTask(user);
	}
	/**
	 * Returns all unwishlisted tasks
	 * @param user
	 * @return all tasks that are not wishlisted
	 * @throws CustomException if there are no tasks
	 */
	@GetMapping("/unwishlisted-tasks/{user}")
	public ResponseEntity<List<Task>> getUnWishlistedTasks(@PathVariable ("user") String user) throws CustomException{
		return taskService.getUnWishlistedTask(user);
	}
	/**
	 * 
	 * @param user
	 * @return the rated task by the rating set in the assisgment(20% first, 20% second, 10% third, 5% fourth,5% fifth, 40% random task from the list)
	 * @throws CustomException if there are no tasks
	 */
	@GetMapping("/rated/{user}")
	public ResponseEntity<Task> getRatedTasks(@PathVariable ("user") String user) throws CustomException{
		return taskService.getTasksByRating(user);
	}

	/**
	 * Creates a new task for the user,
	 * @param user
	 * @return return a response on success that the task was created
	 */
	@PostMapping(path={"/completed-tasks/{user}","/wishlisted-tasks/{user}","/unwishlisted-tasks/{user}","/uncompleted-tasks/{user}","/all-tasks/{user}","/rated/{user}"})
	public ResponseEntity<String> createTaskForUser(@PathVariable ("user") String user){
		taskService.createTask(user);
		return ResponseEntity.ok("Task Created");
	}
	/**
	 * Set a certain task as completed for the user
	 * @param user
	 * @param key
	 * @return return a response on success that the task was completed
	 */ 
	@PutMapping("/uncompleted-tasks/{user}")
	public ResponseEntity<String> updateCompletedTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "task_key",required = true) String key){
		taskService.completeTask(user, key);
		return ResponseEntity.ok("Task Completed");
	}
	/**
	 * Wishlist a certain user task
	 * @param user current user
	 * @param key	the task key we would like to wishlist
	 * @return return a response on success that the task was wishlisted
	 */
	@PutMapping("/unwishlisted-tasks/{user}")
	public ResponseEntity<String> updateWishlistTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "task_key",required = true) String key){
		taskService.wishlistTask(user, key);
		return ResponseEntity.ok("Task Wishlisted");
	}
	
	/**
	 * Delete a certain task for a user, from every path available
	 * @param user
	 * @param key the task key we want to delete
	 * @return return a response on success that the task was deleted
	 */
	@DeleteMapping(path={"/completed-tasks/{user}","/wishlisted-tasks/{user}","/unwishlisted-tasks/{user}","/uncompleted-tasks/{user}","/all-tasks/{user}","/rated/{user}"})
	public ResponseEntity<String> deleteTaskForUser(@PathVariable ("user") String user, @RequestParam(name = "task_key",required = true) String key){
		taskService.deleteTask(user, key);
		return ResponseEntity.ok("Task Deleted");
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

