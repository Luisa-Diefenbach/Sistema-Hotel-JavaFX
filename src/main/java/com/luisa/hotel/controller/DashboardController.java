package com.luisa.hotel.controller;

import com.luisa.hotel.utils.ConexaoBanco;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DashboardController {

    @FXML
    private Label lblHospedes;

    @FXML
    private Label lblQuartos;

    @FXML
    private Label lblDisponiveis;

    @FXML
    private Label lblOcupados;

    @FXML
    private Label lblReservas;

    @FXML
    private PieChart graficoOcupacao;

    @FXML
    public void initialize() {

        try (Connection conn = ConexaoBanco.conectar()) {

            Statement stmt = conn.createStatement();

            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM hospedes");
            if (rs1.next()) lblHospedes.setText(String.valueOf(rs1.getInt(1)));

            ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM quartos");
            if (rs2.next()) lblQuartos.setText(String.valueOf(rs2.getInt(1)));

            ResultSet rs3 = stmt.executeQuery("SELECT COUNT(*) FROM quartos WHERE status='DISPONIVEL'");
            if (rs3.next()) lblDisponiveis.setText(String.valueOf(rs3.getInt(1)));

            ResultSet rs4 = stmt.executeQuery("SELECT COUNT(*) FROM quartos WHERE status='OCUPADO'");
            if (rs4.next()) lblOcupados.setText(String.valueOf(rs4.getInt(1)));

            ResultSet rs5 = stmt.executeQuery("SELECT COUNT(*) FROM reservas");
            if (rs5.next()) lblReservas.setText(String.valueOf(rs5.getInt(1)));

            graficoOcupacao.getData().addAll(
                    new PieChart.Data("Disponíveis", Integer.parseInt(lblDisponiveis.getText())),
                    new PieChart.Data("Ocupados", Integer.parseInt(lblOcupados.getText()))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}