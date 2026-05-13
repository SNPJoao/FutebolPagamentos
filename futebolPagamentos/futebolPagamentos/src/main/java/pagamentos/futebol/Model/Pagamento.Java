package pagamentos.futebol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/*
    Entidade de pagamentos
*/
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_pagamento")
    private long codPagamento;

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 2000, message = "Ano deve ser maior ou igual a 2000")
    @Max(value = 2100, message = "Ano deve ser menor ou igual a 2100")
    @Column(name = "ano", nullable = false)
    private Short ano;

    @NotNull(message = "Mês é obrigatório")
    @Min(value = 1, message = "Mês deve ser entre 1 e 12")
    @Max(value = 12, message = "Mês deve ser entre 1 e 12")
    @Column(name = "mes", nullable = false)
    private Byte mes;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    // Relacionamento many to one
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_jogador", nullable = false)
    @JsonBackReference
    private Jogador jogador;

    // Construtores
    public Pagamento() {
    }

    public Pagamento(Short ano, Byte mes, BigDecimal valor, Jogador jogador) {
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
        this.jogador = jogador;
    }

    // Getters e Setters
    public long getCodPagamento() {
        return codPagamento;
    }

    public void setCodPagamento(long codPagamento) {
        this.codPagamento = codPagamento;
    }

    public short getAno() {
        return ano;
    }

    public void setAno(short ano) {
        this.ano = ano;
    }

    public byte getMes() {
        return mes;
    }

    public void setMes(byte mes) {
        this.mes = mes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
}