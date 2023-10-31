package com.br.livepaypedidos.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaDTO {

    private String nome;

    private String email;

    private String cpf;

}
