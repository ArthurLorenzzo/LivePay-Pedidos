package com.br.livepaypedidos.controller;

import com.br.livepaypedidos.dto.CriarPedidoDTO;
import com.br.livepaypedidos.dto.LerPedidoDTO;
import com.br.livepaypedidos.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public Page<LerPedidoDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return pedidoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LerPedidoDTO> detalhar(@PathVariable @NotNull Long id) {
        LerPedidoDTO dto = pedidoService.obterPorId(id);

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<LerPedidoDTO> cadastrar(@RequestBody @Valid CriarPedidoDTO dto) {
        var pedidoCriado = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }

}
