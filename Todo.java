
package com.example.model;

public class Todo {
    private int id;
    private String todoTitle;
    private String todoDesc;
    private String todoDateTime;
    private String todotargetdate;
    private String todoStatus;  

  
    public int getId() { 
    	return id;
    	}
    public void setId(int id) { 
    	this.id = id;
    	}
    public String getTodoTitle() {
    	return todoTitle; 
    	}

    public void setTodoTitle(String todoTitle) { 
    	this.todoTitle = todoTitle; 
    	}

    public String getTodoDesc() { 
    	return todoDesc; 
    	}
    public void setTodoDesc(String todoDesc) { 
    	this.todoDesc = todoDesc; 
    	}

    public String getTodoDateTime() {
    	return todoDateTime;  
    	}
    public void setTodoDateTime(String todoDateTime) {
    	this.todoDateTime = todoDateTime; 
    	}

    public String getTodoStatus() {
    	return todoStatus; 
    	}
    public void setTodoStatus(String todoStatus) {
    	this.todoStatus = todoStatus; 
    	}
	public String getTodotargetdate() {
		return todotargetdate;
	}
	public void setTodotargetdate(String todotargetdate) {
		this.todotargetdate = todotargetdate;
	}
}

