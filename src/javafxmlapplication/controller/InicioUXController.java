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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafxmlapplication.JavaFXMLApplication;
import javafxmlapplication.controller.LoginController;
import model.Club;
import model.ClubDAOException;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class InicioUXController implements Initializable {

    private Button pistasBtn;
    private Button aboutUsBtn;
    private Button contactUsBtn;
    @FXML
    private VBox mainWrapper;
    @FXML
    private Button pistasButton;
    @FXML
    private Button perfilButton;
    @FXML
    private Button aboutButton;
    @FXML
    private VBox mainWrapper_parent;
    @FXML
    private Circle topBar_userImg_container;
    @FXML
    private Text pageTitle;
    @FXML
    private TextField topBar_searchBar;
    @FXML
    private Button topBar_notification;
    @FXML
    private ImageView topBar_notificationButton;
    @FXML
    private Button buttonLogout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        System.out.println("Initialize currentUser: "+JavaFXMLApplication.current_user);
//        loginBtnNav.setText(JavaFXMLApplication.current_user == null ? "Iniciar Sesion" : "Mi Perfil");
//        if (JavaFXMLApplication.current_user != null) {
//            loadFXML_NAV(getClass().getResource("PistaSinConexion.fxml"),"PistaSinConexion.fxml");
//            loginBtnNav.setUserData("Profile.fxml");
//        }
        
        if (JavaFXMLApplication.current_user != null) {
            System.out.println("entraaaasdsadsada");
            ImagePattern pattern = new ImagePattern(
                new Image(getClass().getResource("../../icons/this-person-does-not-exist.jpeg")+"", 40, 40, false, false) // Resizing
            );
            System.out.println("Pattern: " + pattern);
            topBar_userImg_container.setFill(pattern);
//            topBar_userImg_container.setFill(Color.RED);
            
            
        }

        
//        topBar_userImg_container.setEffect(new DropShadow(20, Color.BLACK));  // Shadow
    }

    
    private void loadFXML_NAV(URL url, String frameName) {
        try {
            
            Node frame = JavaFXMLApplication.getFrame(frameName);
//            System.out.println("url: "+url);
//            System.out.println("frameName: "+frameName);
//            System.out.println("frame: "+frame);
            if (frame == null) frame = JavaFXMLApplication.setFrame(frameName, new FXMLLoader(url).load());

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
//        System.out.println("view: " + view);
//        System.out.println("URL: " + getClass().getResource("../view/"+view));
        if (view.equals("ProfileUX.fxml")) {
            mainWrapper.getStyleClass().clear();
        } else {
            if (!mainWrapper.getStyleClass().contains("mainWrapper_parent")) mainWrapper.getStyleClass().add("mainWrapper_parent");
        }
        loadFXML_NAV(getClass().getResource("../view/" + view), view);
        Object o = event.getSource();
        Button b = (Button) o;
        if (b.getId().equals("loginTopBarButton")) {
            VBox.setVgrow(mainWrapper, Priority.NEVER);
            mainWrapper_parent.setFillWidth(false);
        } else {
            VBox.setVgrow(mainWrapper, Priority.ALWAYS);
            mainWrapper_parent.setFillWidth(true);
        }
        switch (b.getId()) {
            case "pistasButton":
                pageTitle.setText("Pistas");
                if (!pistasButton.getStyleClass().contains("buttonSidebarActive")) pistasButton.getStyleClass().add("buttonSidebarActive");
                aboutButton.getStyleClass().remove("buttonSidebarActive");
                
                if (JavaFXMLApplication.current_user != null) perfilButton.getStyleClass().remove("buttonSidebarActive");
                break;
            case "aboutButton":
                pageTitle.setText("Sobre Nosotros");
                if (!aboutButton.getStyleClass().contains("buttonSidebarActive")) aboutButton.getStyleClass().add("buttonSidebarActive");
                pistasButton.getStyleClass().remove("buttonSidebarActive");
                if (JavaFXMLApplication.current_user != null) perfilButton.getStyleClass().remove("buttonSidebarActive");
                break;
//            case "contactUsBtn":
//                contactUsBtn.getStyleClass().add("buttonSidebarActive");
//                pistasBtn.getStyleClass().remove("buttonSidebarActive");
//                aboutUsBtn.getStyleClass().remove("buttonSidebarActive");
//                break;
            case "perfilButton":
                pageTitle.setText("Mi Perfil");
//                contactUsBtn.getStyleClass().add("buttonSidebarActive");
                if (JavaFXMLApplication.current_user != null && !perfilButton.getStyleClass().contains("buttonSidebarActive")) perfilButton.getStyleClass().add("buttonSidebarActive");
                pistasButton.getStyleClass().remove("buttonSidebarActive");
                aboutButton.getStyleClass().remove("buttonSidebarActive");
                
                break;
            case "loginTopBarButton":
                pageTitle.setText("Formulario de autentiación");
//                contactUsBtn.getStyleClass().add("buttonSidebarActive");
                if (JavaFXMLApplication.current_user != null) perfilButton.getStyleClass().remove("buttonSidebarActive");
                pistasButton.getStyleClass().remove("buttonSidebarActive");
                aboutButton.getStyleClass().remove("buttonSidebarActive");
                break;
            default:
                break;
        }
    
        
    }

    @FXML
    private void showNotifications(ActionEvent event) throws ClubDAOException, IOException {
        
        Club c = Club.getInstance();
        System.out.println("User bookings: "+c.getUserBookings(JavaFXMLApplication.current_user.getNickName()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tus notificaciones");
        alert.setHeaderText("Tienes 3 notificaciones:");
        alert.setContentText("Partido hoy a las 16:00\nPartido hoy a las 20:00\nPartido mañana a las 18:00");
        alert.showAndWait();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        TrayNotification notif = new TrayNotification();
        notif.setAnimationType(AnimationType.POPUP);
        notif.setTitle("HASTA PRONTO!");
        notif.setMessage("Usted ha cerrado la sesión");
        notif.setNotificationType(NotificationType.SUCCESS);
        notif.showAndDismiss(Duration.millis(3000));
        
        JavaFXMLApplication.current_user = null;
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/InicioUX.fxml"));
        JavaFXMLApplication.removeFrames(new String[]{"Profile.fxml", "InicioUX_Logged.fxml"});
        Parent root2 = loader2.load();
        javafxmlapplication.JavaFXMLApplication.setRoot(root2);
    }
    
}
