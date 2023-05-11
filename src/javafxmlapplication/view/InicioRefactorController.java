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
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class InicioRefactorController implements Initializable {

    private VBox vBoxContent;
    private BorderPane PaneContent;
    @FXML
    private Button pistasBtn;
    private Button iniSesionBtnNav;
    private Button registerBtnNav;
    @FXML
    private Button aboutUsBtn;
    @FXML
    private Button contactUsBtn;
    @FXML
    private VBox mainWrapper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void handleShowView(ActionEvent e) {
        String view = (String) ((Node)e.getSource()).getUserData();
        loadFXML_LR(getClass().getResource(view));
        Object o = e.getSource();
        Button b = (Button) o;
        if (b.getId().equals("iniSesionBtnNav")){
            System.out.println("login");
            iniSesionBtnNav.getStyleClass().add("activeLR");
            registerBtnNav.getStyleClass().remove("activeLR");
        } else {
            System.out.println("register");
            registerBtnNav.getStyleClass().add("activeLR");
            iniSesionBtnNav.getStyleClass().remove("activeLR");
            
        }
    }
    
    private void loadFXML_LR(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            PaneContent.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 
    
    private void loadFXML_NAV(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            mainWrapper.getChildren().clear();
            mainWrapper.getChildren().add(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNavBtn(ActionEvent event) {
        /*System.out.println("adadad");
        loginRegisterPane.setManaged(false);
        loginRegisterPane.setVisible(false);*/
        String view = (String) ((Node)event.getSource()).getUserData();
        loadFXML_NAV(getClass().getResource(view));
        
    }
    
    
  
    
}
