package com.luisa.hotel.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.luisa.hotel.dao.UsuarioDAO;

public class LoginController {

    // 👇 AQUI (variáveis da tela)
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    // 👇 AQUI (método do botão)
    @FXML
    private void fazerLogin() {

        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Preencha usuário e senha!");
            alert.showAndWait();
            return;
        }

        try {

            UsuarioDAO dao = new UsuarioDAO();

            if (dao.login(usuario, senha)) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Login realizado com sucesso!");
                alert.showAndWait();

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/fxml/menu.fxml")
                );

                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // fecha tela de login
                txtUsuario.getScene().getWindow().hide();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Usuário ou senha inválidos!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}