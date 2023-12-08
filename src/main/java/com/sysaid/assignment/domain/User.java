package com.sysaid.assignment.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user",uniqueConstraints = @UniqueConstraint(name = "UK_username", columnNames = "username"))
public class User implements Serializable{

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    
    /**
     * One user to many tasks relationship
     * mapped by the user
     * each user holds a list of his own tasks
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();
    public User(){
        this.username="";
    }
    public User(String username){
        this.username=username;
    }

    /*
     * a couple of getters and setters for the attributes
     */

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public List<Task> getTasks(){
        return this.tasks;
    }
   
    
}
