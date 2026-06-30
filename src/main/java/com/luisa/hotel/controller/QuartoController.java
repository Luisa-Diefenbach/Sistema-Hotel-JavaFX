package com.luisa.hotel.controller;

import com.luisa.hotel.dao.QuartoDAO;
import com.luisa.hotel.model.Quarto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class QuartoController {

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtTipo;

    @FXML
    private TextField txtValorDiaria;

    @FXML
    private TextField txtStatus;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TableView<Quarto> tbQuartos;

    @FXML
    private TableColumn<Quarto, Integer> colNumero;

    @FXML
    private TableColumn<Quarto, String> colTipo;

    @FXML
    private TableColumn<Quarto, Double> colValorDiaria;

    @FXML
    private TableColumn<Quarto, String> colStatus;

    private Quarto quartoSelecionado;

    @FXML
    public void initialize() {

        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colValorDiaria.setCellValueFactory(new PropertyValueFactory<>("valorDiaria"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        carregarQuartos();

        tbQuartos.getSelectionModel().selectedItemProperty().addListener(
                (observable, antigo, novo) -> {

                    if (novo != null) {

                        quartoSelecionado = novo;

                        txtNumero.setText(String.valueOf(novo.getNumero()));
                        txtTipo.setText(novo.getTipo());
                        txtValorDiaria.setText(String.valueOf(novo.getValorDiaria()));
                        txtStatus.setText(novo.getStatus());
                    }
                });
    }

    @FXML
    private void salvarQuarto() {

        Quarto quarto = new Quarto();

        quarto.setNumero(Integer.parseInt(txtNumero.getText()));
        quarto.setTipo(txtTipo.getText());
        quarto.setValorDiaria(Double.parseDouble(txtValorDiaria.getText()));
        quarto.setStatus(txtStatus.getText());

        QuartoDAO dao = new QuartoDAO();
        dao.inserir(quarto);

        limparCampos();
        carregarQuartos();
    }

    @FXML
    private void atualizarQuarto() {

        if (quartoSelecionado == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um quarto na tabela!");
            alert.showAndWait();

            return;
        }

        try {

            quartoSelecionado.setNumero(Integer.parseInt(txtNumero.getText()));
            quartoSelecionado.setTipo(txtTipo.getText());
            quartoSelecionado.setValorDiaria(Double.parseDouble(txtValorDiaria.getText()));
            quartoSelecionado.setStatus(txtStatus.getText());

            QuartoDAO dao = new QuartoDAO();
            dao.atualizar(quartoSelecionado);

            carregarQuartos();
            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void excluirQuarto() {

        if (quartoSelecionado == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um quarto na tabela!");
            alert.showAndWait();

            return;
        }

        try {

            QuartoDAO dao = new QuartoDAO();
            dao.excluir(quartoSelecionado.getId());

            carregarQuartos();
            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void listarQuartos() {

        carregarQuartos();
    }

    private void carregarQuartos() {

        QuartoDAO dao = new QuartoDAO();

        ObservableList<Quarto> lista =
                FXCollections.observableArrayList(dao.listar());

        tbQuartos.setItems(lista);
    }

    private void limparCampos() {

        txtNumero.clear();
        txtTipo.clear();
        txtValorDiaria.clear();
        txtStatus.clear();

        quartoSelecionado = null;

        tbQuartos.getSelectionModel().clearSelection();
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
}