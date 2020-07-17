package demo1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Producer {

  private static final String EXCHANGE_NAME = "direct_exchange";
  private static final String ROUTING_KEY = "routingkey_test";
  private static final String RABBITMQ_HOST = "localhost";

  public static void main(String[] args) throws IOException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(RABBITMQ_HOST);
    factory.setUsername("admin");
    factory.setPassword("admin");
    factory.setPort(5672);
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "direct");

    String message = "Hello World!";
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
}
