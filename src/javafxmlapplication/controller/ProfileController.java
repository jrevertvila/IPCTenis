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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafxmlapplication.JavaFXMLApplication;
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
        try 
		{ 
			Integer.parseInt(phoneField.getText()); 
			JavaFXMLApplication.current_user.setName(phoneField.getText());
		}  
	catch (NumberFormatException e)  
		{ 
                    error = true;
                    errorPhoneLabel.setText("Solo números");
                   // phoneField.setText(user.getTelephone());
                    
		} 
        
        if (!error) {
            JavaFXMLApplication.current_user.setName(phoneField.getText());
 
            JavaFXMLApplication.current_user.setName(nameField.getText());
            JavaFXMLApplication.current_user.setSurname(surnameField.getText());
            if (phoneField.getText().length() == 9  ) {
                JavaFXMLApplication.current_user.setTelephone(phoneField.getText());
            }
            JavaFXMLApplication.current_user.setTelephone(phoneField.getText());
            JavaFXMLApplication.current_user.setCreditCard(creditCardField.getText());
        } 
        
     
    }
}


 