/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Usuario
 */


public class Utils {
    private static final Pattern SOLO_LETRAS_PATTERN = Pattern.compile("^[a-zA-Z]+$");
    private static final Pattern NUEVE_NUMEROS_PATTERN = Pattern.compile("^\\d{9}$");
    private static final Pattern SOLO_NUMEROS_PATTERN = Pattern.compile("\\d+$");
    private static final Pattern DIECISEIS_NUMEROS_PATTERN = Pattern.compile("^\\d{16}$"); 
    private static final Pattern SOLO_CONTRASEÑA_SEGURA = Pattern.compile("^(?=.*\\d)(?=.*[A-Z])\\S{5,10}$");
    private static final Pattern SOLO_TRES_NUMEROS = Pattern.compile("^\\d{3}$");
 
    public static boolean isPhoneNumber(String phone) {
         return NUEVE_NUMEROS_PATTERN.matcher(phone).matches();
    }
    
    public static boolean isOnlyLetters (String letters) {
        return SOLO_LETRAS_PATTERN.matcher(letters).matches();
    }
    
    public static boolean isNumeric(String num) {
        return SOLO_NUMEROS_PATTERN.matcher(num).matches();
    }
    
    public static boolean isCreditCard(String card) {
        return DIECISEIS_NUMEROS_PATTERN.matcher(card).matches(); 
    }
    
    public static boolean isSecurePassword(String password) {
        return SOLO_CONTRASEÑA_SEGURA.matcher(password).matches();
    }

    public static boolean isCSV(String csv) {
        return SOLO_TRES_NUMEROS.matcher(csv).matches(); 
    }
        
   
    public static File uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File localImgFile;
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Archivos de imagen", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        
        File initialDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(initialDirectory);
        
        localImgFile = fileChooser.showOpenDialog((Stage)((Node) event.getSource()).getScene().getWindow());
        
        if (localImgFile != null && !isValidExtension(localImgFile, imageFilter)) {
            TrayNotification notif = new TrayNotification();
            notif.setAnimationType(AnimationType.POPUP);
            notif.setTitle("Archivo Invalido");
            notif.setMessage("El archivo seleccionado no es una imagen válida");
            notif.setNotificationType(NotificationType.ERROR);
            notif.showAndDismiss(Duration.millis(3000));
            return uploadImage(event);
        } else {
            return localImgFile;
        }
//        if (imgFile != null) {
////            openFile(file);
//            System.out.println(imgFile);
//        }

    }
    
    private static boolean isValidExtension(File file, ExtensionFilter filter) {
        String extension = getExtension(file);
        for (String ext : filter.getExtensions()) {
            if (extension != null && ext.endsWith("*." + extension)) {
                return true;
            }
        }
        return false;
    }

    private static String getExtension(File file) {
        String name = file.getName();
        int lastIndexOfDot = name.lastIndexOf(".");
        if (lastIndexOfDot != -1 && lastIndexOfDot < name.length() - 1) {
            return name.substring(lastIndexOfDot + 1);
        }
        return "";
    }
    
}
