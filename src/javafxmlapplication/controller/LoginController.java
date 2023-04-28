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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginUser(ActionEvent event) throws IOException, ClubDAOException{
        String nickname = login_nickname.getText();
        String passwd = login_passwd.getText();

        if (nickname.isBlank()|| passwd.isBlank()) {
            
        } else { //try to login
            Club club = Club.getInstance();
            
            try {
                
                Member user = club.getMemberByCredentials(nickname, passwd);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            System.out.println(club.existsLogin(""));
            System.out.println(club.existsLogin(nickname));
            
            /*FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/Register.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            RegisterController controladorRegistro = loader.getController();
            stage.setScene(scene);
            stage.setTitle("Demo vista de lista de personas");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();*/
            
            
            //if (user)
            //System.out.println(user);
        }
    }
    
    public boolean isLogged() throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        return club.existsLogin("");
        
    }
    
}
