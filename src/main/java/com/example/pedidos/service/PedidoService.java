package com.example.pedidos.service;

import org.springframework.data.repository.CrudRepository;

import com.example.pedidos.entity.PedidoEntity;

public interface PedidoService extends CrudRepository<PedidoEntity, Long>{

	
}
