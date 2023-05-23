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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafxmlapplication.JavaFXMLApplication;

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

    //Member actualUser = JavaFXMLApplication.current_user;
    String actualUser = "joan25";

    //pistaColArr = [labelPista1];
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //---------------------------------------------------------------------
        // Instanciació de la base de dades 
        try {
            // TODO
            club = Club.getInstance();

            //club.addSimpleData();
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

    }

    private void test(ActionEvent event) {
        System.out.println(club.getName());
        System.out.println(club.getCourts());
        System.out.println(club.getBookings());

        System.out.println(club.getMembers());

    }

    private void setTimeSlotsGrid(LocalDate date) {
        //actualizamos la seleccion
        timeSlotSelected.setValue(null);

        //--------------------------------------------        
        //borramos los SlotTime del grid
        // List <TimeSlot> timeSlot;
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

        System.out.println("reserves per al dia " + day.getValue());

        System.out.println("numero de reserves = " + arraySize);

        List<Booking>[] llistaPerPistes = new List[7];

        for (int i = 0; i < llistaPerPistes.length; i++) {
            llistaPerPistes[i] = new ArrayList<>();
        }

        for (int i = 1; i < LabelDayBooking.size(); i++) {

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
        System.out.println("abans del for");

        for (int i = 0; i < llistaPerPistes[1].size(); i++) {
            // Court pista2 = LabelDayBooking.get(i).getCourt();
            //String pista2Name = pista2.getName();
            // System.out.println(pista2Name);
            Member miembros = llistaPerPistes[1].get(i).getMember();
            System.out.println("Reserva:" + miembros.getNickName());
            LocalTime horaReserva = llistaPerPistes[1].get(i).getFromTime();
            String pista = llistaPerPistes[1].get(i).getCourt().getName();
            System.out.println(pista);
            System.out.println(horaReserva);
            System.out.println(LabelDayBooking.size());
            //System.out.println(llistaPerPistes[0].size());            
            System.out.println(llistaPerPistes[1].size());
            System.out.println(llistaPerPistes[2].size());
            System.out.println(llistaPerPistes[3].size());
            System.out.println(llistaPerPistes[4].size());
            System.out.println(llistaPerPistes[5].size());
            System.out.println(llistaPerPistes[6].size());

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
                // LabelDayBooking.get(cont)
                // primer agafe la reserva , la comprove i si es, la pose en el constructor
                // buscar el booking i afegirlo en el propi constructor del timeSlot

                LocalTime reserva = llistaPerPistes[i].get(cont).getFromTime();

                if (startTime2 == reserva) {
                    // cree el TimeSlot en el nom de usuari
                    TimeSlot timeSlot = new TimeSlot(startTime, slotLength, llistaPerPistes[i].get(cont).getMember().getNickName(), llistaPerPistes[i].get(cont).getCourt());

                    if (cont < llistaPerPistes[i].size() - 1) {
                        cont++;
                    }

                    timeSlotsPista.add(timeSlot);
                    registerHandlers(timeSlot);
                    grid.add(timeSlot.getView(), i, slotIndex);
                } else {
                    // cree el timeSlot sense el nom de usuari
                    TimeSlot timeSlot = new TimeSlot(startTime, slotLength, /*"prova" +i*/ " - ", llistaPerPistes[i].get(cont).getCourt());

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
            //----------------------------------------------------------------
            // si es un doubleClik  vamos a mostrar una alerta y cambiar el estilo de la celda
            if (event.getClickCount() > 1) {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Confirmació de la reserva");
                alerta.setHeaderText("Revisa els datos de la reserva ");
                alerta.setContentText("Has seleccionat: "
                        + timeSlot.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + ", "
                        + timeSlot.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));

                Optional<ButtonType> result = alerta.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (actualUser != null) {

                        ObservableList<String> styles = timeSlot.getView().getStyleClass();
                        // si ja està marcada 
                        if (styles.contains("time-slot")) {
                            //                            try {
//                                club.registerBooking(timeSlot.getStart(), timeSlot.getDate(), timeSlot.getTime(),true, timeSlot.getCourt() , actualUser);
//                            } catch (ClubDAOException ex) {
//                                Logger.getLogger(PistaSinConexionController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                            styles.remove("time-slot");
                            styles.add("time-slot-libre");
                        } else {

                            timeSlot.setContenido(actualUser);
//                            timeSlot.setContenido(actualUser.getNickName());
                            styles.remove("time-slot-libre");
                            styles.add("time-slot");
                        }

                    }

                }
            }
        });

    }

    public class TimeSlot {

        private final LocalDateTime start;
        private final Duration duration;
        protected final Pane view;
        protected String contenido;
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
            view.getChildren().setAll(label);
        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

        public TimeSlot(LocalDateTime start, Duration duration, String cont) {
            this.start = start;
            this.duration = duration;
            view = new Pane();
            view.getStyleClass().add("time-slot");
            this.contenido = cont;
            this.setContenido(contenido);
            //this.court = pista;

            // ---------------------------------------------------------------
            // de esta manera cambiamos la apariencia del TimeSlot cuando los seleccionamos
            selectedProperty().addListener((obs, wasSelected, isSelected)
                    -> view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));

        }

        public TimeSlot(LocalDateTime start, Duration duration, String cont, Court pista) {
            this.start = start;
            this.duration = duration;
            view = new Pane();
            view.getStyleClass().add("time-slot");
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
