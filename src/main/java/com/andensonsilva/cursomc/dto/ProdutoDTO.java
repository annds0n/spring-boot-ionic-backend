package com.andensonsilva.cursomc.dto;

import com.andensonsilva.cursomc.domain.Categoria;
import com.andensonsilva.cursomc.domain.Produto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Campo obrigatório")
    @Length(min = 5, max = 80, message = "O tamnho deve estar entre 5 e 80 caracteres")
    private String nome;

    private Double preco;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
