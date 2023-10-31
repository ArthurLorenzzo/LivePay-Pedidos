package com.br.livepaypedidos.service;

import com.br.livepaypedidos.dto.PessoaDTO;
import com.br.livepaypedidos.dto.ProdutoDTO;
import com.br.livepaypedidos.model.Pessoa;
import com.br.livepaypedidos.model.Produto;
import com.br.livepaypedidos.repository.PessoaRepository;
import com.br.livepaypedidos.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PessoaDTO> obterTodos(Pageable pageable){
        return pessoaRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PessoaDTO.class));
    }

    public PessoaDTO obterPorId(Long id){
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO){
        Pessoa pessoa = modelMapper.map(pessoaDTO, Pessoa.class);
        pessoaRepository.save(pessoa);

        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    public PessoaDTO atualizarPessoa(Long id, PessoaDTO dto) {
        Pessoa pessoa = modelMapper.map(dto, Pessoa.class);
        pessoa.setId(id);
        pessoa = pessoaRepository.save(pessoa);
        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    public void excluirPessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

}
