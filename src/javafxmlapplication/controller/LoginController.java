/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;
import model.Member;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.NotificationPane;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class LoginController implements Initializable {

    @FXML
    private Button loginBtn;
    @FXML
    private TextField login_nickname;
    @FXML
    private TextField login_passwd;
    @FXML
    private Text errorNicknameLabel;
    @FXML
    private Text errorPasswdLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loginUser(ActionEvent event) throws IOException, ClubDAOException {
       submitForm();
    }

    private void submitForm() throws IOException, ClubDAOException {
         //Reset
        login_nickname.getStyleClass().remove("inputStyledError");
        errorNicknameLabel.setText("");
        login_passwd.getStyleClass().remove("inputStyledError");
        errorPasswdLabel.setText("");
        
        String nickname = login_nickname.getText();
        String passwd = login_passwd.getText();
        System.out.println("hola");
        if (nickname.isBlank() || passwd.isBlank()) {
            if (nickname.isBlank()) {
                login_nickname.getStyleClass().add("inputStyledError");
                errorNicknameLabel.setText("Este campo no puede estar vacío");
            }
            if (passwd.isBlank()) {
                login_passwd.getStyleClass().add("inputStyledError");
                errorPasswdLabel.setText("Este campo no puede estar vacío");
            }

        } else { //try to login
            Club club = Club.getInstance();

            try {

                Member user = club.getMemberByCredentials(nickname, passwd);
                JavaFXMLApplication.current_user = user;
            } catch (Exception ex) {
                System.out.println(ex);
            }

            System.out.println(club.existsLogin(nickname));

            
            if (club.existsLogin(nickname) && JavaFXMLApplication.current_user != null) {
                TrayNotification notif = new TrayNotification();
                notif.setAnimationType(AnimationType.POPUP);
                notif.setTitle("INICIO DE SESIÓN CORRECTO");
                notif.setMessage("Bienvenido " + JavaFXMLApplication.current_user.getName());
                notif.setNotificationType(NotificationType.SUCCESS);
                notif.showAndDismiss(Duration.millis(3000));

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/InicioUX_Logged.fxml"));
                JavaFXMLApplication.removeFrames(new String[]{"LoginRegister.fxml", "InicioUX.fxml", "Login.fxml", "Register.fxml"});
                Parent root2 = loader2.load();
                javafxmlapplication.JavaFXMLApplication.setRoot(root2);
            } else {
                TrayNotification notif = new TrayNotification();
                notif.setAnimationType(AnimationType.POPUP);
                notif.setTitle("ERROR EN EL INICIO DE SESIÓN");
                notif.setMessage("Nombre de usuario o contraseña incorrectos");
                notif.setNotificationType(NotificationType.ERROR);
                notif.showAndDismiss(Duration.millis(3000));
            }
        }
    }
    
    private void handleEnterKey(KeyEvent event) throws IOException, ClubDAOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            submitForm();
        }
    }
}
