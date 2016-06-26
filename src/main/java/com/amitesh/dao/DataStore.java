/**
 * 
 */
package com.amitesh.dao;

import java.util.Date;
import java.util.List;

import com.amitesh.controller.ServiceEventRequest;

/**
 * @author amitesh
 *
 */
public interface DataStore<T> {

	public boolean storeRawEvent(String jsonData);

	public T getAll();

}
