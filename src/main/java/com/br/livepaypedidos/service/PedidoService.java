package com.br.livepaypedidos.service;

import com.br.livepaypedidos.dto.CriarPedidoDTO;
import com.br.livepaypedidos.dto.LerPedidoDTO;
import com.br.livepaypedidos.dto.ProdutoDTO;
import com.br.livepaypedidos.exceptions.RequiredObjectIsNullException;
import com.br.livepaypedidos.exceptions.ResourceNotFoundException;
import com.br.livepaypedidos.model.Estoque;
import com.br.livepaypedidos.model.Pedidos;
import com.br.livepaypedidos.model.Produto;
import com.br.livepaypedidos.repository.PedidoRepository;
import com.br.livepaypedidos.repository.PessoaRepository;
import com.br.livepaypedidos.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<LerPedidoDTO> obterTodos(Pageable pageable){
        return pedidoRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, LerPedidoDTO.class));
    }

    public LerPedidoDTO obterPorId(Long id){
        Pedidos pedidos = pedidoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pedidos, LerPedidoDTO.class);
    }

    public LerPedidoDTO criarPedido(CriarPedidoDTO pedidoDTO){

        if (pedidoDTO == null){throw new RequiredObjectIsNullException();}

        Pedidos pedidoCriado = modelMapper.map(pedidoDTO, Pedidos.class);
        pedidoCriado.setProduto(
                pedidoDTO.getProdutos_id()
                        .stream()
                        .map(idProduto -> produtoRepository.findById(idProduto)
                                .orElseThrow(ResourceNotFoundException::new))
                        .collect(Collectors.toList())
        );
        pedidoCriado.setPessoa(pessoaRepository.findById(pedidoDTO.getPessoa_id())
                .orElseThrow(ResourceNotFoundException::new));

        return modelMapper.map(pedidoRepository.save(pedidoCriado), LerPedidoDTO.class);

    }


    public LerPedidoDTO compraPedido(LerPedidoDTO dto) {
        if (dto == null){throw new RequiredObjectIsNullException();}
        List<Produto> produtos = dto.getProduto();
        Produto produtoExistente = new Produto();
        Estoque estoque = new Estoque();
        modelMapper.map(dto, produtoExistente);



        for (Produto produto : produtos){
           if (produto.getEstoque().getQuantidadeProduto() > produto.getQuantidade()){
                produto.calcularDecrecimoEstoque(produto);
                produto.getEstoque().setQuantidadeProduto(produto.getEstoque().getQuantidadeProduto() - produto.getQuantidade());
                produtoExistente.setQuantidade(produtos);

           }
        }

        Pedidos pedidoAtualizado = pedidoRepository.save(produtoExistente);

        return modelMapper.map(pedidoAtualizado, LerPedidoDTO.class);
    }
}
