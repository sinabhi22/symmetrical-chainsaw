package com.nokia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Stack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Id of the item",name="id", value="1")
    private int id;
	
	 @ApiModelProperty(notes = "Name of the item",name="name",required=true,value="test name")
	 private String name;

	public Stack(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Stack() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Stack [id=" + id + ", name=" + name + "]";
	}

	
	
}
