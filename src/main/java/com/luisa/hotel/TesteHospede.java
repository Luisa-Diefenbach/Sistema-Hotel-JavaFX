package com.luisa.hotel;

import com.luisa.hotel.dao.HospedeDAO;
import com.luisa.hotel.model.Hospede;

public class TesteHospede {

    public static void main(String[] args) {

        HospedeDAO dao = new HospedeDAO();

        Hospede hospede = new Hospede();

        hospede.setNome("Luisa");
        hospede.setCpf("12345678900");
        hospede.setTelefone("51999999999");
        hospede.setEmail("luisa@email.com");

        dao.inserir(hospede);

        System.out.println("Teste finalizado!");
    }
}