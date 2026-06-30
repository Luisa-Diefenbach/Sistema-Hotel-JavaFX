package com.luisa.hotel.dao;

import com.luisa.hotel.model.Quarto;
import com.luisa.hotel.utils.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuartoDAO implements CrudDAO<Quarto> {

    @Override
    public void inserir(Quarto quarto) {

        String sql = "INSERT INTO quartos (numero, tipo, valor_diaria, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quarto.getNumero());
            stmt.setString(2, quarto.getTipo());
            stmt.setDouble(3, quarto.getValorDiaria());
            stmt.setString(4, quarto.getStatus());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean atualizarStatus(int id, String status) {

        String sql = "UPDATE quartos SET status = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);

            int linhas = stmt.executeUpdate();

            System.out.println("Quarto ID: " + id);
            System.out.println("Status: " + status);
            System.out.println("Linhas atualizadas: " + linhas);

            return linhas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Quarto buscarPorId(int id) {

        String sql = "SELECT * FROM quartos WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Quarto quarto = new Quarto();

                quarto.setId(rs.getInt("id"));
                quarto.setNumero(rs.getInt("numero"));
                quarto.setTipo(rs.getString("tipo"));
                quarto.setValorDiaria(rs.getDouble("valor_diaria"));
                quarto.setStatus(rs.getString("status"));

                return quarto;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void atualizar(Quarto quarto) {

        String sql = "UPDATE quartos SET tipo = ?, valor_diaria = ?, status = ?, numero = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, quarto.getTipo());
            stmt.setDouble(2, quarto.getValorDiaria());
            stmt.setString(3, quarto.getStatus());
            stmt.setInt(4, quarto.getNumero());
            stmt.setInt(5, quarto.getId());

            int linhas = stmt.executeUpdate();
            System.out.println("Quartos atualizados: " + linhas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Integer id) {

        String sql = "DELETE FROM quartos WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();
            System.out.println("Quartos excluídos: " + linhas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Quarto> listar() {

        List<Quarto> lista = new ArrayList<>();

        String sql = "SELECT * FROM quartos";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Quarto q = new Quarto();

                q.setId(rs.getInt("id"));
                q.setNumero(rs.getInt("numero"));
                q.setTipo(rs.getString("tipo"));
                q.setValorDiaria(rs.getDouble("valor_diaria"));
                q.setStatus(rs.getString("status"));

                lista.add(q);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}