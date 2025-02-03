package com.example.testefxlogin;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginController  {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Pane rootPane;

    @FXML
    void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private Label loginMensagemLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;



    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void loginButtonOnAction(ActionEvent event) {

        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginMensagemLabel.setText("Por favor preencha o Usuário e a Senha!");
            loginMensagemLabel.setStyle("-fx-text-fill: red; -fx-background-color:#f2f2f2; -fx-background-radius: 3px; -fx-padding:5px; ");
        }
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void validateLogin() {
        DBConnection connectionOn = new DBConnection();
        Connection connectDB = connectionOn.getConnection();

        String verifyLogin = "SELECT password FROM user_account WHERE username = ?";

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();


        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, username); // Define o primeiro parâmetro

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                String storedHashedPassword = queryResult.getString("password"); // Obtendo o hash da senha do banco
                if (encoder.matches(password, storedHashedPassword)) {
                    loginMensagemLabel.setText("Login feito com sucesso!");
                    loginMensagemLabel.setStyle("-fx-text-fill: #bcffc7; ");
                } else {
                    loginMensagemLabel.setText("Usuário ou Senha incorreta.");
                    loginMensagemLabel.setStyle("-fx-text-fill: red; -fx-background-color:#f2f2f2; -fx-background-radius: 3px; -fx-padding:5px; ");
                }
            } else {
                loginMensagemLabel.setText("Usuário ou Senha incorreta.");
                loginMensagemLabel.setStyle("-fx-text-fill: red; -fx-background-color:#f2f2f2; -fx-background-radius: 3px; -fx-padding:5px; ");
            }

            queryResult.close();
            preparedStatement.close();
            connectDB.close();

        } catch (Exception e) {
            e.printStackTrace();
            loginMensagemLabel.setText("Erro ao tentar realizar o login.");
        }
    }


    public void switchScene(String fxmlFile, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, 550, 700);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountForm(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Obtém o Stage atual
        switchScene("register.fxml", stage); // Muda para a tela de registro
    }

    public void voltarInicioLogin(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Obtém o Stage atual
        switchScene("login.fxml", stage); // Muda para a tela de registro
    }

}