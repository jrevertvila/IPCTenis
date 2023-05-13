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
import javafxmlapplication.JavaFXMLApplication;
import javafxmlapplication.controller.LoginController;
import model.Club;
import model.ClubDAOException;

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
        System.out.println("Initialize currentUser: "+JavaFXMLApplication.current_user);
        loginBtnNav.setText(JavaFXMLApplication.current_user == null ? "Iniciar Sesion" : "Mi Perfil");
        if (JavaFXMLApplication.current_user != null) {
            loadFXML_NAV(getClass().getResource("PistaSinConexion.fxml"),"PistaSinConexion.fxml");
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

    private void loadFXML_NAV(URL url, String frameName) {
        try {
            
            System.out.println("URL: " + url);
            Node frame = JavaFXMLApplication.getFrame(frameName);
            System.out.println("frame antes: "+frame);
            if (frame == null) frame = JavaFXMLApplication.setFrame(frameName, new FXMLLoader(url).load());
            System.out.println("frame despues: "+frame);
            //FXMLLoader loader = new FXMLLoader(url);
            mainWrapper.getChildren().clear();
            mainWrapper.getChildren().add(frame);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    @FXML
    private void handleNavBtn(ActionEvent event) {
        
        String view = (String) ((Node)event.getSource()).getUserData();
        System.out.println("view: " + view);
        loadFXML_NAV(getClass().getResource(view), view);
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
        System.out.println("AA:" + JavaFXMLApplication.current_user);
    }

    @FXML
    private void reloadSceneBtn(ActionEvent event) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/InicioRefactor.fxml"));
        Parent root2 = loader2.load();
        javafxmlapplication.JavaFXMLApplication.setRoot(root2);
    }

    @FXML
    private void handleLoginProfileBtn(ActionEvent event) throws IOException {
        if (JavaFXMLApplication.current_user == null) {
            loadFXML_NAV(getClass().getResource("LoginRegister.fxml"), "LoginRegister.fxml");
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
