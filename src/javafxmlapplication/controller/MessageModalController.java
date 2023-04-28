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
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author joeli
 */
public class MessageModalController implements Initializable {


    @FXML
    private Button confirmBtn;
    @FXML
    private Text messageTextModal;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //confirmBtn.fire();
        
                
        // TODO
    }
    
    public void setTextMessage(String msg) { messageTextModal.setText(msg); }
    
    @FXML
    private void closeMessageModal(ActionEvent event) {
        ((Button)event.getSource()).getScene().getWindow().hide();
    }

}
