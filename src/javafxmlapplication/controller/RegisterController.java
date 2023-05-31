/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import static javafxmlapplication.JavaFXMLApplication.current_user;
import javafxmlapplication.Utils;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class RegisterController implements Initializable {

    @FXML
    private Button registerBtn;
    private TextField field_nombre;
    private TextField field_apellidos;
    private TextField field_tlf;
    @FXML
    private TextField field_nickname;
    @FXML
    private TextField field_password;
    private TextField field_repeat_password;
    @FXML
    private TextField field_tarjeta;
    public final FileChooser fileChooser = new FileChooser();
    private File imgFile;
    private Text imageNameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label nicknameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label creditCardLabel;
    @FXML
    private ImageView imagenProfile;
    @FXML
    private Label svcLabel;
    @FXML
    private TextField field_name;
    @FXML
    private TextField field_surname;
    @FXML
    private TextField field_phone;
    @FXML
    private TextField field_svc;
    @FXML
    private Button uploadFileButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenProfile.setImage(new Image(getClass().getResource("../../icons/default-profile.png") + ""));
        InicioUXController.pageTitleProperty.set("Formulario de registro");
    }

    @FXML
    private void registerUser(ActionEvent event) throws ClubDAOException, IOException {
        field_name.getStyleClass().remove("inputStyledError");
        nameLabel.setText("");

        field_surname.getStyleClass().remove("inputStyledError");
        surnameLabel.setText("");

        field_phone.getStyleClass().remove("inputStyledError");
        phoneLabel.setText("");

        field_tarjeta.getStyleClass().remove("inputStyledError");
        creditCardLabel.setText("");

        field_svc.getStyleClass().remove("inputStyledError");
        svcLabel.setText("");

        field_nickname.getStyleClass().remove("inputStyledError");
        nicknameLabel.setText("");

        field_password.getStyleClass().remove("inputStyledError");
        passwordLabel.setText("");

        boolean error = false;
        boolean name_error = false;
        boolean surname_error = false;
        boolean phone_error = false;
        boolean creditCard_error = false;
        boolean csv_error = false;
        boolean nickname_error = false;
        boolean password_error = false;

        if (!Utils.isOnlyLetters(field_name.getText()) || field_name.getText().isBlank()) {
            error = true;
            nameLabel.setText("No puede contener números");
            field_name.getStyleClass().add("inputStyledError");
            name_error = true;
        }
        if (!Utils.isOnlyLetters(field_surname.getText()) || field_surname.getText().isBlank()) {
            error = true;
            surname_error = true;
            surnameLabel.setText("No puede contener números");
            field_surname.getStyleClass().add("inputStyledError");
        }
        if (!Utils.isPhoneNumber(field_phone.getText()) || field_phone.getText().isBlank()) {
            error = true;
            phone_error = true;
            phoneLabel.setText("No puede contener valores no numéricos");
            field_phone.getStyleClass().add("inputStyledError");
        }
        if (!Utils.isCreditCard(field_tarjeta.getText())) {
            error = true;
            creditCard_error = true;

            creditCardLabel.setText("Introduzca una tarjeta válida");
            field_tarjeta.getStyleClass().add("inputStyledError");
        }

        if (field_tarjeta.getText().isBlank()) {
            if (!field_svc.getText().isBlank()) {
                error = true;
                csv_error = true;
                creditCardLabel.setText("No puedes introducir svc sin tarjeta");
                field_svc.getStyleClass().add("inputStyledError");
                field_tarjeta.getStyleClass().add("inputStyledError");
            }

        } else {
            if (!Utils.isCSV(field_svc.getText())) {
                error = true;
                csv_error = true;
                svcLabel.setText("solo 3");
                field_svc.getStyleClass().add("inputStyledError");
            }
        }

        if (!field_tarjeta.getText().isBlank()) {
            if (field_svc.getText().isBlank()) {
                error = true;
                csv_error = true;
                creditCardLabel.setText("Debe introducirse el SVC");
                field_svc.getStyleClass().add("inputStyledError");
            }
        }

        if (!Utils.isCualquiera(field_nickname.getText()) || field_nickname.getText().isBlank()) {
            error = true;
            nickname_error = true;
            nicknameLabel.setText("No puede contener espacios");
            field_nickname.getStyleClass().add("inputStyledError");
        }
        if (!Utils.isSecurePassword(field_password.getText()) || field_password.getText().isBlank()) {
            error = true;
            password_error = true;
            passwordLabel.setText("Debe contener entre 5 y 10 caracteres");
            field_password.getStyleClass().add("inputStyledError");
        }

        String nombre = field_name.getText();
        String apellidos = field_surname.getText();
        String tlf = field_phone.getText();
        String nickname = field_nickname.getText();
        String passwd = field_password.getText();
        String tarjeta = field_tarjeta.getText();
        int svc = 0;

        try {
            svc = Integer.parseInt(field_svc.getText());
        } catch (NumberFormatException e) {
        }

        if (!error) {
            Club club = Club.getInstance();
            try {
                Member result = club.registerMember(nombre, apellidos, tlf, nickname, passwd,
                        tarjeta.isBlank() ? "" : tarjeta,
                        svc == 0 ? 0 : svc,
                        imgFile == null
                                ? new Image(getClass().getResource("../../icons/default-profile.png") + "")
                                : new Image(imgFile.getAbsolutePath())
                );
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro Completado");
                alert.setHeaderText("Registro Completado");
                alert.setContentText("Te has registrado correctamente. Inicia sesión por favor");
                alert.showAndWait();
                LoginRegisterController.controlStageProperty.set(LocalDateTime.now().toString());
            } catch (ClubDAOException e) {
                error = true;
                nickname_error = true;
                nicknameLabel.setText("Este nombre de usuario ya está en uso");
                field_nickname.getStyleClass().add("inputStyledError");
            }

        }

    }

    @FXML
    private void uploadImage(ActionEvent event) {
        imgFile = Utils.uploadImage(event);
        Image image = new Image(imgFile.toURI().toString());
        imagenProfile.setImage(image);
    }

}
