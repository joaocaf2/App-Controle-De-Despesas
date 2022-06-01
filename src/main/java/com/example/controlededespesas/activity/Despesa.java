package com.example.controlededespesas.activity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Despesa {
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    public Despesa(String descricao, BigDecimal valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }
    public Despesa(){

    }


    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getData() {
        return data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
