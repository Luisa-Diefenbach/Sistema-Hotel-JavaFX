package com.luisa.hotel.controller;

import com.luisa.hotel.dao.HospedeDAO;
import com.luisa.hotel.model.Hospede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HospedeController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TableView<Hospede> tbHospedes;

    @FXML
    private TableColumn<Hospede, String> colNome;

    @FXML
    private TableColumn<Hospede, String> colCpf;

    @FXML
    private TableColumn<Hospede, String> colTelefone;

    @FXML
    private TableColumn<Hospede, String> colEmail;

    private Hospede hospedeSelecionado;

    @FXML
    public void initialize() {

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        carregarHospedes();

        tbHospedes.getSelectionModel().selectedItemProperty().addListener(
                (observable, antigo, novo) -> {

                    if (novo != null) {

                        hospedeSelecionado = novo;

                        txtNome.setText(novo.getNome());
                        txtCpf.setText(novo.getCpf());
                        txtTelefone.setText(novo.getTelefone());
                        txtEmail.setText(novo.getEmail());
                    }
                });
    }

    @FXML
    private void salvarHospede() {

        Hospede hospede = new Hospede();

        hospede.setNome(txtNome.getText());
        hospede.setCpf(txtCpf.getText());
        hospede.setTelefone(txtTelefone.getText());
        hospede.setEmail(txtEmail.getText());

        HospedeDAO dao = new HospedeDAO();

        dao.inserir(hospede);

        limparCampos();
        carregarHospedes();
    }

    @FXML
    private void atualizarHospede() {

        if (hospedeSelecionado == null) {
            return;
        }

        hospedeSelecionado.setNome(txtNome.getText());
        hospedeSelecionado.setCpf(txtCpf.getText());
        hospedeSelecionado.setTelefone(txtTelefone.getText());
        hospedeSelecionado.setEmail(txtEmail.getText());

        HospedeDAO dao = new HospedeDAO();

        dao.atualizar(hospedeSelecionado);

        limparCampos();
        carregarHospedes();
    }

    @FXML
    private void excluirHospede() {

        if (hospedeSelecionado == null) {
            return;
        }

        HospedeDAO dao = new HospedeDAO();

        dao.excluir(hospedeSelecionado.getId());

        limparCampos();
        carregarHospedes();
    }

    @FXML
    private void listarHospedes() {

        carregarHospedes();
    }

    private void carregarHospedes() {

        HospedeDAO dao = new HospedeDAO();

        ObservableList<Hospede> lista =
                FXCollections.observableArrayList(
                        dao.listar());

        tbHospedes.setItems(lista);
    }

    private void limparCampos() {

        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();

        hospedeSelecionado = null;

        tbHospedes.getSelectionModel().clearSelection();
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