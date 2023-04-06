package com.igor.Vendas.domain.repository;

import com.igor.Vendas.domain.entity.Cliente;
import com.igor.Vendas.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository < Pedido, Integer > {
    List<Pedido> findByCliente( Cliente cliente );
}
