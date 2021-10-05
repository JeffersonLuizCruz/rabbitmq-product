package com.rabbitmq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

//  @Autowired
//  private ObjectMapper objectMapper;

  public void enviaMensagem(String nomeFila, Object mensagem){
    try {
    //String mensagemJson = (Object)this.objectMapper.writeValueAsString(mensagem);
      this.rabbitTemplate.convertAndSend(nomeFila, mensagem); // -> nomeFile é achave de roteamento que tem o mesmo nome da fila
    } catch (Exception e){
      e.printStackTrace();
    }
  }
}
