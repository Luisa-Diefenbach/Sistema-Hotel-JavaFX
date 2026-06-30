package com.luisa.hotel.model;

import java.time.LocalDate;

public class Reserva {

    private Integer id;
    private Hospede hospede;
    private Quarto quarto;
    private LocalDate dataCheckin;
    private LocalDate dataCheckout;
    private Double valorTotal;

    public Reserva() {
    }

    public Reserva(Integer id, Hospede hospede, Quarto quarto,
                   LocalDate dataCheckin, LocalDate dataCheckout,
                   Double valorTotal) {

        this.id = id;
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataCheckin = dataCheckin;
        this.dataCheckout = dataCheckout;
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public LocalDate getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(LocalDate dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public LocalDate getDataCheckout() {
        return dataCheckout;
    }

    public void setDataCheckout(LocalDate dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}