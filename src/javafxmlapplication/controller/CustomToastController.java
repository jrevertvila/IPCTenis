/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class CustomToastController implements Initializable {
    
    public static final int TOAST_SUCCESS = 11;
    public static final int TOAST_WARN = 12;
    public static final int TOAST_ERROR = 13;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void setToast(int toastType, String content){
//        textToast.setText(content);
//        switch (toastType){
//            case TOAST_SUCCESS:
//                containerToast.setStyle("-fx-background-color: #9FFF96");
//                break;
//            case TOAST_WARN:
//                containerToast.setStyle("-fx-background-color: #FFCF82");
//                break;
//            case TOAST_ERROR:
//                containerToast.setStyle("-fx-background-color: #FF777C");
//                break;
//        }
    }

    public static void showToast(int toastTyoe, Control control, String text){
        Stage dialog = new Stage();
        dialog.initOwner(control.getScene().getWindow());
//        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.UNDECORATED);

        double dialogX = dialog.getOwner().getX();
        double dialogY = dialog.getOwner().getY();
        double dialogW = dialog.getOwner().getWidth();
        double dialogH = dialog.getOwner().getHeight();

        double posX = dialogW;
        double posY = dialogY + dialogH/6 *5;
        dialog.setX(posX);
        dialog.setY(posY);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ToastController.class.getResource("../view/Toast.fxml"));
            loader.load();
            ToastController ce = loader.getController();
//            ce.setToast(toastTyoe,text);
            dialog.setScene(new Scene(loader.getRoot()));
            dialog.show();
            new Timeline(new KeyFrame(
                    Duration.millis(3000),
                    ae -> {
                        dialog.close();
                    })).play();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}