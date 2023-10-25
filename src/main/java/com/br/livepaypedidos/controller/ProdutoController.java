package com.br.livepaypedidos.controller;

import com.br.livepaypedidos.dto.ProdutoDTO;
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
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public Page<ProdutoDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return produtoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> detalhar(@PathVariable @NotNull Long id) {
        ProdutoDTO dto = produtoService.obterPorId(id);

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody @Valid ProdutoDTO dto) {
        ProdutoDTO pagamento = produtoService.criarProduto(dto);

        return ResponseEntity.ok(pagamento);
    }

    @PutMapping
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid ProdutoDTO dto) {
        ProdutoDTO atualizado = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoDTO> remover(@PathVariable @NotNull Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

}
