package com.br.livepaypedidos.controller;

import com.br.livepaypedidos.dto.PessoaDTO;
import com.br.livepaypedidos.dto.ProdutoDTO;
import com.br.livepaypedidos.service.PessoaService;
import com.br.livepaypedidos.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public Page<PessoaDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return pessoaService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> detalhar(@PathVariable @NotNull Long id) {
        PessoaDTO dto = pessoaService.obterPorId(id);

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<PessoaDTO> cadastrar(@RequestBody @Valid PessoaDTO dto) {
        PessoaDTO pessoa = pessoaService.criarPessoa(dto);

        return ResponseEntity.ok(pessoa);
    }

    @PutMapping
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PessoaDTO dto) {
        PessoaDTO atualizado = pessoaService.atualizarPessoa(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PessoaDTO> remover(@PathVariable @NotNull Long id) {
        pessoaService.excluirPessoa(id);
        return ResponseEntity.noContent().build();
    }

}
