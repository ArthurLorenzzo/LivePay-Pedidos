package com.br.livepaypedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantidadeProduto;

    public void calcularDecrecimo(Produto produto){
         setQuantidadeProduto(produto.getEstoque().getQuantidadeProduto() - produto.getQuantidade());
    }
}
