package com.br.livepaypedidos.service;

import com.br.livepaypedidos.dto.CriarPedidoDTO;
import com.br.livepaypedidos.dto.LerPedidoDTO;
import com.br.livepaypedidos.dto.ProdutoDTO;
import com.br.livepaypedidos.exceptions.RequiredObjectIsNullException;
import com.br.livepaypedidos.exceptions.ResourceNotFoundException;
import com.br.livepaypedidos.model.Pedidos;
import com.br.livepaypedidos.model.Produto;
import com.br.livepaypedidos.repository.PedidoRepository;
import com.br.livepaypedidos.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

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

        return modelMapper.map(pedidoRepository.save(pedidoCriado), LerPedidoDTO.class);

    }

}
