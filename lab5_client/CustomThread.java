/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Женя
 */
public class CustomThread extends Thread{
    private ArrayList<ActionListener> ActionListeners
       = new ArrayList<ActionListener>();
    int millis;
    @Override
    public void run(){
        for(;;){
            try {
                Thread.sleep(millis);
                fireTimerExceedEvent();
            } catch (InterruptedException ex) {
                //Logger.getLogger(CustomThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    CustomThread(int millis){
        this.millis=millis;
    }
    public void addTimerExceedListener(ActionListener listener){
 	              ActionListeners.add(listener);
    }

    public ActionListener[] getTimerExceedListeners(){
                 return ActionListeners.toArray(
                         new ActionListener[ActionListeners.size()]);
    }

    public void removeTimerExceedListener(ActionListener listener){
                  ActionListeners.remove(listener);
    }

    protected void fireTimerExceedEvent(){
        ActionEvent ev = new ActionEvent((Object)this,0, "");
        for (ActionListener listener : ActionListeners){
           listener.actionPerformed(ev);
        }
    }
}
