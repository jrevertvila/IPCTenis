/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;


public class JavaFXMLApplication extends Application {
    
    private static Scene scene;
    private static HashMap<String, Node> frames = new HashMap<>();
    private static Stage stage;
    public static Member current_user = null;
    public static Node getFrame(String frameName) {
        return frames.get(frameName);
    }
    
    public static  Node setFrame(String frameName, Node loader) {
        frames.put(frameName, loader);
        return loader;
    }
    
    public static void removeFrames(String[] framesList) {
        for (String frame : framesList) frames.remove(frame);
    }
    
    public static void setRoot(Parent root) {
        scene = new Scene(root);
        stage.close();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("GreenBall");
        stage.show();
    }
    
    public static Stage currentStage = null;
    
    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        currentStage = stage;
        Club c = Club.getInstance();
        System.out.println(c.getBookings());
        
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("view/InicioRefactor.fxml"));
        Parent root = loader.load();
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        scene = new Scene(root);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        scene.getStylesheets().add(getClass().getResource("../style/fontstyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("GreenBall");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
    public static boolean isLogged() throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        boolean res = club.existsLogin(current_user.getNickName());
        return res;
        
    }

    
}
