package com.nokia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nokia.configuration.DBContextHolder;
import com.nokia.configuration.DBTypeEnum;
import com.nokia.exception.StackIsEmptyException;
import com.nokia.model.Stack;
import com.nokia.repository.StackRepository;

@Service
public class StackService {

	@Autowired
	private StackRepository stackRepository;
	
	public Stack push(Stack s,String db) {
		selectDB(db);
		stackRepository.save(s);
		return s;
	}
	
	public Stack pop(String db) {
		selectDB(db);
		Stack s = stackRepository.findTopByOrderByIdDesc();
		if(s==null)
			throw new StackIsEmptyException();
		stackRepository.delete(s);
		return s;
	}
	
	public void selectDB(String db) {
		if(db.equalsIgnoreCase("mysql")) {
			DBContextHolder.setCurrentDb(DBTypeEnum.MySQL); 
		}
		else {
			DBContextHolder.setCurrentDb(DBTypeEnum.MariaDB); 
		}
	}

	
}
