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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import javafxmlapplication.JavaFXMLApplication;
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

    private int reservasPendientes;
    private int reservasTotales;
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

    private ObservableList<Booking> misReservas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

//        numBookingCOL.setCellValueFactory(new PropertyValueFactory<Booking, String>("nombre"));
//        private ObservableList<Persona> misPersonas;
//        personaTableV.setItems(misPersonas);
        Club club;
        try {
            club = Club.getInstance();
            List<Booking> userBookings = club.getUserBookings(JavaFXMLApplication.current_user.getNickName());

            misReservas = FXCollections.observableArrayList(club.getUserBookings(JavaFXMLApplication.current_user.getNickName()));

            dateBookingCOL.setCellValueFactory((booking) -> {
                return new SimpleStringProperty(booking.getValue().getBookingDate().toString());
            });

            courtBookingCOL.setCellValueFactory((booking) -> {
                return new SimpleStringProperty(booking.getValue().getCourt().getName());
            });

            estadoBookingCOL.setCellValueFactory((booking) -> {
                return new SimpleStringProperty(booking.getValue().getBookingDate().isAfter(LocalDateTime.now()) ? "PENDIENTE" : "COMPLETADA");
            });

            actionBookingCOL.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

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

//            actionBookingCOL.setCellValueFactory((booking) -> {
//                Button b = new Button("button");
//                return b;
//            });
            tableViewReservas.setItems(misReservas);

            reservasTotales = userBookings.size();
            reservasTotalesLabel.setText(reservasTotales + "");
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
            reservasPendientesLabel.setText(reservasPendientes + "");
            System.out.println("USER BOOKINGS: " + club.getUserBookings(JavaFXMLApplication.current_user.getNickName()));

        } catch (ClubDAOException ex) {
            Logger.getLogger(ProfileUXController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProfileUXController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
