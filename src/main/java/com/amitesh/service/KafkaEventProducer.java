/**
 * 
 */
package com.amitesh.service;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;

/**
 * @author amitesh
 *
 */
public class KafkaEventProducer implements EventProducer {
	private static KafkaEventProducer kafkaProducer = null;
	private static Producer<String, String> internalProducer;

	private KafkaEventProducer() {
	}

	private final static org.slf4j.Logger logger = LoggerFactory
			.getLogger(KafkaEventProducer.class);

	/**
	 * Establish the connection with kafka cluster
	 * @param host
	 * @param port
	 * @return
	 */
	public static EventProducer getConnection(String host, String port) {
		synchronized (KafkaEventProducer.class) {
			if (kafkaProducer == null) {
				BasicConfigurator.configure();
				Properties props = new Properties();
				props.put("zk.connect", host + ":" + port);

				props.put("serializer.class", "kafka.serializer.StringEncoder");
				props.put("metadata.broker.list",
						"127.0.0.1:9092,127.0.0.1:9092");
				props.put("partitioner.class",
						"com.amitesh.service.SimplePartitioner");
				ProducerConfig config = new ProducerConfig(props);
				internalProducer = new Producer<String, String>(config);
				kafkaProducer = new KafkaEventProducer();
			}
		}
		return kafkaProducer;
	}

	/**
	 * Send the message to the broker
	 */

	@Override
	public boolean send(String topicName, String data) {
		boolean success = false;
		System.out.println("Data: "+data);
		KeyedMessage<String, String> message = new KeyedMessage<String, String>(
				topicName, data);
		System.out.println("Messgae: "+message);
		try {
			internalProducer.send(message);
			success = true;
		} catch (Exception e) {
			success = false;
			logger.error("Error while sending kafka message: "
					+ e.getStackTrace());
		}
		System.out.println("Status: "+success);
		return success;
	}

	@Override
	public void disconnect() {
		internalProducer.close();

	}

}
