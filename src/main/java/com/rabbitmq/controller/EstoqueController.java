package com.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.constante.RabbitmqConstantes;
import com.rabbitmq.dto.CategoriaDto;
import com.rabbitmq.service.RabbitmqService;

@RestController
@RequestMapping(value = "categoria")
public class EstoqueController {

  @Autowired
  private RabbitmqService rabbitmqService;

  @PutMapping
  private ResponseEntity alteraEstoque(@RequestBody CategoriaDto estoqueDto){
    System.out.println(estoqueDto.getId());
    this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_CATEGORIA, estoqueDto);
    return new ResponseEntity(HttpStatus.OK);
  }
}
