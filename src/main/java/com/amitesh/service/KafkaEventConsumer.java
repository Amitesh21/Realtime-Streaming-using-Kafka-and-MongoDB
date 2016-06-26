/**
 * 
 */
package com.amitesh.service;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;

import com.amitesh.controller.BaseController;
import com.amitesh.dao.DataStore;
import com.amitesh.dao.MongoDataStore;

import ch.qos.logback.classic.BasicConfigurator.*;

/**
 * @author amitesh
 *
 */
public class KafkaEventConsumer extends Thread implements EventConsumer {

	final static String clientId = "SarojKafkaClient";
	final static String TOPIC = "test-events";
	private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;
	private ConsumerConnector consumerConnector;
	private ExecutorService executor;
	private final static org.slf4j.Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	public static void main(String[] argv) {
		System.out.println("start");
		BasicConfigurator.configure();
		KafkaEventConsumer kafkaConsumer = new KafkaEventConsumer();
		kafkaConsumer.start();
	}

	public KafkaEventConsumer() {
		
		Properties props = new Properties();
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "test-group");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "10000");
        props.put("auto.commit.interval.ms", "10000");
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        consumerConnector = Consumer
				.createJavaConsumerConnector(consumerConfig);
		
	}

	/**
	 * This thread will pull the events from the topic and insert into mongo DB
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		
		System.out.println("inside run");

		DataStore store = null;
		try {
			store = MongoDataStore.getInstance(MONGO_HOST, MONGO_PORT);
			System.out.println("Mongo Connection Check: "+store.toString());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
	        topicCountMap.put(TOPIC, new Integer(1));
	        
	        System.out.println("amitesh");
	        
	        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
	        KafkaStream<byte[], byte[]> streams = consumerMap.get(TOPIC).get(0);
	        ConsumerIterator<byte[], byte[]> it = streams.iterator();
		while (it.hasNext()){
			try {
				String data = new String(it.next().message());
				System.out.println(data);
				store.storeRawEvent(data);
			} catch (Exception e) {
				logger.error(
						"Throwing Exception while inserting data to Mongo DB",
						e);
			}
		}
	}
}
