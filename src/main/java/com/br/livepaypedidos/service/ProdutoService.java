package com.br.livepaypedidos.service;

import com.br.livepaypedidos.dto.ProdutoDTO;
import com.br.livepaypedidos.model.Produto;
import com.br.livepaypedidos.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<ProdutoDTO> obterTodos(Pageable pageable){
        return produtoRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, ProdutoDTO.class));
    }

    public ProdutoDTO obterPorId(Long id){
        Produto pagamento = produtoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, ProdutoDTO.class);
    }

    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO){
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        produtoRepository.save(produto);

        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO dto) {
        Produto produto = modelMapper.map(dto, Produto.class);
        produto.setId(id);
        produto = produtoRepository.save(produto);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

}
