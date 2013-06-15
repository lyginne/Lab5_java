/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Locale;

public class Localization {
    public static Locale getLocale(){
	System.out.println(Locale.getDefault());
        return Locale.getDefault();
        //return new Locale("pt", "PT");
    }    
}
