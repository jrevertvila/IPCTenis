/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import model.Booking;
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
    public Text pageTitle;
    @FXML
    private Button loginTopBarButton;
    
    public static StringProperty pageTitleProperty = new SimpleStringProperty("Disponibilidad de pistas");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pageTitle.textProperty().bind(pageTitleProperty);
        if (JavaFXMLApplication.current_user != null) {
            ImagePattern pattern = new ImagePattern( JavaFXMLApplication.current_user.getImage() );
            topBar_userImg_container.setFill(pattern);
        }
    }

    
    private void loadFXML_NAV(URL url, String frameName) throws ClubDAOException {
        try {

            Node frame = JavaFXMLApplication.setFrame(frameName, new FXMLLoader(url).load());
            
            if (frameName.equals("ProfileUX.fxml")) frame = JavaFXMLApplication.setFrame(frameName, new FXMLLoader(url).load());
            frame.setVisible(true);
            mainWrapper.getChildren().clear();
            mainWrapper.getChildren().add(frame);
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleNavBtn(ActionEvent event) throws ClubDAOException {
        
        String view = (String) ((Node)event.getSource()).getUserData();
        if (view.equals("ProfileUX.fxml")) {
            mainWrapper.getStyleClass().clear();
        } else {
            if (!mainWrapper.getStyleClass().contains("mainWrapper_parent")) mainWrapper.getStyleClass().add("mainWrapper_parent");
        }
        loadFXML_NAV(getClass().getResource("../view/" + view), view);
        Object o = event.getSource();
        Button b = (Button) o;
        if (b.getId().equals("loginTopBarButton")) {
            loginTopBarButton.setVisible(false);
            VBox.setVgrow(mainWrapper, Priority.NEVER);
            mainWrapper_parent.setFillWidth(false);
        } else {
            if (JavaFXMLApplication.current_user == null) loginTopBarButton.setVisible(true);
            VBox.setVgrow(mainWrapper, Priority.ALWAYS);
            mainWrapper_parent.setFillWidth(true);
        }
        switch (b.getId()) {
            case "pistasButton":
                pageTitleProperty.set("Disponibilidad de pistas");
                if (!pistasButton.getStyleClass().contains("buttonSidebarActive")) pistasButton.getStyleClass().add("buttonSidebarActive");
                aboutButton.getStyleClass().remove("buttonSidebarActive");
                
                if (JavaFXMLApplication.current_user != null) perfilButton.getStyleClass().remove("buttonSidebarActive");
                break;
            case "aboutButton":
                pageTitleProperty.set("Sobre Nosotros");

                if (!aboutButton.getStyleClass().contains("buttonSidebarActive")) aboutButton.getStyleClass().add("buttonSidebarActive");
                pistasButton.getStyleClass().remove("buttonSidebarActive");
                if (JavaFXMLApplication.current_user != null) perfilButton.getStyleClass().remove("buttonSidebarActive");
                break;
            case "perfilButton":
                pageTitleProperty.set("Mi Perfil");
                if (JavaFXMLApplication.current_user != null && !perfilButton.getStyleClass().contains("buttonSidebarActive")) perfilButton.getStyleClass().add("buttonSidebarActive");
                pistasButton.getStyleClass().remove("buttonSidebarActive");
                aboutButton.getStyleClass().remove("buttonSidebarActive");
                
                break;
            case "loginTopBarButton":
                pageTitleProperty.set("Formulario de autenticación");
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
        int notifCont = 0;
        Club club = Club.getInstance();
        List<Booking> userBookings = club.getUserBookings(JavaFXMLApplication.current_user.getNickName());
        String res = "";
        
        for (int i = 0; i < userBookings.size(); i++) {
            Booking booking = userBookings.get(i);
            
            if (booking.getBookingDate().compareTo(LocalDateTime.now()) > 0 && booking.getBookingDate().compareTo(LocalDateTime.now().plusDays(1)) < 0) {
                notifCont++;
                res += "Partido " + (booking.getBookingDate().getDayOfYear() == LocalDateTime.now().getDayOfYear()? "hoy " : "mañana ") + 
                        "a las " + booking.getBookingDate().getHour() + ":" + booking.getBookingDate().getMinute() + "" + (booking.getBookingDate().getMinute() < 10 ? "0" : "") + "\n";
            }

        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tus notificaciones");
        alert.setHeaderText("Tienes "+ notifCont +" notificaciones:");
        alert.setContentText(res);
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
        JavaFXMLApplication.removeFrames(new String[]{"Profile.fxml", "InicioUX_Logged.fxml", "PistaSinConexion.fxml"});
        Parent root2 = loader2.load();
        javafxmlapplication.JavaFXMLApplication.setRoot(root2);
    }
    
}
