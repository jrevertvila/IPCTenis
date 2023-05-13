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
    private Button homeButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Member user = JavaFXMLApplication.current_user;
        //INICIAR SESION DE PRUEBA=============== BORRAR
//        Club club;
//        
//        try { 
//            club = Club.getInstance();
//            Member userC = club.getMemberByCredentials("admin", "admin");
//            LoginController.current_user = userC;  
//            user = userC; //ESTA NO BORRAR:  Sustituir per: user = LoginController.current_user;
//            
//        //=======================================
//        
//            userLabel.setText(user.getNickName());
//            
//            
//            
//            
//            
//            
//            
//            
//            
//            
//        //========================================    BORRAR
//        } catch (ClubDAOException ex) {
//        } catch (IOException ex) {
//        }
        //========================================    
        
        userLabel.setText(user.getNickName());
    }

    @FXML   
    private void handleHomeButton(ActionEvent event) throws IOException {
         FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/InicioRefactor.fxml"));
         Parent root2 = loader2.load();
         JavaFXMLApplication.setRoot(root2);
    }
    
    
}
