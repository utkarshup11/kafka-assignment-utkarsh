package com.knoldus.Producer;

import com.knoldus.Entity.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer. * ;

import java.util.Properties;

public class Producer
{
    public static void main(String[] args)
    {
        //creating object of User Entity class to send it as a Json data
        User firstUser = new User(1, "Utkarsh Upadhyay", 22, "B.Tech");
        User secondUser = new User(2, "Suraj Jaiswal", 23, "MBA");
        User thirdUser = new User(3, "Upendra Singh", 25, "M.Tech");
        User fourthUser = new User(4, "Kushagra Srivastava", 26, "PHD");

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "com.knoldus.Serialization.UserSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        try
        {

            kafkaProducer.send(new ProducerRecord("user", Integer.toString(1), firstUser));
            kafkaProducer.send(new ProducerRecord("user", Integer.toString(2), secondUser));
            kafkaProducer.send(new ProducerRecord("user", Integer.toString(3), thirdUser));
            kafkaProducer.send(new ProducerRecord("user", Integer.toString(4), fourthUser));


            System.out.println("Producer successfully produces all messages ");

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            kafkaProducer.close();
        }

    }
}