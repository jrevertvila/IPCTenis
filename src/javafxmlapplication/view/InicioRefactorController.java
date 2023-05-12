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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafxmlapplication.controller.LoginController;

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
    @FXML
    private Button loginBtnNav;
    private String loginBtnNavText = "Mi perfil";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize currentUser: "+LoginController.current_user);
        loginBtnNav.setText(LoginController.current_user == null ? "Iniciar Sesion" : "Mi Perfil");
        if (LoginController.current_user != null) {
            loadFXML_NAV(getClass().getResource("PistaSinConexion.fxml"));
            loginBtnNav.setUserData("Profile.fxml");
        }
//        loginBtnNav.setOnAction((e) -> loginBtnNav.setText("Mi Perfil"));
        // TODO
//        loginBtnNav.textProperty().bind(loginBtnNavText.);
        //iniSesionBtnNav.setText("Profile");
    }
    public String getLoginBtnNavText() {
        return this.loginBtnNavText;
    }
    
    public void setLoginBtnNavText(String s) {
        this.loginBtnNavText = s;
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
        
        System.out.println("yeayea");
        String view = (String) ((Node)event.getSource()).getUserData();
        loadFXML_NAV(getClass().getResource(view));
        Object o = event.getSource();
        Button b = (Button) o;
        switch (b.getId()) {
            case "pistasBtn":
                pistasBtn.getStyleClass().add("activeNAV");
                aboutUsBtn.getStyleClass().remove("activeNAV");
                contactUsBtn.getStyleClass().remove("activeNAV");
                break;
            case "aboutUsBtn":
                aboutUsBtn.getStyleClass().add("activeNAV");
                pistasBtn.getStyleClass().remove("activeNAV");
                contactUsBtn.getStyleClass().remove("activeNAV");
                break;
            case "contactUsBtn":
                contactUsBtn.getStyleClass().add("activeNAV");
                pistasBtn.getStyleClass().remove("activeNAV");
                aboutUsBtn.getStyleClass().remove("activeNAV");
                break;
            default:
                break;
        }
    
        
    }

    @FXML
    private void testLogin(ActionEvent event) {
        System.out.println("AA:" + LoginController.current_user);
    }

    @FXML
    private void reloadSceneBtn(ActionEvent event) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/InicioRefactor.fxml"));
        Parent root2 = loader2.load();
        javafxmlapplication.JavaFXMLApplication.setRoot(root2);
    }

    @FXML
    private void handleLoginProfileBtn(ActionEvent event) throws IOException {
        if (LoginController.current_user == null) {
            loadFXML_NAV(getClass().getResource("LoginRegister.fxml"));
            pistasBtn.getStyleClass().remove("activeNAV");
            aboutUsBtn.getStyleClass().remove("activeNAV");
            contactUsBtn.getStyleClass().remove("activeNAV");
        } else {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/Profile.fxml"));
            Parent root2 = loader2.load();
            javafxmlapplication.JavaFXMLApplication.setRoot(root2);
        }
        
    }
    
    
  
    
}
