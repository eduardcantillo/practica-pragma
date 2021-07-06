package com.pragma.laboratorio.customer.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class MessageException extends RuntimeException{

	private HttpStatus status;
	
	MessageException(String message,HttpStatus status){
		super(message);
		this.status=status;
	}
	
}
