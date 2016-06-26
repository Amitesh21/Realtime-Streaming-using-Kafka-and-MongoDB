/**
 * 
 */
package com.amitesh.service;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * @author amitesh
 *
 */
public class SimplePartitioner implements Partitioner {
	
    public SimplePartitioner (VerifiableProperties props) {
 
    }
 
    public int partition(Object key, int a_numPartitions) {
        int partition = 0;
        String stringKey = (String) key;
        System.out.println("stringKey: "+stringKey);
        int offset = stringKey.lastIndexOf('.');
        System.out.println("offset: "+offset);
        if (offset > 0) {
           partition = Integer.parseInt( stringKey.substring(offset+1)) % a_numPartitions;
        }
       return partition;
  }

}
