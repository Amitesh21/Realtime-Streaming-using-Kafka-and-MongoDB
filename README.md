# RealtimeStreaming
Reading Realtime data using Kafka and MongoDB
#############################################

Below are Steps to Execute the Project:
------------------------------------------------------------------
1. Download Kafka using below given link
http://www.us.apache.org/dist/kafka/0.8.1.1/kafka_2.9.2-0.8.1.1.tgz

	a. To start zooKeeper node:
	bin/zookeeper-server-start.sh config/zookeeper.properties

	b.  To start kafka broker 
	bin/kafka-server-start.sh config/server.properties

	c. Create the topic
	bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test-events
	Replication of opic can be incresed as per the available brokers and requirement

2. Donwload and install the mongoDB
	https://www.mongodb.com/download-center?jmp=docs#community 
3. Go to MongoDB folder and create a directory /data/db. Then run mongodb server using ./mongod command inside bin
4. Go through the project. 
5. Import the project in any ide (I m running it on eclipse) and right click and Run as maven build and give the command as tomcat:run
6. Open any rest client(e.g PostMan) and submit a POST request as below with this 
	URL http://127.0.0.1:8080/RealTimeAnalytics/send
	Request JSON in the body of request: { "name": "Data4" }

7. Run KafkaEventConsumer as java application
8. In a new tab, run this URL: http://127.0.0.1:8080/RealTimeAnalytics/dashboard/all
9. You will get the live streaming coming from your DB

SUMMARY:
a. Producer will read the data from PostMan and write it to Kafka bus.
b. Consumer pulls the offset(data) from bus and insert into DB.
c. Now when you try accessing the URL http://127.0.0.1:8080/RealTimeAnalytics/dashboard/all, you get live data form mongoDB 
