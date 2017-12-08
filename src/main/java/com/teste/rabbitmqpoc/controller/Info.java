package com.teste.rabbitmqpoc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import com.teste.rabbitmqpoc.consumer.ConsumerRabbitMQQueue;

@Component("info")
public class Info implements HttpRequestHandler {
    
    @Autowired
    private ConsumerRabbitMQQueue queue;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
        String mensagem = queue.getLastMessage();
        response.getOutputStream().print(mensagem == null ? "Sem mensagens" : mensagem);
    }
}
