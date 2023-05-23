/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.util.regex.Pattern;

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

        
   
    
    
}
