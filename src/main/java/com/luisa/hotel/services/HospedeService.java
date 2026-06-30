package com.luisa.hotel.services;

import com.luisa.hotel.model.Hospede;

public class HospedeService {

    public boolean validarHospede(Hospede hospede) {

        if (hospede.getNome() == null || hospede.getNome().isEmpty()) {
            return false;
        }

        if (hospede.getCpf() == null || hospede.getCpf().isEmpty()) {
            return false;
        }

        return true;
    }
}