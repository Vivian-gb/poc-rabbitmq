package com.teste.rabbitmqpoc.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component("messageListener")
public class ConsumerRabbitMQQueue  implements MessageListener {
    
    private String lastMessage;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRabbitMQQueue.class);

    @Override
    public void onMessage(Message message) {
        lastMessage = new String(message.getBody());
        LOGGER.info("Received message: " + message);
        LOGGER.info("Text: " + lastMessage);
    }
    
    /**
     * Método get do atributo lastMessage
     * @return O valor do atributo lastMessage
     */
    public String getLastMessage() {
        return lastMessage;
    }
}
