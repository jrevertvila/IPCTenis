/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafxmlapplication.JavaFXMLApplication;
import static javafxmlapplication.JavaFXMLApplication.current_user;
import javafxmlapplication.Utils;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ProfileUXController implements Initializable {

    @FXML
    private Text nameLabel;
    @FXML
    private Button changePasswordButton;
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text reservasPendientesLabel;
    @FXML
    private Text reservasTotalesLabel;

    private static int reservasPendientes;
    private static int reservasTotales;
    @FXML
    private TableView<Booking> tableViewReservas;
    @FXML
    private TableColumn<Booking, String> courtBookingCOL;
    @FXML
    private TableColumn<Booking, String> estadoBookingCOL;
    @FXML
    private TableColumn<Booking, String> actionBookingCOL;
    @FXML
    private TableColumn<Booking, String> dateBookingCOL;

    private static ObservableList<Booking> misReservas;
    private static SimpleStringProperty pendientesP = new SimpleStringProperty("0");
    private static SimpleStringProperty totalesP = new SimpleStringProperty("0");
    @FXML
    private TableColumn<Booking, String> isPaidBookingCOL;
    @FXML
    private TableColumn<Booking, String> pagarBookingCOL;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField creditCardField;
    @FXML
    private Button applyChangesButton;
    @FXML
    private Label nameError;
    @FXML
    private Label surnameError;
    @FXML
    private Label phoneError;
    @FXML
    private Label creditCardError;
    @FXML
    private TextField csvField;
    @FXML
    private Label csvError;
    @FXML
    private ImageView imageView;
    
    public ProfileUXController() {
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
      
        
        
//        reservasPendientesLabel.textProperty().bind(pendientesP);
//        reservasTotalesLabel.textProperty().bind(totalesP);
//        numBookingCOL.setCellValueFactory(new PropertyValueFactory<Booking, String>("nombre"));
//        private ObservableList<Persona> misPersonas;
//        personaTableV.setItems(misPersonas);
        Club club;
        try {
            club = Club.getInstance();
            List<Booking> userBookings = club.getUserBookings(JavaFXMLApplication.current_user.getNickName());
            System.out.println("RESERVAS TOTALESSSSSSSSSSS: " + userBookings.size());
            misReservas = FXCollections.observableArrayList(club.getUserBookings(JavaFXMLApplication.current_user.getNickName()));
             nameField.setText(current_user.getName());
             surnameField.setText(current_user.getSurname());
             phoneField.setText(current_user.getTelephone());
             creditCardField.setText(current_user.getCreditCard());
             csvField.setText(current_user.getSvc() + "");
             
            nameLabel.setText(current_user.getName() + " " + current_user.getSurname());  
            userField.setText(current_user.getNickName());
            passwordField.setText(current_user.getPassword());
            imageView.setImage(current_user.getImage());
             

            dateBookingCOL.setCellValueFactory((booking) -> {
                return new SimpleStringProperty(booking.getValue().getBookingDate().toString());
            });

            courtBookingCOL.setCellValueFactory((booking) -> {
                return new SimpleStringProperty(booking.getValue().getCourt().getName());
            });

            estadoBookingCOL.setCellValueFactory((booking) -> {
                return new SimpleStringProperty(booking.getValue().getBookingDate().isAfter(LocalDateTime.now()) ? "PENDIENTE" : "COMPLETADA");
            });
            
            isPaidBookingCOL.setCellValueFactory((booking) -> {
                return new SimpleStringProperty(booking.getValue().getPaid() ? "SI" : "NO");
            });

            actionBookingCOL.setCellValueFactory(new PropertyValueFactory<>(""));
            pagarBookingCOL.setCellValueFactory(new PropertyValueFactory<>(""));
            
            
            //CELLFACTORY PARA ACTION_COL, ES DECIR, COLUMNA DE ANULAR RESERVA
            Callback<TableColumn<Booking, String>, TableCell<Booking, String>> cellFactory
                    = //
                    (final TableColumn<Booking, String> param) -> {
                        final TableCell<Booking, String> cell = new TableCell<Booking, String>() {

                    final Button btn = new Button("Anular");
//                    getTableView().getItems().get(getIndex())
                    
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setDisable(getTableView().getItems().get(getIndex()).getBookingDate().isAfter(LocalDateTime.now()) ? false : true);
                            btn.setOnAction(event -> {
                                try {
                                    club.removeBooking(getTableView().getItems().get(getIndex()));
                                    reservasPendientes--;reservasTotales--;
                                    reservasPendientesLabel.setText(""+reservasPendientes--);
                                    reservasTotalesLabel.setText(""+reservasTotales);
                                    TrayNotification notif = new TrayNotification();
                                    notif.setAnimationType(AnimationType.POPUP);
                                    notif.setTitle("RESERVA ANULADA");
                                    notif.setMessage("Reserva para el dia " + getTableView().getItems().get(getIndex()).getBookingDate() + " anulada correctamente");
                                    notif.setNotificationType(NotificationType.SUCCESS);
                                    notif.showAndDismiss(Duration.millis(3000));
                                    misReservas.remove(getIndex());
                                } catch (ClubDAOException ex) {
                                    Logger.getLogger(ProfileUXController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            setGraphic(btn);

                            setText(null);
                        }
                    }
                };
                        return cell;
                    };

            actionBookingCOL.setCellFactory(cellFactory);
            
            //CELLFACTORY PARA COLUMNA PAGAR
            Callback<TableColumn<Booking, String>, TableCell<Booking, String>> cellFactory2
                    = //
                    (final TableColumn<Booking, String> param) -> {
                        final TableCell<Booking, String> cell = new TableCell<Booking, String>() {

                    final Button btn = new Button("Pagar");
//                    getTableView().getItems().get(getIndex())
                    
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setDisable(getTableView().getItems().get(getIndex()).getPaid() ? true : false);
                            btn.setOnAction(event -> {
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confimarción pago reserva:");
                            alert.setHeaderText("La reserva para el dia " + getTableView().getItems().get(getIndex()).getBookingDate() + " se pagará con la tarjeta de credito proporcionada");
                            alert.setContentText("¿Seguro que quieres continuar?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK){
                                
                                getTableView().getItems().get(getIndex()).setPaid(true);
                                misReservas = FXCollections.observableArrayList(club.getUserBookings(JavaFXMLApplication.current_user.getNickName()));
                                tableViewReservas.setItems(misReservas);
                                btn.setDisable(true);
                                
                                TrayNotification notif = new TrayNotification();
                                notif.setAnimationType(AnimationType.POPUP);
                                notif.setTitle("RESERVA PAGADA");
                                notif.setMessage("Reserva para el dia " + getTableView().getItems().get(getIndex()).getBookingDate() + " pagada correctamente");
                                notif.setNotificationType(NotificationType.SUCCESS);
                                notif.showAndDismiss(Duration.millis(3000));
                            }
                            });
                            setGraphic(btn);

                            setText(null);
                        }
                    }
                };
                        return cell;
                    };

            pagarBookingCOL.setCellFactory(cellFactory2);

//            actionBookingCOL.setCellValueFactory((booking) -> {
//                Button b = new Button("button");
//                return b;
//            });
            tableViewReservas.setItems(misReservas);

            reservasTotales = userBookings.size();
//            totalesP.setValue(reservasTotales + "");
            reservasTotalesLabel.setText(reservasTotales+"");
            reservasPendientes = 0;
            for (int i = 0; i < userBookings.size(); i++) {
                Booking booking = userBookings.get(i);
                System.out.println("GetBookingDate: " + booking.getBookingDate());
                System.out.println("CurrentDate.now: " + LocalDateTime.now());
                System.out.println("IS AFTER: " + booking.getBookingDate().isAfter(LocalDateTime.now()));
                if (booking.getBookingDate().compareTo(LocalDateTime.now()) > 0) {
                    reservasPendientes++;
                }
                System.out.println("Booking " + i + ": " + booking.getBookingDate());

            }
            reservasPendientesLabel.setText(reservasPendientes+"");
//            pendientesP.setValue(reservasPendientes + "");
            System.out.println("USER BOOKINGS: " + club.getUserBookings(JavaFXMLApplication.current_user.getNickName()));
            
            
             

        } catch (ClubDAOException ex) {
            Logger.getLogger(ProfileUXController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProfileUXController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void setLabels(String totales, String pendientes){
        reservasPendientesLabel.setText(pendientes);
        reservasTotalesLabel.setText(totales);
    }
    
    public static void refresh() throws ClubDAOException, IOException {
        
        Club club = Club.getInstance();
        List<Booking> userBookings = club.getUserBookings(JavaFXMLApplication.current_user.getNickName());
        
        if (misReservas.size() !=  userBookings.size()) {
            userBookings.clear();
            userBookings = club.getUserBookings(JavaFXMLApplication.current_user.getNickName());
            int reservasTotales = userBookings.size();
            
            for (int i = 0; i < userBookings.size(); i++) {
                Booking booking = userBookings.get(i);
                System.out.println("GetBookingDate: " + booking.getBookingDate());
                System.out.println("CurrentDate.now: " + LocalDateTime.now());
                System.out.println("IS AFTER: " + booking.getBookingDate().isAfter(LocalDateTime.now()));
                if (booking.getBookingDate().compareTo(LocalDateTime.now()) > 0) {
                    reservasPendientes++;
                }
                System.out.println("Booking " + i + ": " + booking.getBookingDate());

            }
            pendientesP.setValue(reservasPendientes + "");
            totalesP.setValue(reservasTotales + "");
//            reservasTotalesLabel.setText(reservasTotales + "");
//            reservasPendientesLabel.setText(reservasPendientes + "");
        }
    }

    @FXML
    private void changePassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ChangePassword.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        ChangePasswordController controladorChangePassword = loader.getController();
       
        stage.setScene(scene);
        stage.setTitle("Cambiar Contraseña");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void applyChanges(MouseEvent event) {
        boolean error = false;
        boolean name_error = false;
        boolean surname_error = false;
        boolean phone_error = false;
        boolean creditCard_error= false;
        boolean csv_error = false;
        
        if(!Utils.isOnlyLetters(nameField.getText())){
           error = true;
           nameError.setText("Introduce unicamente letras"); 
           nameField.getStyleClass().add("inputStyledError");
           name_error = true;
        }
         if(!Utils.isOnlyLetters(surnameField.getText())){
            error = true;
            surname_error = true;
           surnameError.setText("Introduce unicamente letras");
           surnameField.getStyleClass().add("inputStyledError");
        }
          if(!Utils.isPhoneNumber(phoneField.getText())){
            error = true;
            phone_error = true;
           phoneError.setText("Introduce únicamente 9 números");
           phoneField.getStyleClass().add("inputStyledError");
        }
        if(!Utils.isCreditCard(creditCardField.getText())){
            error = true;
            creditCard_error = true;
           creditCardError.setText("Introduce únicamente numeros");
           creditCardField.getStyleClass().add("inputStyledError");
           }
           
           if(!Utils.isCSV(csvField.getText())){
            error = true;
            csv_error = true;
           csvError.setText("Introduce únicamente numeros");
           csvField.getStyleClass().add("inputStyledError");
        }
           
        if (!error) {
            current_user.setName(nameField.getText());
            current_user.setSurname(surnameField.getText());
            current_user.setTelephone(phoneField.getText());
            current_user.setCreditCard(creditCardField.getText());
            current_user.setSvc(Integer.parseInt(csvField.getText()));
        }    
        if (!name_error) {
                nameField.getStyleClass().remove("inputStyledError");
                nameError.setText("");
        }
        if (!surname_error) {
                surnameField.getStyleClass().remove("inputStyledError");
                surnameError.setText("");
        }
        if (!phone_error) {
                phoneField.getStyleClass().remove("inputStyledError");
                phoneError.setText("");
        } 
        if (!creditCard_error) {
                 creditCardField.getStyleClass().remove("inputStyledError");
                 creditCardError.setText("");
        }
        if(!csv_error) {
            csvField.getStyleClass().remove("inputStyledError");
            csvError.setText("");
        }
                   
    }
    
}
