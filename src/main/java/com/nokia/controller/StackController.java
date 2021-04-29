package com.nokia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nokia.model.Stack;
import com.nokia.service.StackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.*;

@RestController
@RequestMapping("stackcontroller")
@Api(value="Operations pertaining to stack") 
public class StackController {

	
	@Autowired
	private StackService stackService;
	
	
	@PostMapping("/push/{db}")
	@ApiOperation(value = "Pushes the element at the top of the stack  ", response = Stack.class, tags = "push")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	public Stack push(@ApiParam(value = "Details of stack ",
            required = true)
	@RequestBody Stack s, @PathVariable String db) {
		stackService.push(s,db);
		return s;
	}
	
	@GetMapping("/pop/{db}")
	@ApiOperation(value = "Returns the element present at the top of the stack  ", response = Stack.class, tags = "pop")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	public Stack pop(@PathVariable String db) {
		Stack s = stackService.pop(db);
		return s;
	}
	
	
	
	
}
