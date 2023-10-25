package com.br.livepaypedidos.dto;

import com.br.livepaypedidos.model.Pessoa;
import com.br.livepaypedidos.model.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CriarPedidoDTO {

    private Long id;

    private Long pessoa_id;

    private List<Long> produtos_id;

}
