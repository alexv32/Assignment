package com.sysaid.assignment.domain;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Task implements Serializable{

	private static final long serialVersionUID = 1L;

	private String activity;
	private Float accessibility;
	private String type;
	private Integer participants;
	private Float price;
	private String link;
	@Id
	private String key;
	private boolean completed=false;
	private boolean wishlist=false;
	private int rate=0;

	@ManyToOne
	@JoinColumn(name = "user_id")
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

