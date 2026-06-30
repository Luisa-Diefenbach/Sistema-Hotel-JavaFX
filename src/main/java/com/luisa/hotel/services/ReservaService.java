package com.luisa.hotel.services;

import com.luisa.hotel.model.Quarto;
import com.luisa.hotel.model.Reserva;

import java.time.temporal.ChronoUnit;

public class ReservaService {

    // Calcula o valor total da reserva
    public double calcularValorTotal(Reserva reserva) {

        long dias = ChronoUnit.DAYS.between(
                reserva.getDataCheckin(),
                reserva.getDataCheckout()
        );

        if (dias <= 0) {
            throw new IllegalArgumentException(
                    "A data de check-out deve ser maior que a data de check-in."
            );
        }

        return dias * reserva.getQuarto().getValorDiaria();
    }

    // Realiza o check-in
    public void realizarCheckIn(Quarto quarto) {

        quarto.setStatus("OCUPADO");
    }

    // Realiza o check-out
    public void realizarCheckOut(Quarto quarto) {

        quarto.setStatus("DISPONIVEL");
    }
}