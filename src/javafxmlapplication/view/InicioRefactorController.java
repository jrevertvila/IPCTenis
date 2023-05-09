/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class InicioRefactorController implements Initializable {

    private VBox vBoxContent;
    @FXML
    private BorderPane PaneContent;
    @FXML
    private Button pistasBtn;
    @FXML
    private VBox loginRegisterPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    public void handleShowView(ActionEvent e) {
        String view = (String) ((Node)e.getSource()).getUserData();
        loadFXML(getClass().getResource(view));
    }
    
    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            PaneContent.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNavBtn(ActionEvent event) {
        System.out.println("adadad");
        loginRegisterPane.setManaged(false);
        loginRegisterPane.setVisible(false);
    }
    
}
