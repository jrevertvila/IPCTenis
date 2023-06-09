/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafxmlapplication.JavaFXMLApplication;
import javafxmlapplication.controller.InicioUXController;

/**
 * FXML Controller class
 *
 * @author joan
 */
public class PistaSinConexionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private final LocalTime firstSlotStart = LocalTime.of(9, 0);
    private final Duration slotLength = Duration.ofMinutes(60);
    private final LocalTime lastSlotStart = LocalTime.of(21, 0);

    private Club club;

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    private List<List<TimeSlot>> timeSlots = new ArrayList<>(); //Para varias columnas List<List<TimeSolt>>
    private ObjectProperty<TimeSlot> timeSlotSelected;
    private LocalDate daySelected;

    String contenidoTimeSlot;

    boolean reservaHablilitada = false;

    Member actualUser = JavaFXMLApplication.current_user == null ? null : JavaFXMLApplication.current_user;

    private ObservableList<Court> pistas = null;

    @FXML
    private DatePicker day;
    @FXML
    private GridPane grid;
    @FXML
    private Label labelPista1;
    @FXML
    private Label labelPista2;
    @FXML
    private Label labelPista3;
    @FXML
    private Label labelPista4;
    @FXML
    private Label labelPista5;
    @FXML
    private Label labelPista6;

    private Label slotSelected;
    @FXML
    private Label labelDia;
    @FXML
    public ToggleButton bookingButton;
    @FXML
    private Label disponibilidadReservaLabel;
    @FXML
    private Button diaAnterior;
    @FXML
    private Button diaSiguiente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //---------------------------------------------------------------------
        // Instanciació de la base de dades 
        InicioUXController.pageTitleProperty.set("Disponibilidad de pistas");
        try {
            // TODO
            club = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(PistaSinConexionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PistaSinConexionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        timeSlotSelected = new SimpleObjectProperty<>();

        //Pose el dia que es en la etiqueta Label Dia
        day.valueProperty().addListener((a, b, c) -> {
            setTimeSlotsGrid(c);
            labelDia.setText(c.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
        });

        //---------------------------------------------------------------------
        //inicializa el DatePicker al dia actual
        day.setValue(LocalDate.now());
        //---------------------------------------------------------------------
        // pinta los SlotTime en el grid
        setTimeSlotsGrid(day.getValue());

        //---------------------------------------------------------------------
        // enlazamos timeSlotSelected con el label para mostrar la seleccion
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E MMM d");
        timeSlotSelected.addListener((a, b, c) -> {
            if (c == null) {
                slotSelected.setText("");
            } else {
                slotSelected.setText(c.getDate().format(dayFormatter)
                        + "-"
                        + c.getStart().format(timeFormatter));
            }
        });

        if (JavaFXMLApplication.current_user == null) {
            bookingButton.setStyle("-fx-opacity: 0;");
        }

    }

    

    private void setTimeSlotsGrid(LocalDate date) {
        //actualizamos la seleccion
        disponibilidadReservaLabel.setStyle("disponibilidad-Reserva-Label");
        timeSlotSelected.setValue(null);

        //--------------------------------------------        
        //borramos los SlotTime del grid
        ObservableList<Node> children = grid.getChildren();

        // com es una llista bidimensional ho he de fer anidant els bucles
        for (List<TimeSlot> list : timeSlots) {
            for (TimeSlot timeSlot : list) {
                children.remove(timeSlot.getView());
            }
        }

        timeSlots = new ArrayList<>();

        // generem un array que continga els noms de les pistes 
        String[] pistas = new String[6];
        for (int i = 1; i < 7; i++) {
            pistas[i - 1] = "Pista " + i;
        }

        // tinc una llista en les reserves per al dia indicat en el datepicker
        List<Booking> LabelDayBooking = club.getForDayBookings(day.getValue());

        // ArrayList<Booking> reserva = club.getBookings();
        int arraySize = LabelDayBooking.size();
        ArrayList<Booking> b1;

        List<Booking>[] llistaPerPistes = new List[7];

        for (int i = 0; i < llistaPerPistes.length; i++) {
            llistaPerPistes[i] = new ArrayList<>();
        }
        for (int i = 0; i < LabelDayBooking.size(); i++) {

            String pista = LabelDayBooking.get(i).getCourt().getName();

            if (pista.compareTo("Pista 1") == 0) {

                llistaPerPistes[1].add(LabelDayBooking.get(i));
            }
            if (pista.compareTo("Pista 2") == 0) {

                llistaPerPistes[2].add(LabelDayBooking.get(i));
            }
            if (pista.compareTo("Pista 3") == 0) {

                llistaPerPistes[3].add(LabelDayBooking.get(i));
            }
            if (pista.compareTo("Pista 4") == 0) {

                llistaPerPistes[4].add(LabelDayBooking.get(i));
            }
            if (pista.compareTo("Pista 5") == 0) {

                llistaPerPistes[5].add(LabelDayBooking.get(i));
            }
            if (pista.compareTo("Pista 6") == 0) {

                llistaPerPistes[6].add(LabelDayBooking.get(i));
            }

        }

        //----------------------------------------------------------------------------------
        // desde la hora de inicio y hasta la hora de fin creamos slotTime segun la duracion
        for (int i = 1; i <= 6; i++) {
            int cont = 0;
            int slotIndex = 1;
            List<TimeSlot> timeSlotsPista = new ArrayList<TimeSlot>();
            timeSlots.add(timeSlotsPista);

            // per a totes les hores que te la taula
            for (LocalDateTime startTime = date.atTime(firstSlotStart);
                    !startTime.isAfter(date.atTime(lastSlotStart));
                    startTime = startTime.plus(slotLength)) {

                //---------------------------------------------------------------------------------------
                // creamos el SlotTime, lo anyadimos a la lista de la columna y asignamos sus manejadores
                // faig un cast per a que siga soles la hora 
                LocalTime startTime2 = startTime.toLocalTime();

                // deshabilitar el timeSlot si es abans de l'hora actual
                // primer agafe la reserva , la comprove i si es, la pose en el constructor
                // buscar el booking i afegirlo en el propi constructor del timeSlot
                if (llistaPerPistes[i].size() != 0) {

                    LocalTime reserva = cont >= llistaPerPistes[i].size() ? null : llistaPerPistes[i].get(cont).getFromTime();

                    if (startTime2 == reserva) {
                        // cree el TimeSlot en el nom de usuari
//                        Pane pane = new Pane();
//                        pane.getStyleClass().add("pane");

                        TimeSlot timeSlot = new TimeSlot(startTime, slotLength, llistaPerPistes[i].get(cont).getMember().getNickName(), club.getCourt("Pista " + i));

//                        
                        cont++;
//                        

                        timeSlotsPista.add(timeSlot);
                        registerHandlers(timeSlot);
                        grid.add(timeSlot.getView(), i, slotIndex);
                    } else {

                        TimeSlot timeSlot = new TimeSlot(startTime, slotLength, /*"prova" +i*/ "", club.getCourt("Pista " + i));

                        timeSlotsPista.add(timeSlot);
                        registerHandlers(timeSlot);
                        grid.add(timeSlot.getView(), i, slotIndex);
                    }
                } else {
//                    
                    TimeSlot timeSlot = new TimeSlot(startTime, slotLength, /*"prova" +i*/ "", club.getCourt("Pista " + i));
//                   
                    timeSlotsPista.add(timeSlot);
                    registerHandlers(timeSlot);
                    grid.add(timeSlot.getView(), i, slotIndex);
                }

                slotIndex++;
            }
        }
    }

    private void registerHandlers(TimeSlot timeSlot) {

        timeSlot.getView().setOnMousePressed((MouseEvent event) -> {
            //---------------------------------------------slot----------------------------
            //solamente puede estar seleccionado un slot dentro de la lista de slot

            timeSlots.forEach(list -> list.forEach(slot -> slot.setSelected(slot == timeSlot)));

            //----------------------------------------------------------------
            //actualizamos el label Dia-Hora, esto es ad hoc,  para mi diseño
            timeSlotSelected.setValue(timeSlot);

            // vaiga a fer que no pugues reservar mes de dos hores seguides 
            Court pistaSelected = timeSlotSelected.get().getCourt();


            LocalDate startTimeSelected = timeSlotSelected.get().getDate();

            if (timeSlotSelected.getValue().start.compareTo(LocalDateTime.now()) == -1 && actualUser != null && reservaHablilitada == true) {
                if (event.getClickCount() == 1) {
                    
                    Alert alertaInfor = new Alert(Alert.AlertType.ERROR);
                    alertaInfor.setTitle("No se puede reservar");
                    alertaInfor.setHeaderText("No se pueden reservar pistas pasadas");
                    alertaInfor.setContentText("Has seleccionat: "
                            + timeSlot.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + ", "
                            + timeSlot.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                    Optional<ButtonType> result = alertaInfor.showAndWait();
                }

            }

            if (actualUser != null && reservaHablilitada == true && timeSlotSelected.getValue().contenido.equals("") && timeSlotSelected.getValue().start.compareTo(LocalDateTime.now()) == 1) {

                List<Booking> selectedBookings = club.getCourtBookings(pistaSelected.getName(), startTimeSelected);
                int contAnt = 0;
                int contDesp = 0;
                int horaAbans = 0;
                int horaDesp = 0;

                for (Booking booking : selectedBookings) {
                    if (booking.getMember().getNickName() == actualUser.getNickName()) {

                        if (timeSlotSelected.get().getTime().getHour() == (booking.getFromTime().getHour() - 2) || timeSlotSelected.get().getTime().getHour() == (booking.getFromTime().getHour() - 1)) {
                            contAnt++;

                        }
                        if (timeSlotSelected.get().getTime().getHour() == (booking.getFromTime().getHour() - 1)) {
                            horaAbans = (booking.getFromTime().getHour() - 1);
                        }
                        if (timeSlotSelected.get().getTime().getHour() == (booking.getFromTime().getHour() + 2) || timeSlotSelected.get().getTime().getHour() == (booking.getFromTime().getHour() + 1)) {
                            contDesp++;
                        }
                        if (timeSlotSelected.get().getTime().getHour() == (booking.getFromTime().getHour() + 1)) {
                            horaDesp = (booking.getFromTime().getHour() + 1);
                        }
                    }
                }
              
                if (!(contAnt >= 2 || contDesp >= 2 || (contAnt == 1 && contDesp == 1 && (horaDesp - horaAbans <= 2)))) {
                   

                    if (event.getClickCount() == 1) {

                        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("Confirmación de la reserva");
                        alerta.setHeaderText("Revisa los datos de la reserva ");
                        alerta.setContentText("Has seleccionado: "
                                + timeSlot.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + ", "
                                + timeSlot.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));

                        Optional<ButtonType> result = alerta.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            if (actualUser != null && reservaHablilitada == true) {
                                
                                ObservableList<String> styles = timeSlot.getView().getStyleClass();

                                timeSlot.setContenido(actualUser.getNickName());
                                boolean pagado = true;
                                if (actualUser.getCreditCard() == null) {
                                    pagado = false;
                                }

                                try {
                                    club.registerBooking(timeSlot.getStart(), timeSlot.getDate(), timeSlot.getTime(), pagado, timeSlot.getCourt(), actualUser);
                                } catch (ClubDAOException ex) {
                                    Logger.getLogger(PistaSinConexionController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

                        }

                    }
                } else {
                    if (event.getClickCount() == 1) {
                        
                        Alert alertaInfor = new Alert(Alert.AlertType.ERROR);
                        alertaInfor.setTitle("No se puede reservar");
                        alertaInfor.setHeaderText("No se pueden reservar más de dos horas seguidas");
                        alertaInfor.setContentText("Has seleccionado: "
                                + timeSlot.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + ", "
                                + timeSlot.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                        Optional<ButtonType> result = alertaInfor.showAndWait();
                    }
                }

            }
        });

    }

    @FXML
    private void habilitarReserva(ActionEvent event) {

        if (reservaHablilitada == true) {
            reservaHablilitada = false;
            bookingButton.getStyleClass().remove("button-booking-active");
            disponibilidadReservaLabel.setText("Disponibilidad de pistas para el : ");
            bookingButton.setText("Reservar");
            InicioUXController.pageTitleProperty.set("Disponibilidad de pistas");

        } else {
            InicioUXController.pageTitleProperty.set("Reservando pistas");
            reservaHablilitada = true;
            if (!bookingButton.getStyleClass().contains("button-booking-active")) {
                bookingButton.getStyleClass().add("button-booking-active");
            }
            disponibilidadReservaLabel.setText("Reservando pistas para el : ");
            bookingButton.setText("Dejar de reservar");
        }
        LocalDate fechaActual = day.getValue();

        // Demane disculpes públiques per fer-ho de esta manera, pero no se m'ha ocorregut altra forma
        LocalDate fechaManana = fechaActual.plusDays(1);
        LocalDate fechaAyer = fechaManana.minusDays(1);

        day.setValue(fechaManana);
        day.setValue(fechaAyer);

    }

    @FXML
    private void diaAnterior(ActionEvent event) {

        LocalDate fechaActual = day.getValue();

        // Restar un día a la fecha actual
        LocalDate fechaAyer = fechaActual.minusDays(1);

        day.setValue(fechaAyer);
    }

    @FXML
    private void diaSiguiente(ActionEvent event) {

        LocalDate fechaActual = day.getValue();

        // Restar un día a la fecha actual
        LocalDate fechaManana = fechaActual.plusDays(1);

        day.setValue(fechaManana);
    }

    public class TimeSlot {

        public final LocalDateTime start;
        private final Duration duration;
        protected final Pane view;
        public String contenido;
        protected Court court;

        // contenidoTimeSlot = contenido;
        private final BooleanProperty selected = new SimpleBooleanProperty();

        public final BooleanProperty selectedProperty() {
            return selected;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
            // Actualizar el texto dentro del Pane

            Label label = new Label(contenido);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setMaxHeight(Double.MAX_VALUE);
            label.setAlignment(Pos.CENTER);

            if (this.start.compareTo(LocalDateTime.now()) == -1) {

                label.getStyleClass().add("label-timeslot-blur");
            }
            // coloreja diferent les reserves del propi usuari
            if (JavaFXMLApplication.current_user != null) {
                if (JavaFXMLApplication.current_user.getNickName().equals(contenido)) {
                    label.getStyleClass().add("label-timeslot-user");
                } else {
                    label.getStyleClass().add("label-timeslot");
                }
            } else {
                label.getStyleClass().add("label-timeslot");
            }
            view.getChildren().setAll(label);

        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

//       
        public TimeSlot(LocalDateTime start, Duration duration, String cont, Court pista) {
            this.start = start;
            this.duration = duration;
            view = new Pane();
            if (reservaHablilitada == true) {

                view.getStyleClass().add("time-slot-reserva");

            } else {
                view.getStyleClass().add("time-slot");

            }

            this.contenido = cont;
            this.setContenido(contenido);
            this.court = pista;

            // ---------------------------------------------------------------
            // de esta manera cambiamos la apariencia del TimeSlot cuando los seleccionamos
            selectedProperty().addListener((obs, wasSelected, isSelected)
                    -> view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));

        }

        public Court getCourt() {
            return court;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalTime getTime() {
            return start.toLocalTime();
        }

        public LocalDate getDate() {
            return start.toLocalDate();
        }

        public DayOfWeek getDayOfWeek() {
            return start.getDayOfWeek();
        }

        public Duration getDuration() {
            return duration;
        }

        public Node getView() {
            return view;
        }

    }

}
