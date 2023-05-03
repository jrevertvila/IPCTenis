/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author joan
 */
public class PistaSinConexionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Club club;
   
    @FXML
    private Button botoxd;
   // private ListView<Member> ListView;
    
    private ObservableList<Member> miembros = null;
   
    @FXML
    private TableView<Member> TableView;
    
    
    @FXML
     private TableColumn<Member, String> Column1 ;
    @FXML
    private TableColumn<Member, String> Column2;
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Instanciació de la base de dades 
        try {
            // TODO
            club = Club.getInstance();
            club.addSimpleData();
        } catch (ClubDAOException ex) {
            Logger.getLogger(PistaSinConexionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PistaSinConexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        
        
        //ArrayList<Member> membres = club.getMembers();
        
        
        List<Member> m = club.getMembers();
        int listSize = m.size();
        
        miembros = FXCollections.observableArrayList(m);
        /*  
        for(int i = 0; i< listSize; i++){
            System.out.println("membre: "+m.get(i).getName() + " ");
             System.out.println(""+m.get(i).getSurname() + " \n");
             System.out.println(""+m.get(i).getPassword() + " \n");
        
        }
    */
        
        
       // Column1 = new TableColumn<>("Nombre primero");
        
        //Column1.setText(m.get(3).getName());
        
        //TableView.getColumns().addAll(Column1,Column2);
        TableView.setItems(miembros);
        Column1.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        Column2.setCellValueFactory(new PropertyValueFactory<Member, String>("surname"));
        
        
        
        //Column1.setCellFactory((Callback<TableColumn<Member, String>, TableCell<Member, String>>) miembros);
        
       // TableView.getColumns().addAll(Column1,Column2);
        //ListView.setItems(miembros);
        
      
        
        
        
        
               
    }  

    @FXML
    private void test(ActionEvent event) {
        System.out.println(club.getName());
                System.out.println(club.getCourts());
                                  System.out.println(club.getBookings());

                System.out.println(club.getMembers());

    }
    
    
   
}
