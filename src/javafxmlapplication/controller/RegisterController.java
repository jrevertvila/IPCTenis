/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import static javafxmlapplication.JavaFXMLApplication.current_user;
import javafxmlapplication.Utils;
import javafxmlapplication.view.LoginRegisterController;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author joeli
 */
public class RegisterController implements Initializable {

    @FXML
    private Button registerBtn;
    private TextField field_nombre;
    private TextField field_apellidos;
    private TextField field_tlf;
    @FXML
    private TextField field_nickname;
    @FXML
    private TextField field_password;
    private TextField field_repeat_password;
    @FXML
    private TextField field_tarjeta;
    public final FileChooser fileChooser = new FileChooser();
    private File imgFile;
    private Text imageNameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label nicknameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label creditCardLabel;
    @FXML
    private ImageView imagenProfile;
    @FXML
    private Label svcLabel;
    @FXML
    private TextField field_name;
    @FXML
    private TextField field_surname;
    @FXML
    private TextField field_phone;
    @FXML
    private TextField field_svc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenProfile.setImage(new Image(getClass().getResource("../../icons/default-profile.png")+"") );
    }

    @FXML
    private void registerUser(ActionEvent event) throws ClubDAOException, IOException {
        boolean error = false;
        boolean name_error = false;
        boolean surname_error = false;
        boolean phone_error = false;
        boolean creditCard_error= false;
        boolean csv_error = false;
        boolean nickname_error = false;
        boolean password_error = false;
        
        if(!Utils.isOnlyLetters(field_name.getText())){
           error = true;
           nameLabel.setText("Introduce unicamente letras"); 
           field_name.getStyleClass().add("inputStyledError");
           name_error = true;
        }
         if(!Utils.isOnlyLetters(field_surname.getText())){
            error = true;
            surname_error = true;
           surnameLabel.setText("Introduce unicamente letras");
           field_surname.getStyleClass().add("inputStyledError");
        }
          if(!Utils.isPhoneNumber(field_phone.getText())){
            error = true;
            phone_error = true;
           phoneLabel.setText("Introduce únicamente 9 números");
           field_phone.getStyleClass().add("inputStyledError");
        }
        if(!Utils.isCreditCard(field_tarjeta.getText())){
            error = true;
            creditCard_error = true;
           creditCardLabel.setText("Introduce únicamente numeros");
           field_tarjeta.getStyleClass().add("inputStyledError");
           }
           
           if(!Utils.isCSV(field_svc.getText())){
            error = true;
            csv_error = true;
           svcLabel.setText("Introduce únicamente numeros");
           field_svc.getStyleClass().add("inputStyledError");
        }
           if(!Utils.isOnlyLetters(field_nickname.getText())) {
           error = true;
           nickname_error = true;
           nicknameLabel.setText("Introduce únicamente numeros");
           field_nickname.getStyleClass().add("inputStyledError"); 
           }
           if(!Utils.isSecurePassword(field_password.getText())) {
           error = true;
           password_error = true;
           passwordLabel.setText("Introduce únicamente numeros");
           field_password.getStyleClass().add("inputStyledError"); 
           }
           
        String nombre = field_name.getText(); 
        String apellidos = field_surname.getText(); 
        String tlf = field_phone.getText(); 
        String nickname = field_nickname.getText();
        String passwd = field_password.getText(); 
        String tarjeta = field_tarjeta.getText(); 
        int svc = Integer.parseInt(field_svc.getText());       
        if (!error) {
            Club club = Club.getInstance();
            
            Member result = club.registerMember(nombre, apellidos, tlf, nickname, passwd, tarjeta, svc ,
                imgFile == null ? 
                        new Image(getClass().getResource("../../icons/default-profile.png")+"") 
                        : new Image(imgFile.getAbsolutePath()) 
            );
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MessageModal.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            MessageModalController controladorMessageModal = loader.getController();
            controladorMessageModal.setTextMessage("Se ha registrado correctamente. \n Inicie sesión por favor.");
            stage.setScene(scene);
            stage.setTitle("Registro correcto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
//            LoginRegisterController.loadFXML_LR(getClass().getResource("../view/Login.fxml"),"../view/Login.fxml");
//            LoginRegisterController.iniSesionBtnNav.getStyleClass().add("activeLR");
//            LoginRegisterController.registerBtnNav.getStyleClass().remove("activeLR");
//            Node frame = JavaFXMLApplication.setFrame(frameName, new FXMLLoader(url).load());
//            
//            mainWrapper.getChildren().clear();
//            mainWrapper.getChildren().add(frame);

//            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../view/InicioRefactor.fxml"));
//            String[] framesList = ["",""];
//            JavaFXMLApplication.removeFrames(new String[]{"LoginRegister.fxml","Login.fxml","Register.fxml"});
//            Parent root2 = loader2.load();
//            javafxmlapplication.JavaFXMLApplication.setRoot(root2);

//            LoginRegisterController.registerBtnNav.fire();            
        }

    }

    private void uploadImage(ActionEvent event) {
        imgFile = Utils.uploadImage(event);
        if (imgFile != null) imageNameLabel.setText(imgFile.getName());
    }
    
    

}
