package com.luisa.hotel.controller;

import com.luisa.hotel.dao.QuartoDAO;
import com.luisa.hotel.dao.ReservaDAO;
import com.luisa.hotel.model.Hospede;
import com.luisa.hotel.model.Quarto;
import com.luisa.hotel.model.Reserva;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.luisa.hotel.dao.HospedeDAO;

import java.time.LocalDate;

public class ReservaController {

    @FXML
    private ComboBox<Hospede> cbHospede;

    @FXML
    private ComboBox<Quarto> cbQuarto;

    @FXML
    private DatePicker dpCheckIn;

    @FXML
    private DatePicker dpCheckOut;

    @FXML
    private TextField txtValorTotal;

    @FXML
    private TableView<Reserva> tbReservas;

    @FXML
    private TableColumn<Reserva, Integer> colHospede;

    @FXML
    private TableColumn<Reserva, Integer> colQuarto;

    @FXML
    private TableColumn<Reserva, String> colCheckIn;

    @FXML
    private TableColumn<Reserva, String> colCheckOut;

    @FXML
    private TableColumn<Reserva, Double> colValorTotal;

    private Reserva reservaSelecionada;

    @FXML
    public void initialize() {

        colHospede.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getHospede().getId()
                ).asObject()
        );

        colQuarto.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getQuarto().getId()
                ).asObject()
        );

        colCheckIn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDataCheckin().toString()
                )
        );

        colCheckOut.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDataCheckout().toString()
                )
        );

        colValorTotal.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getValorTotal()
                )
        );

        carregarTabela();
        carregarCombos();

        tbReservas.getSelectionModel().selectedItemProperty().addListener(
                (observable, antigaReserva, novaReserva) -> {

                    if (novaReserva != null) {

                        reservaSelecionada = novaReserva;

                        cbHospede.setValue(novaReserva.getHospede());
                        cbQuarto.setValue(novaReserva.getQuarto());

                        dpCheckIn.setValue(novaReserva.getDataCheckin());
                        dpCheckOut.setValue(novaReserva.getDataCheckout());

                        txtValorTotal.setText(String.valueOf(novaReserva.getValorTotal()));
                    }
                });

    }

    @FXML
    private void cadastrarReserva() {

        if (cbHospede.getValue() == null
                || cbQuarto.getValue() == null
                || dpCheckIn.getValue() == null
                || dpCheckOut.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        try {

            LocalDate checkIn = dpCheckIn.getValue();
            LocalDate checkOut = dpCheckOut.getValue();

            if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Check-out deve ser depois do check-in!");
                alert.showAndWait();
                return;
            }

            Hospede hospede = cbHospede.getValue();
            Quarto quarto = cbQuarto.getValue();

            if (hospede == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Hóspede não encontrado.");
                alert.showAndWait();
                return;
            }

            if (quarto == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Quarto não encontrado.");
                alert.showAndWait();
                return;
            }

            Reserva reserva = new Reserva();

            reserva.setHospede(hospede);
            reserva.setQuarto(quarto);
            reserva.setDataCheckin(checkIn);
            reserva.setDataCheckout(checkOut);

            reserva.setValorTotal(0.0);

            ReservaDAO dao = new ReservaDAO();
            dao.inserir(reserva);

            carregarTabela();
            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Reserva cadastrada com sucesso!");
            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Erro ao cadastrar reserva.");
            alert.showAndWait();
        }
    }

    @FXML
    private void atualizarReserva() {

        if (reservaSelecionada == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma reserva na tabela.");
            alert.showAndWait();
            return;
        }

        if (cbHospede.getValue() == null
                || cbQuarto.getValue() == null
                || dpCheckIn.getValue() == null
                || dpCheckOut.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        try {

            LocalDate checkIn = dpCheckIn.getValue();
            LocalDate checkOut = dpCheckOut.getValue();

            if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Check-out deve ser depois do check-in!");
                alert.showAndWait();
                return;
            }

            Hospede hospede = cbHospede.getValue();
            Quarto quarto = cbQuarto.getValue();

            if (hospede == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Hóspede não encontrado.");
                alert.showAndWait();
                return;
            }

            if (quarto == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Quarto não encontrado.");
                alert.showAndWait();
                return;
            }

            reservaSelecionada.setHospede(hospede);
            reservaSelecionada.setQuarto(quarto);
            reservaSelecionada.setDataCheckin(checkIn);
            reservaSelecionada.setDataCheckout(checkOut);

            ReservaDAO dao = new ReservaDAO();
            dao.atualizar(reservaSelecionada);

            carregarTabela();
            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Reserva atualizada com sucesso!");
            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Erro ao atualizar reserva.");
            alert.showAndWait();
        }
    }

    @FXML
    private void excluirReserva() {

        if (reservaSelecionada == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma reserva na tabela.");
            alert.showAndWait();
            return;
        }

        try {

            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Deseja realmente excluir esta reserva?");

            if (confirmacao.showAndWait().get() == ButtonType.OK) {

                ReservaDAO dao = new ReservaDAO();
                QuartoDAO quartoDAO = new QuartoDAO();

                // Libera o quarto antes de excluir
                quartoDAO.atualizarStatus(
                        reservaSelecionada.getQuarto().getId(),
                        "DISPONIVEL"
                );

                dao.excluir(reservaSelecionada.getId());

                carregarTabela();
                limparCampos();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Reserva excluída com sucesso!");
                alert.showAndWait();
            }

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Erro ao excluir reserva.");
            alert.showAndWait();
        }
    }

    @FXML
    private void realizarCheckIn() {

        if (reservaSelecionada == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma reserva.");
            alert.showAndWait();
            return;
        }

        try {

            QuartoDAO quartoDAO = new QuartoDAO();

            Quarto quarto = quartoDAO.buscarPorId(
                    reservaSelecionada.getQuarto().getId()
            );

            if (quarto == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Quarto não encontrado.");
                alert.showAndWait();
                return;
            }

            if (quarto.getStatus().equalsIgnoreCase("OCUPADO")) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Este quarto já está ocupado.");
                alert.showAndWait();
                return;
            }

            quartoDAO.atualizarStatus(quarto.getId(), "OCUPADO");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Check-In realizado com sucesso!");
            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Erro no Check-In.");
            alert.showAndWait();
        }
    }

    @FXML
    private void realizarCheckOut() {

        if (reservaSelecionada == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma reserva.");
            alert.showAndWait();
            return;
        }

        try {

            LocalDate checkIn = reservaSelecionada.getDataCheckin();
            LocalDate checkOut = reservaSelecionada.getDataCheckout();

            long dias = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);

            if (dias <= 0) dias = 1;

            double valorTotal = dias * reservaSelecionada.getQuarto().getValorDiaria();

            reservaSelecionada.setValorTotal(valorTotal);

            ReservaDAO dao = new ReservaDAO();
            dao.atualizar(reservaSelecionada);

            QuartoDAO quartoDAO = new QuartoDAO();
            quartoDAO.atualizarStatus(
                    reservaSelecionada.getQuarto().getId(),
                    "DISPONIVEL"
            );

            carregarTabela();
            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Check-Out realizado! Valor: R$ " + valorTotal);
            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Erro no Check-Out.");
            alert.showAndWait();
        }
    }

    @FXML
    private void pesquisarReserva() {

        try {

            Hospede hospede = cbHospede.getValue();

            if (hospede == null) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Selecione um hóspede.");
                alert.showAndWait();
                return;
            }

            int hospedeId = hospede.getId();

            ReservaDAO dao = new ReservaDAO();

            tbReservas.setItems(
                    FXCollections.observableArrayList(
                            dao.buscarPorHospede(hospedeId)
                    )
            );

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);
            alert.setContentText("Informe um ID de hóspede válido.");

            alert.showAndWait();
        }
    }

    @FXML
    private void listar() {
        carregarTabela();
    }

    private void carregarTabela() {

        ReservaDAO dao = new ReservaDAO();

        tbReservas.setItems(
                FXCollections.observableArrayList(dao.listar())
        );
    }

    private void carregarCombos() {

        HospedeDAO hospedeDAO = new HospedeDAO();
        QuartoDAO quartoDAO = new QuartoDAO();

        cbHospede.setItems(
                FXCollections.observableArrayList(
                        hospedeDAO.listar()
                )
        );

        cbQuarto.setItems(
                FXCollections.observableArrayList(
                        quartoDAO.listar()
                )
        );
    }

    @FXML
    private void voltarMenu(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/menu.fxml")
            );

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(loader.load()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {

        cbHospede.setValue(null);
        cbQuarto.setValue(null);

        dpCheckIn.setValue(null);
        dpCheckOut.setValue(null);

        txtValorTotal.clear();

        reservaSelecionada = null;

        tbReservas.getSelectionModel().clearSelection();
    }

}