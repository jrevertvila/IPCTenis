/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class DashboardController implements Initializable {

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button logout;
    @FXML
    private ImageView imgSidebar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Stage stage = JavaFXMLApplication.currentStage;
        final Circle clip = new Circle(300, 200, 200);
        imgSidebar.setClip(clip);
        
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(stage.getTitle());
            //if (stage.getTitle())
     // Do whatever you want
            System.out.println("DASHBOARD RESIZE: " + newVal);
            if ((double) newVal <= 615.0) {
                System.out.println("LOWER");
            }
        });
        
        //tSage stage = (Stage) imgSidebar.getScene().getWindow();
        
        //File file = new File("amazon.png");
        //Image im = new Image(file.toURI().toString());
        //Image im = new Image("../../icons/cover.jpg",false);
        
        //ircleImg.setFill(new ImagePattern(im));
        //circleImg.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLACK));
        // TODO
    }    
    
    @FXML
    private BorderPane mainBorderPane;
  
    @FXML
    private void handleShowView(ActionEvent e) {
        String view = (String) ((Node)e.getSource()).getUserData();
        loadFXML(getClass().getResource(view));
    }
    
    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            mainBorderPane.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleBorderPaneWidth(ActionEvent e) {
        
    }
    
}
