package com.sysaid.assignment.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User{

    @Id
    private String username;
    

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();
    public User(){
        this.username="";
        
    }
    public User(String username){
        this.username=username;
    }

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
