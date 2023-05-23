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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafxmlapplication.JavaFXMLApplication;
import javafxmlapplication.Utils;
import model.Club;
import model.ClubDAOException;
import model.Member;


/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ProfileController implements Initializable {

    @FXML
    private TextField userLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField creditCardField;
    @FXML
    private TextField photoField;
    @FXML
    private Button applyButton;
    @FXML
    private Label errorPhoneLabel;
    @FXML
    private Button changePasswordButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Member user = JavaFXMLApplication.current_user;
        //INICIAR SESION DE PRUEBA=============== BORRAR
        Club club;
        
        try { 
            club = Club.getInstance();
            Member userC = club.getMemberByCredentials("admin", "admin");
            JavaFXMLApplication.current_user = userC;  
            user = userC; //ESTA NO BORRAR:  Sustituir per: user = LoginController.current_user;
//            
//        //=======================================
//        
            userLabel.setText(user.getNickName());
            nameField.setText(user.getName());
            surnameField.setText(user.getSurname());
            phoneField.setText(user.getTelephone());
            creditCardField.setText(user.getCreditCard());   
//        //========================================    BORRAR
        } catch (ClubDAOException ex) {
        } catch (IOException ex) {
        }
        //==========================================   
    }

    private void handleHomeButton(ActionEvent event) throws IOException {
         FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/InicioRefactor.fxml"));
         Parent root2 = loader2.load();
         JavaFXMLApplication.setRoot(root2);
    }

    @FXML
    private void applyChangesButton(ActionEvent event) {
        boolean error = false;
        if(!Utils.isOnlyLetters(nameField.getText())) {
            error = true;
            //nameErrorMessage.setText("Introduce unicamente letras")
        }
        if(!Utils.isOnlyLetters(surnameField.getText())) {
           error = true;
           //surnameErrorMessage.setText("Introduce únicamente letras")   
        }
        if(!Utils.isPhoneNumber(phoneField.getText())) {
            error = true;
            //phoneErrorMessage.setText("Introduce únicamente 9 números")
        }
        if(!Utils.isCreditCard(creditCardField.getText())) {
            error=true;
            //crediCardErrorMessage.setText("Introduce únicamente 16 números")
        }
        if (!error) {
            JavaFXMLApplication.current_user.setName(nameField.getText());
            JavaFXMLApplication.current_user.setSurname(surnameField.getText());
            JavaFXMLApplication.current_user.setTelephone(phoneField.getText());
            JavaFXMLApplication.current_user.setCreditCard(creditCardField.getText());
        } 
    }

    @FXML
    private void changePassword(ActionEvent event) {
       
    }


}


 