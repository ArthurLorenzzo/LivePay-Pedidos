package com.br.livepaypedidos.repository;

import com.br.livepaypedidos.model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedidos, Long> {
}
