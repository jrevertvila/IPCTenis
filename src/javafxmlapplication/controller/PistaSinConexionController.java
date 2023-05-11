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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
    private final LocalTime lastSlotStart = LocalTime.of(22, 0);
    
    private Club club;
    
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
    
    private List<List<TimeSlot>> timeSlots = new ArrayList<>(); //Para varias columnas List<List<TimeSolt>>
    private ObjectProperty<TimeSlot> timeSlotSelected;
    private LocalDate daySelected;
    
    
    
    
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
    @FXML
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

        
        //ArrayList<Member> membres = club.getMembers();
        
        /*
        List<Member> m = club.getMembers();
        int listSize = m.size();
       //String ma = club.getCourt(pista1);
        
        miembros = FXCollections.observableArrayList(m);
       System.out.println(ma);
      
         
        for(int i = 0; i< listSize; i++){
            System.out.println("membre: "+m.get(i).getName() + " ");
             System.out.println(""+m.get(i).getSurname() + " \n");
             System.out.println(""+m.get(i).getPassword() + " \n");
        }
*/
        Court pista = club.getCourt("pista 1");
//        String pista1 = pista.getName();
        
        
        
        //System.out.println(club.getBookings());
        
        // obtenim i imprimim certes dades de les reserves
        ArrayList<Booking> b = club.getBookings();
        int arraySize = b.size();
        ArrayList<Booking> b1 ;
        
        // nota a futuro : puc en un sol bucle recorrer el arraylist de b , demanar la seua pista i en un if o switch 
        // anar colocantla en la tableview que corresponga.        
        
        for(int i=0; i<arraySize; i++){
            Court pista2 = b.get(i).getCourt();
            String pista2Name = pista2.getName();
           // System.out.println(pista2Name);
            
            
            if (pista2Name.equals("Pista 2") ) {
            
                
                System.out.println("Reserva: " + i +" \n En pista: "+ pista2Name );

                Member miembros = b.get(i).getMember();
                System.out.println("Reserva:" + miembros.getName() );
            }
        
        }
        System.out.println("numero de reserves = " + arraySize);
        
        // obtenim e imprimim imprimim les pistes que hi han
         /*
         List<Court> m = club.getCourts();
         int listSize = m.size();
        
        for(int i = 0; i< listSize; i++){
           
            System.out.println("pista: "+m.get(i).getName() + " \n");
             //System.out.println(""+m.get(i).getSurname() + " \n");
            // System.out.println(""+m.get(i).getPassword() + " \n");
        }
    */
       // pistas = FXCollections.observableArrayList(m);
        
       // Column1 = new TableColumn<>("Nombre primero");
        
        //Column1.setText(m.get(3).getName());
        
        //TableView.getColumns().addAll(Column1,Column2);
            // TableView.setItems(miembros);
             //Column1.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
            // Column2.setCellValueFactory(new PropertyValueFactory<Member, String>("surname"));

        
        
        //Column1.setCellFactory((Callback<TableColumn<Member, String>, TableCell<Member, String>>) miembros);
        
       // TableView.getColumns().addAll(Column1,Column2);
        //ListView.setItems(miembros);
        
      
        
        
        
        
               
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

        //----------------------------------------------------------------------------------
        // desde la hora de inicio y hasta la hora de fin creamos slotTime segun la duracion
        int slotIndex = 1;
        for(List<TimeSlot> list : timeSlots){
            
            // per a totes les hores que te la taula
            for (LocalDateTime startTime = date.atTime(firstSlotStart);
                    !startTime.isAfter(date.atTime(lastSlotStart));
                    startTime = startTime.plus(slotLength)) {

                //---------------------------------------------------------------------------------------
                // creamos el SlotTime, lo anyadimos a la lista de la columna y asignamos sus manejadores
                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                timeSlots.add(list);
                registerHandlers(timeSlots);
                //-----------------------------------------------------------
                // lo anyadimos al grid en la posicion x= 1, y= slotIndex
                grid.add(timeSlot.getView(), 1, slotIndex);
                slotIndex++;
            }
        }
    }

   private void registerHandlers(List<List<TimeSlot>> timeSlots) {
    for (List<TimeSlot> slotList : timeSlots) {
        for (TimeSlot timeSlot : slotList) {
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
                    alerta.setTitle("SlotTime");
                    alerta.setHeaderText("Confirma la selecció");
                    alerta.setContentText("Has seleccionat: "
                            + timeSlot.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + ", "
                            + timeSlot.getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                    Optional<ButtonType> result = alerta.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        ObservableList<String> styles = timeSlot.getView().getStyleClass();
                        if (styles.contains("time-slot")) {
                            styles.remove("time-slot");
                            styles.add("time-slot-libre");
                        } else {
                            styles.remove("time-slot-libre");
                            styles.add("time-slot");
                        }
                    }
                }
            });
        }
    }
}
public class TimeSlot {

        private final LocalDateTime start;
        private final Duration duration;
        protected final Pane view;

        private final BooleanProperty selected = new SimpleBooleanProperty();

        public final BooleanProperty selectedProperty() {
            return selected;
        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;
            this.duration = duration;
            view = new Pane();
            view.getStyleClass().add("time-slot");
            // ---------------------------------------------------------------
            // de esta manera cambiamos la apariencia del TimeSlot cuando los seleccionamos
            selectedProperty().addListener((obs, wasSelected, isSelected)
                    -> view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));

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
