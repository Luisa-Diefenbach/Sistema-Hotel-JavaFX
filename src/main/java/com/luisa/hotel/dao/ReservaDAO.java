package com.luisa.hotel.dao;

import com.luisa.hotel.model.Hospede;
import com.luisa.hotel.model.Quarto;
import com.luisa.hotel.model.Reserva;
import com.luisa.hotel.utils.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO implements CrudDAO<Reserva> {

    @Override
    public void inserir(Reserva r) {

        String sql = """
                INSERT INTO reservas
                (hospede_id, quarto_id, data_checkin, data_checkout, valor_total)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getHospede().getId());
            stmt.setInt(2, r.getQuarto().getId());
            stmt.setString(3, r.getDataCheckin().toString());
            stmt.setString(4, r.getDataCheckout().toString());
            stmt.setDouble(5, r.getValorTotal());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Reserva r) {

        String sql = """
            UPDATE reservas
            SET hospede_id = ?,
                quarto_id = ?,
                data_checkin = ?,
                data_checkout = ?,
                valor_total = ?
            WHERE id = ?
            """;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getHospede().getId());
            stmt.setInt(2, r.getQuarto().getId());
            stmt.setString(3, r.getDataCheckin().toString());
            stmt.setString(4, r.getDataCheckout().toString());
            stmt.setDouble(5, r.getValorTotal());
            stmt.setInt(6, r.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Integer id) {

        String sql = "DELETE FROM reservas WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reserva> listar() {

        List<Reserva> lista = new ArrayList<>();

        String sql = "SELECT * FROM reservas";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            HospedeDAO hospedeDAO = new HospedeDAO();
            QuartoDAO quartoDAO = new QuartoDAO();

            while (rs.next()) {

                Reserva r = new Reserva();

                r.setId(rs.getInt("id"));

                Hospede h = hospedeDAO.buscarPorId(rs.getInt("hospede_id"));
                Quarto q = quartoDAO.buscarPorId(rs.getInt("quarto_id"));

                r.setHospede(h);
                r.setQuarto(q);

                r.setDataCheckin(Date.valueOf(rs.getString("data_checkin")).toLocalDate());
                r.setDataCheckout(Date.valueOf(rs.getString("data_checkout")).toLocalDate());

                r.setValorTotal(rs.getDouble("valor_total"));

                lista.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Reserva> buscarPorHospede(int hospedeId) {

        List<Reserva> lista = new ArrayList<>();

        String sql = "SELECT * FROM reservas WHERE hospede_id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            HospedeDAO hospedeDAO = new HospedeDAO();
            QuartoDAO quartoDAO = new QuartoDAO();

            stmt.setInt(1, hospedeId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Reserva r = new Reserva();

                r.setId(rs.getInt("id"));

                Hospede h = hospedeDAO.buscarPorId(rs.getInt("hospede_id"));
                Quarto q = quartoDAO.buscarPorId(rs.getInt("quarto_id"));

                r.setHospede(h);
                r.setQuarto(q);

                r.setDataCheckin(
                        Date.valueOf(rs.getString("data_checkin")).toLocalDate()
                );

                r.setDataCheckout(
                        Date.valueOf(rs.getString("data_checkout")).toLocalDate()
                );

                r.setValorTotal(rs.getDouble("valor_total"));

                lista.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}