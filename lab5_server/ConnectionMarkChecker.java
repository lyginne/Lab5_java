/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionMarkChecker implements Runnable {

    Socket clientSocket;
    
    ConnectionMarkChecker(Socket clientSocket){
        this.clientSocket=clientSocket;
    }
    @Override
    public void run() {
        try {
            float x = (new DataInputStream(clientSocket.getInputStream())).readFloat();
            float y = (new DataInputStream(clientSocket.getInputStream())).readFloat();
            float R = (new DataInputStream(clientSocket.getInputStream())).readFloat();
            Outline outline = new Outline((int)R);
            if(outline.contains(x,y))
                (new DataOutputStream(clientSocket.getOutputStream())).writeInt(1);
            else
                (new DataOutputStream(clientSocket.getOutputStream())).writeInt(0);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionMarkChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
