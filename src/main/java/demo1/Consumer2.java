package demo1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer2 {
  private final static String QUEUE_NAME = "queuename_2";
  private static final String EXCHANGE_NAME = "direct_exchange";
  private static final String ROUTING_KEY = "routingkey_test";
  private static final String RABBITMQ_HOST = "localhost";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost(RABBITMQ_HOST);
    factory.setUsername("admin");
    factory.setPassword("admin");
    factory.setVirtualHost("/");
    factory.setPort(5672);
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, "direct");

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    //绑定队列到交换机
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(QUEUE_NAME, true, consumer);
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      System.out.println(" [x] Received '" + message + "'");

      Thread.sleep(100);
    }
  }
}