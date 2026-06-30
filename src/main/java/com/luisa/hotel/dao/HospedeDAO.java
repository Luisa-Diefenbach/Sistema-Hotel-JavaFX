package com.luisa.hotel.dao;

import com.luisa.hotel.model.Hospede;
import com.luisa.hotel.utils.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospedeDAO implements CrudDAO<Hospede> {

    @Override
    public void inserir(Hospede hospede) {

        String sql = "INSERT INTO hospedes (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getCpf());
            stmt.setString(3, hospede.getTelefone());
            stmt.setString(4, hospede.getEmail());

            stmt.executeUpdate();

            System.out.println("Hóspede cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Hospede hospede) {

        String sql = "UPDATE hospedes SET nome=?, cpf=?, telefone=?, email=? WHERE id=?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getCpf());
            stmt.setString(3, hospede.getTelefone());
            stmt.setString(4, hospede.getEmail());
            stmt.setInt(5, hospede.getId());

            stmt.executeUpdate();

            System.out.println("Hóspede atualizado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Integer id) {

        String sql = "DELETE FROM hospedes WHERE id=?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Hóspede removido!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Hospede> listar() {

        List<Hospede> lista = new ArrayList<>();

        String sql = "SELECT * FROM hospedes";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Hospede hospede = new Hospede();

                hospede.setId(rs.getInt("id"));
                hospede.setNome(rs.getString("nome"));
                hospede.setCpf(rs.getString("cpf"));
                hospede.setTelefone(rs.getString("telefone"));
                hospede.setEmail(rs.getString("email"));

                lista.add(hospede);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Hospede buscarPorId(int id) {

        String sql = "SELECT * FROM hospedes WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Hospede hospede = new Hospede();

                hospede.setId(rs.getInt("id"));
                hospede.setNome(rs.getString("nome"));
                hospede.setCpf(rs.getString("cpf"));
                hospede.setTelefone(rs.getString("telefone"));
                hospede.setEmail(rs.getString("email"));

                return hospede;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}