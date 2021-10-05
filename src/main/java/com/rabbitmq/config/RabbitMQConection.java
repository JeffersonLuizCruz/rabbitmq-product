package com.rabbitmq.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.constante.RabbitmqConstantes;

@Component
public class RabbitMQConection {
  private static final String NOME_EXCHANGE = "amq.direct"; // -> essa variavel está definida no Rabbit

  @Autowired
  private AmqpAdmin amqpAdmin;

  public RabbitMQConection(AmqpAdmin amqpAdmin){
    this.amqpAdmin = amqpAdmin;
  }

  // Criação da Fila - Uma fila precisa de um nome
  private Queue fila(String nomeFila){
    return new Queue(nomeFila, true, false, false);
  }

  // Criação de um tipo de Exchange. Uma Exgchange precisa de um tipo.Nessa caso é Exchange Direct
  private DirectExchange trocaDireta() {
    return new DirectExchange(NOME_EXCHANGE); // -> essa variavel está definida no Rabbit
  }

  // Ligação de uma fila com uma Exchange
  private Binding relacionamento(Queue fila, DirectExchange troca){
    return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
  }
  
//  private Binding bindin(Queue fila, DirectExchange troca) {
//	  return BindingBuilder
//			  .bind(fila)
//			  .to(troca)
//			  .with(NOME_EXCHANGE);
//			  				
//  }

  //está função é executada assim que nossa classe é instanciada pelo Spring, devido a anotação @Component
  @PostConstruct
  private void adiciona(){
    Queue filaEstoque = this.fila(RabbitmqConstantes.FILA_CATEGORIA);
    Queue filaPreco   = this.fila(RabbitmqConstantes.FILA_PRECO);

    DirectExchange troca = this.trocaDireta();

    Binding ligacaoEstoque = this.relacionamento(filaEstoque, troca);
    Binding ligacaoPreco   = this.relacionamento(filaPreco, troca);

    //Conecta e cria as filas no RabbitMQ
    this.amqpAdmin.declareQueue(filaEstoque);
    this.amqpAdmin.declareQueue(filaPreco);

    this.amqpAdmin.declareExchange(troca);

    this.amqpAdmin.declareBinding(ligacaoEstoque);
    this.amqpAdmin.declareBinding(ligacaoPreco);
  }
}