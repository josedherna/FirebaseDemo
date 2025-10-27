package aydin.firebasedemo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WelcomeController {
    @FXML
    private Button registerViewButton;
    @FXML
    private Button loginViewButton;
    @FXML
    private VBox registerContainer;
    @FXML
    private VBox loginContainer;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;
    @FXML
    private VBox choiceContainer;
    @FXML
    private Button signupBackButton;
    @FXML
    private Button loginBackButton;
    @FXML
    private Label headerLabel;

    @FXML
    public void initialize() {
        loginContainer.setManaged(false);
        registerContainer.setManaged(false);
    }

    @FXML
    public void createAccount() {
    }

    @FXML
    public void viewLogin() {
        headerLabel.setText("Login");
        choiceContainer.setManaged(false);
        choiceContainer.setVisible(false);

        loginContainer.setManaged(true);
        loginContainer.setVisible(true);

        registerContainer.setManaged(false);
        registerContainer.setVisible(false);
    }

    @FXML
    public void login() {
    }

    @FXML
    public void viewRegistration() {
        headerLabel.setText("Register Account");
        choiceContainer.setManaged(false);
        choiceContainer.setVisible(false);

        registerContainer.setManaged(true);
        registerContainer.setVisible(true);
    }

    @FXML
    public void returnToChoices() {
        headerLabel.setText("Welcome");

        choiceContainer.setManaged(true);
        choiceContainer.setVisible(true);

        loginContainer.setManaged(false);
        loginContainer.setVisible(false);

        registerContainer.setManaged(false);
        registerContainer.setVisible(false);
    }
}
