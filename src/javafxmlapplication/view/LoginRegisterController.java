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
import javafxmlapplication.JavaFXMLApplication;

/**
 * FXML Controller class
 *  
 * @author joeli
 */
public class LoginRegisterController implements Initializable {

    @FXML
    private VBox loginRegisterPane;
    @FXML
    private Button iniSesionBtnNav;
    @FXML
    public Button registerBtnNav;
    @FXML
    private VBox PaneContent;

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
        loadFXML_LR(getClass().getResource(view),view);
        Object o = e.getSource();
        Button b = (Button) o;
        if (b.getId().equals("iniSesionBtnNav")){
            if (!iniSesionBtnNav.getStyleClass().contains("activeLR")) iniSesionBtnNav.getStyleClass().add("activeLR");
            registerBtnNav.getStyleClass().remove("activeLR");
        } else {
            if (!registerBtnNav.getStyleClass().contains("activeLR")) registerBtnNav.getStyleClass().add("activeLR");
            iniSesionBtnNav.getStyleClass().remove("activeLR");
        }
    }
    
//    public static void setLoginPage() {
////        String view = (String) ((Node)e.getSource()).getUserData();
//        loadFXML_LR(getClass().getResource("Login.fxml"),"Login.fxml");
////        Object o = e.getSource();
////        Button b = (Button) o;
//        iniSesionBtnNav.getStyleClass().add("activeLR");
//        registerBtnNav.getStyleClass().remove("activeLR");
//            
//    }
    
    public void loadFXML_LR(URL url, String frameName) {
        try {
            Node frame = JavaFXMLApplication.getFrame(frameName);
            if (frame == null) frame = JavaFXMLApplication.setFrame(frameName, new FXMLLoader(url).load());
            System.out.println(frame);
            PaneContent.getChildren().clear();
            PaneContent.getChildren().add(frame);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//    public static void handleLoginBtnClick() {
//        registerBtnNav.fire();
//    }
    
}


