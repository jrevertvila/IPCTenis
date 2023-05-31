/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxmlapplication.JavaFXMLApplication;
import javafxmlapplication.Utils;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private TextField oldPasswordInput;
    @FXML
    private TextField newPassword1input;
    @FXML
    private TextField newPassword2input;
    @FXML
    private Button buttonChangePassword;
    @FXML
    private Text errorOldPassword;
    @FXML
    private Text errorNewPassword1;
    @FXML
    private Text errorNewPassword2;
    @FXML
    private Button buttonCloseWindow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) buttonCloseWindow.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void submitPassword(ActionEvent event) {
       
        submit();
        
        
    }
    
    private void submit() {
        oldPasswordInput.getStyleClass().remove("inputStyledError");
        errorOldPassword.setText("");
        
        newPassword1input.getStyleClass().remove("inputStyledError");
        errorNewPassword1.setText("");
        
        newPassword2input.getStyleClass().remove("inputStyledError");
        errorNewPassword2.setText("");
      
        if (!JavaFXMLApplication.current_user.getPassword().equals(oldPasswordInput.getText())) {
            //No es igual que la contraseña anterior
            oldPasswordInput.getStyleClass().add("inputStyledError");
            errorOldPassword.setText("Contraseña incorrecta.");
            return;
            
        }
        
        if (!Utils.isSecurePassword(newPassword1input.getText())) {
            newPassword1input.getStyleClass().add("inputStyledError");
            errorNewPassword1.setText("Este campo debe contener entre 5 y 10 caracteres");
            return;
        }
        if (!Utils.isSecurePassword(newPassword2input.getText())) {
            newPassword2input.getStyleClass().add("inputStyledError");
            errorNewPassword2.setText("Este campo debe contener entre 5 y 10 caracteres");
            return;
        }
        
        if (oldPasswordInput.getText().equals(newPassword1input.getText()) || oldPasswordInput.getText().equals(newPassword2input.getText())){
            //No coinciden las nuevas contraseñas
            newPassword1input.getStyleClass().add("inputStyledError");
            newPassword2input.getStyleClass().add("inputStyledError");
            errorNewPassword1.setText("No puede ser igual que la anterior");
            errorNewPassword2.setText("No puede ser igual que la anterior");

            return;
        }
        
        if (!newPassword1input.getText().equals(newPassword2input.getText()) || newPassword1input.getText().isBlank() || newPassword2input.getText().isBlank()){
            //No coinciden las nuevas contraseñas
            newPassword1input.getStyleClass().add("inputStyledError");
            newPassword2input.getStyleClass().add("inputStyledError");
            errorNewPassword2.setText("La nueva contraseña no coincide");
            return;
        }
        
        
        
        
        
        JavaFXMLApplication.current_user.setPassword(newPassword1input.getText());
        
        TrayNotification notif = new TrayNotification();
        notif.setAnimationType(AnimationType.POPUP);
        notif.setTitle("CONTRASEÑA ACTUALIZADA");
        notif.setMessage("Se ha cambiado la contraseña correctamente");
        notif.setNotificationType(NotificationType.SUCCESS);
        notif.showAndDismiss(Duration.millis(3000));
        
        Stage stage = (Stage) buttonChangePassword.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void hadleAceptar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) submit();
    }
    
}
