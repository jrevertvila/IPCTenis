/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class RegisterController implements Initializable {

    @FXML
    private Button registerBtn;
    @FXML
    private TextField field_nombre;
    @FXML
    private TextField field_apellidos;
    @FXML
    private TextField field_tlf;
    @FXML
    private TextField field_nickname;
    @FXML
    private TextField field_password;
    @FXML
    private TextField field_repeat_password;
    @FXML
    private TextField field_tarjeta;
    @FXML
    private TextField field_image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registerUser(ActionEvent event) throws ClubDAOException, IOException {
        String nombre = field_nombre.getText();
        String apellidos = field_apellidos.getText();
        String tlf = field_tlf.getText();
        String nickname = field_nickname.getText();
        String passwd = field_password.getText();
        String repeat_passwd = field_repeat_password.getText();
        String tarjeta = field_tarjeta.getText();
        String img = field_image.getText();
        boolean error = false;
        String textError = "";
        
        if (nombre.isBlank()){
            error = true;
            textError = "El nombre no puede estar vacío";
            //Border input with Red color
        }
        if (apellidos.isBlank()){
            error = true;
            textError = "";
            //Border input with Red color
        }
        if (tlf.isBlank()){
            error = true;
            textError = "";
            //Border input with Red color
        }
        if (nickname.isBlank()){
            error = true;
            textError = "";
            //Border input with Red color
        }
        if (passwd.isBlank()){
            error = true;
            textError = "";
            //Border input with Red color
        }
        if (repeat_passwd.isBlank()){
            error = true;
            textError = "";
            //Border input with Red color
        }
        if (tarjeta.isBlank()){
            error = true;
            textError = "";
            //Border input with Red color
        }
        
        if (!error) {
            Club club = Club.getInstance();
            Member result = club.registerMember(nombre, apellidos, tlf, nickname, passwd, tarjeta, 0,
            null);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MessageModal.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            MessageModalController controladorMessageModal = loader.getController();
            controladorMessageModal.setTextMessage("Se ha registrado correctamente. \n Inicie sesión por favor.");
            stage.setScene(scene);
            stage.setTitle("Registro correcto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            ((Button)event.getSource()).getScene().getWindow().hide();
            
        }
        
    }
    
}
