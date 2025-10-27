package aydin.firebasedemo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
    private TextField emailSignupField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordSignupField;
    @FXML
    private TextField displayNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private VBox rootVbox;

    @FXML
    public void initialize() {
        loginContainer.setManaged(false);
        registerContainer.setManaged(false);
    }

    @FXML
    public void createAccount() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(emailSignupField.getText())
                .setEmailVerified(false)
                .setPassword(passwordSignupField.getText())
                .setPhoneNumber("+1" + phoneNumberField.getText())
                .setDisplayName(displayNameField.getText())
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = DemoApp.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");

            DocumentReference docRef = DemoApp.fstore.collection("Users").document(userRecord.getUid());
            Map<String, Object> data = new HashMap<>();
            data.put("Email", emailSignupField.getText());
            data.put("Password", passwordSignupField.getText());

            //asynchronously write data
            ApiFuture<WriteResult> result = docRef.set(data);
            viewLogin();

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating a new user in the firebase");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Creation Error");
            alert.setHeaderText("Error Creating User");
            alert.setContentText("User could not be created. Text fields cannot be empty.");
            alert.showAndWait();
        }
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
        try {
            UserRecord userRecord = DemoApp.fauth.getUserByEmail(emailField.getText());
            String userId = userRecord.getUid();
            DocumentReference docRef = DemoApp.fstore.collection("Users").document(userId);

            ApiFuture<DocumentSnapshot> collectionLookup = docRef.get();

            try {
                DocumentSnapshot document = collectionLookup.get();
                if (document.exists()) {
                    if (Objects.equals(document.get("Email"), emailField.getText()) && Objects.equals(document.get("Password"), passwordField.getText())) {
                        System.out.println("Successfully logged in");
                        try {
                            FXMLLoader primary = new FXMLLoader(getClass().getResource("primary.fxml"));
                            Parent root = primary.load();

                            PrimaryController primaryController = primary.getController();
                            primaryController.setDisplayName(userRecord.getDisplayName());

                            Scene currentScene = rootVbox.getScene();
                            currentScene.setRoot(root);
                        } catch (Exception e) {
                            System.out.println("Error loading registration screen.");
                        }
                    }
                } else {
                    System.out.println("No such document!");
                }
            } catch (Exception e) {
                System.err.println("Error getting document: " + e.getMessage());
            }

        } catch (FirebaseAuthException e) {
            System.out.println("Login error");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Login Error");
            alert.setContentText("Incorrect Email or Password");
            alert.showAndWait();
        }
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
