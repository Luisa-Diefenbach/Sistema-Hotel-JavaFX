package com.luisa.hotel.model;

public class Quarto {

    private Integer id;
    private Integer numero;
    private String tipo;
    private Double valorDiaria;
    private String status;

    public Quarto() {
    }

    public Quarto(Integer id, Integer numero, String tipo, Double valorDiaria, String status) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.valorDiaria = valorDiaria;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(Double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return numero + " - " + tipo;
    }

}