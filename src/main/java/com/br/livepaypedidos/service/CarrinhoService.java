package com.br.livepaypedidos.service;

import com.br.livepaypedidos.model.Pedidos;
import com.br.livepaypedidos.model.Produto;
import com.br.livepaypedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CarrinhoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public void adicionarProduto(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);

        if (produto != null) {
            produto.setQuantidadeNoCarrinho(produto.getQuantidadeNoCarrinho() + 1);
            produtoRepository.save(produto); // Atualiza o registro no banco de dados
        }
    }

    public void removerProduto(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);

        if (produto != null && produto.getQuantidadeNoCarrinho() > 0) {
            produto.setQuantidadeNoCarrinho(produto.getQuantidadeNoCarrinho() - 1);
            produtoRepository.save(produto); // Atualiza o registro no banco de dados
        }
    }

    public double calcularValorTotal(Pedidos pedidos) {
        double valorTotal = 0.0;

        for (Produto produto : pedidos.getProduto()) {
            double precoProduto = produto.getValor();
            int quantidade = produto.getQuantidadeNoCarrinho();
            valorTotal += precoProduto * quantidade;
        }

        return valorTotal;
    }

}
