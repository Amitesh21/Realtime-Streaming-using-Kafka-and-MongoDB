/**
 * 
 */
package com.amitesh.service;

/**
 * @author amitesh
 *
 */
public interface EventProducer {

	public boolean send(String topicName, String data);
	public void disconnect();
	
}
