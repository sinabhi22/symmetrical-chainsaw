package com.nokia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nokia.model.Stack;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface StackRepository extends JpaRepository<Stack, Integer>{

	Stack findTopByOrderByIdDesc();

	/*
	//@Query("SELECT e FROM stack e where e.id = (select max(id) from stack)")
	@Query("SELECT e FROM stack e order by e,id desc")
    public Stack findMaxId();
	*/
	
}
