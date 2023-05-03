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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private TextArea TextNom;
    @FXML
    private Button botoxd;
    
    
    
            
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
      //  System.out.println("NOOOOOOOMMMMM: "+club.getMembers());
        
        
        //ArrayList<Member> membres = club.getMembers();
        List<Member> m = club.getMembers();
        int listSize = m.size();
        
        
        for(int i = 0; i< listSize; i++){
            System.out.println("membre: "+m.get(i).getName() + " ");
             System.out.println(""+m.get(i).getSurname() + " \n");
             System.out.println(""+m.get(i).getPassword() + " \n");
        
        }
        
        
        
        
               
    }  

    @FXML
    private void test(ActionEvent event) {
        System.out.println(club.getName());
                System.out.println(club.getCourts());
                                  System.out.println(club.getBookings());

                System.out.println(club.getMembers());

    }
    
    
   
}
