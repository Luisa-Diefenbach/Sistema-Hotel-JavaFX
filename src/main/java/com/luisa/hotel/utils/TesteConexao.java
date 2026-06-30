package com.luisa.hotel.utils;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        Connection conexao = ConexaoBanco.conectar();

        if (conexao != null) {

            System.out.println("Conexão realizada com sucesso!");

        } else {

            System.out.println("Erro ao conectar!");

        }
    }
}