/**
 * 
 */
package com.amitesh.controller;

/**
 * @author amitesh
 *
 */
public class ServiceResponse {

	public ServiceResponse(String message){
		this.message = message;
	}
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
