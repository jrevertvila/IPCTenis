/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Celia_
 */
public class AboutUsController implements Initializable {

    @FXML
    private Text text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        text.setText("¡Bienvenido a GreenBall!\n" +
"\n" +
"En GreenBall, nos enorgullece ser un club de tenis de primer nivel que ofrece a nuestros miembros una experiencia excepcional en el mundo del tenis. Desde nuestros inicios, nos hemos dedicado a fomentar la pasión por este deporte y a promover un ambiente amigable y acogedor para jugadores de todas las edades y niveles de habilidad.\n" +
"\n" +
"Nuestro club cuenta con modernas instalaciones y una amplia gama de servicios diseñados para satisfacer las necesidades de nuestros miembros. Disponemos de múltiples canchas de tenis de alta calidad, tanto de tierra batida como de superficie dura, que están disponibles para el juego durante todo el año. Además, ofrecemos programas de entrenamiento impartidos por profesionales altamente capacitados, quienes se enfocan en mejorar las habilidades de nuestros miembros y brindarles las herramientas necesarias para alcanzar su máximo potencial.\n" +
"\n" +
"En GreenBall, creemos en la importancia de la comunidad y en la formación de lazos duraderos entre nuestros miembros. Organizamos eventos sociales, torneos internos y actividades especiales que permiten a los jugadores interactuar, disfrutar del tenis y crear amistades dentro y fuera de la cancha. Además, ofrecemos programas juveniles destinados a cultivar el amor por el tenis entre los más jóvenes, fomentando los valores de trabajo en equipo, disciplina y espíritu deportivo.\n" +
"\n" +
"Ya sea que seas un jugador aficionado que busca divertirse y mantenerse activo, o un jugador competitivo que busca desafíos emocionantes, GreenBall es el lugar ideal para ti. Nuestro objetivo es proporcionarte una experiencia única en un entorno inspirador que te permita disfrutar del tenis al máximo.\n" +
"\n" +
"Únete a GreenBall hoy mismo y descubre todo lo que tenemos para ofrecerte. ¡Esperamos verte en nuestras canchas!\n" +
"");
    }    
    
}
