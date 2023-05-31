/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafxmlapplication.JavaFXMLApplication;
import javafxmlapplication.controller.InicioUXController;

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
    public VBox PaneContent;

    public String controlar_stage;
    @FXML
    private Text text_controler_stage;

    public static StringProperty controlStageProperty = new SimpleStringProperty(LocalDateTime.now().toString());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        text_controler_stage.textProperty().bind(controlStageProperty);
        text_controler_stage.textProperty().addListener((e) -> {
            loadFXML_LR(getClass().getResource("../view/Login.fxml"), "Login.fxml");
            registerBtnNav.getStyleClass().remove("activeLR");
            iniSesionBtnNav.getStyleClass().add("activeLR");
        });
    }

    @FXML
    public void handleShowView(ActionEvent e) {
        String view = (String) ((Node) e.getSource()).getUserData();
        loadFXML_LR(getClass().getResource("../view/" + view), view);
        Object o = e.getSource();
        Button b = (Button) o;
        if (b.getId().equals("iniSesionBtnNav")) {
            if (!iniSesionBtnNav.getStyleClass().contains("activeLR")) {
                iniSesionBtnNav.getStyleClass().add("activeLR");
            }
            registerBtnNav.getStyleClass().remove("activeLR");
        } else {
            if (!registerBtnNav.getStyleClass().contains("activeLR")) {
                registerBtnNav.getStyleClass().add("activeLR");
            }
            iniSesionBtnNav.getStyleClass().remove("activeLR");
        }
    }

    public static void isRegistered() {
        System.out.println("javafxmlapplication.controller.LoginRegisterController.isRegistered()");

    }

    public void loadFXML_LR(URL url, String frameName) {
        try {
            Node frame = JavaFXMLApplication.setFrame(frameName, new FXMLLoader(url).load());
            PaneContent.getChildren().clear();
            PaneContent.getChildren().add(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
