package com.example.JavaFXLogin;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterController extends LoginController {

    @FXML
    private Label registerMensagemLabel;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField confirmpasswordTextField;
    @FXML
    private Label loginMensagemLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField primeironomeTextField;
    @FXML
    private TextField segundonomeTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label primeiroNomeLabel;
    @FXML
    private Label segundoNomeLabel;
    @FXML
    private Label usuarioLabel;
    @FXML
    private Label senhaLabel;
    @FXML
    private Label mensagemSenhaDupla;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void registerButtonOnAction(ActionEvent event) {

        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank() && !primeironomeTextField.getText().isBlank() && !segundonomeTextField.getText().isBlank() && !confirmpasswordTextField.getText().isBlank()) {

            if (passwordTextField.getText().equals(confirmpasswordTextField.getText())) {
                registerUser();
            } else {
                loginMensagemLabel.setText("Senhas não se combinam!");
            }
        } else {
            loginMensagemLabel.setText("Por favor preencha os campos em Branco!");
        }
    }

    public void initialize() {
        // Adiciona o evento para validar enquanto digita
        primeironomeTextField.setOnKeyReleased(event -> validarEntradaPrimeiro(primeironomeTextField));
        segundonomeTextField.setOnKeyReleased(event -> validarEntradaSegundo(segundonomeTextField));
        usernameTextField.setOnKeyReleased(event -> validarEntradaUsuario(usernameTextField));
        passwordTextField.setOnKeyReleased(event -> validarEntradaSenha(passwordTextField));
        confirmpasswordTextField.setOnKeyReleased(event -> validarDuasSenhas());
    }

    public void registerUser() {

        DBConnection connectionNow = new DBConnection();
        Connection connectDB = connectionNow.getConnection();

        String firstname = primeironomeTextField.getText();
        String lastname = segundonomeTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        String hashedPassword = encoder.encode(password);

        String checkUserSQL = "SELECT username FROM user_account WHERE username = ?";

//        if (!isValidInput(firstname, 3, 25)) {
//            registerMensagemLabel.setText("Nome deve ter entre 3 e 20 caracteres.");
//            return;
//        }
//        if (!isValidInput(lastname, 3, 20)) {
//            registerMensagemLabel.setText("Sobrenome deve ter entre 3 e 20 caracteres.");
//            return;
//        }
//        if (!isValidInput(username, 6, 15)) {
//            registerMensagemLabel.setText("Usuário deve ter entre 5 e 15 caracteres.");
//            return;
//        }
//        if (!isValidPassword(password)) {
//            registerMensagemLabel.setText("Senha deve ter 8-20 caracteres, 1 número e 1 caractere especial.");
//            return;
//        }


        try {

            PreparedStatement checkStatement = connectDB.prepareStatement(checkUserSQL);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                loginMensagemLabel.setText("");
                registerMensagemLabel.setText("Nome de usuário já cadastrado!");
            } else {
                String insertSQL = "INSERT INTO user_account (firstname, lastname, username, password) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStatement = connectDB.prepareStatement(insertSQL);
                insertStatement.setString(1, firstname);
                insertStatement.setString(2, lastname);
                insertStatement.setString(3, username);
                insertStatement.setString(4, hashedPassword);
                insertStatement.executeUpdate();
                loginMensagemLabel.setText("");
                registerMensagemLabel.setText("Usuário cadastrado com sucesso!");

                insertStatement.close();
            }

            resultSet.close();
            checkStatement.close();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            loginMensagemLabel.setText("");
            registerMensagemLabel.setText("Erro no cadastro!");
        }

    }

    private void validarEntradaPrimeiro(TextField campo) {
        String texto = campo.getText();
        if (!texto.matches("^[a-zA-Z]{3,20}$")) {
            primeiroNomeLabel.setText("Apenas letras são permitidos (3-20 caracteres)");
            primeiroNomeLabel.setStyle("-fx-text-fill: red; -fx-background-color:#f2f2f2; -fx-background-radius: 3px; -fx-padding:5px;");
            loginMensagemLabel.setText("");
        } else {
            primeiroNomeLabel.setText("Primeiro Nome");
            primeiroNomeLabel.setStyle("-fx-text-fill: #f2f2f2;");
        }
    }

    private void validarEntradaSegundo(TextField campo) {
        String texto = campo.getText();
        if (!texto.matches("^[a-zA-Z]{3,20}$")) {
            segundoNomeLabel.setText("Apenas letras são permitidos (3-20 caracteres)");
            segundoNomeLabel.setStyle("-fx-text-fill: red; -fx-background-color:#f2f2f2; -fx-background-radius: 3px; -fx-padding:5px;");
            loginMensagemLabel.setText("");
        } else {
            segundoNomeLabel.setText("Segundo Nome");
            segundoNomeLabel.setStyle("-fx-text-fill: #f2f2f2;");
        }
    }

    private void validarEntradaUsuario(TextField campo) {
        String texto = campo.getText();
        if (!texto.matches("^[a-zA-Z0-9]{6,20}$")) {
            usuarioLabel.setText("Apenas letras e numeros são permitidos (6-20 caracteres)");
            usuarioLabel.setStyle("-fx-text-fill: red; -fx-background-color:#f2f2f2; -fx-background-radius: 3px; -fx-padding:5px;");
            loginMensagemLabel.setText("");
        } else {
            usuarioLabel.setText("Nome de Usuário");
            usuarioLabel.setStyle("-fx-text-fill: #f2f2f2;");
        }
    }

    private void validarEntradaSenha(PasswordField campo) {
        String texto = campo.getText();
        if (!texto.matches("^(?=.*[0-9])(?=.*[!@#$%&*]).{8,20}$")) {
            senhaLabel.setText("Senha deve ter 8-20 caracteres, 1 número e 1 caractere especial.");
            senhaLabel.setStyle("-fx-text-fill: red; -fx-background-color:#f2f2f2; -fx-background-radius: 3px; -fx-padding:5px;");
            loginMensagemLabel.setText("");
        } else {
            senhaLabel.setText("Senha");
            senhaLabel.setStyle("-fx-text-fill: #f2f2f2;");
        }
    }

    private void validarDuasSenhas(){

        String senha = passwordTextField.getText();
        String confirmacao = confirmpasswordTextField.getText();

        if (!senha.equals(confirmacao)) {
            mensagemSenhaDupla.setText("As senhas não coincidem!");
            mensagemSenhaDupla.setStyle("-fx-text-fill: red; -fx-background-color:#f2f2f2; -fx-background-radius: 3px; -fx-padding:5px; ");
            loginMensagemLabel.setText("");
        } else {
            mensagemSenhaDupla.setText("");
        }
    }


//    private boolean isValidPassword(String password) {
//        String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%&*]).{8,20}$";
//        return password != null && Pattern.matches(passwordRegex, password);
//    }

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

    public void voltarInicioLogin(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Obtém o Stage atual
        switchScene("login.fxml", stage); // Muda para a tela de registro
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

}
