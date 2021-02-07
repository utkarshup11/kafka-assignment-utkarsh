package com.knoldus.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.Entity.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {

    public static void main(String[] args) {
        ConsumerListener c = new ConsumerListener();
        Thread thread = new Thread(c);
        thread.start();
    }
    public static void consumer() throws IOException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "com.knoldus.Serialization.UserDeserializer");
        properties.put("group.id", "test-group");

        File file = new File("output.txt");
        file.createNewFile();


        List topics = new ArrayList();
        topics.add("user");
        try (KafkaConsumer<String, User> kafkaConsumer = new KafkaConsumer(properties)) {
            kafkaConsumer.subscribe(topics);
            while (true) {
                ConsumerRecords<String, User> messages = kafkaConsumer.poll(1000);
                for (ConsumerRecord<String, User> message : messages) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonObject = objectMapper.writeValueAsString(message.value());
                    FileWriter fileWriter = new FileWriter("output.txt" , true);
                    System.out.println(jsonObject);
                    fileWriter.write(jsonObject);
                    fileWriter.write("\n");
                    fileWriter.close();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}

class ConsumerListener implements Runnable {
    @Override
    public void run() {
        try {
            Consumer.consumer();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}