package com.sysaid.assignment.domain;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="task")
public class Task implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String activity;
	private Float accessibility;
	private String type;
	private Integer participants;
	private Float price;
	private String link;
	@Column(name="task_key")
	private String key;
	private boolean completed=false;
	private boolean wishlist=false;
	private int rate=0;

	/**
	 * Many tasks to one use relationship
	 * Joining table columns with the username column
	 * JsonIgnore the field so it will not pull it on any Get request
	 */
	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username")
	@JsonIgnore
	private User user;
	
	public Task(){
		this.activity=null;
		this.accessibility=(float) 0;
		this.type=null;
		this.participants=0;
		this.price=(float) 0;
		this.link=null;
		this.key=null;

	}
	public Task(Task task){
		this.activity=task.activity;
		this.accessibility=task.accessibility;
		this.type=task.type;
		this.participants=task.participants;
		this.price=task.price;
		this.link=task.link;
		this.key=task.key;
	}

	/**
	 * A bunch of getters and setters for any of the attributes
	 * 
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Float getAccessibility() {
		return accessibility;
	}

	public void setAccessibility(Float accessibility) {
		this.accessibility = accessibility;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getParticipants() {
		return participants;
	}

	public void setParticipants(Integer participants) {
		this.participants = participants;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public boolean getCompleted(){
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public boolean getWishlist(){
		return wishlist;
	}
	public void setWishlist(boolean wishlist) {
		this.wishlist = wishlist;
	}
	public int getRate() {
		return rate;
	}
	/**
	 * Rate updates for every action done on the object
	 * @param rate
	 */
	public void setRate(int rate) {
		this.rate += rate;
	}

	public void setUser(User user){
		this.user=user;
	}

	public User getUser(){
		return user;
	}

}

