/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector extends Thread {
    
    public static volatile boolean replies;

    Mark mark;
    int R;
    
    public Connector(Mark mark, int R){
        this.mark = mark;
        this.R=R;
    }

    @Override
    public void run() {
        synchronized(mark){
            Socket s;
                    try {
                        s = new Socket("127.0.0.1", 7995);


                    OutputStream os = s.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);
                    dos.writeFloat(mark.getX());
                    dos.writeFloat(mark.getY());
                    dos.writeFloat(R);
                    s.getInputStream();
                    int result = (new DataInputStream(s.getInputStream())).readInt();

                    if(result == 0)
                        mark.state = MarkState.DidNotBelongsToOutline;
                    else
                        mark.state = MarkState.BelongsToOutline;
                    //replies=true;
                } catch (UnknownHostException ex) {
                    mark.state = MarkState.ServerDoesNotRespond;
                    replies=false;
                } catch (IOException ex) {
                    replies=false;
                    mark.state = MarkState.ServerDoesNotRespond;
                }
        }
    }
    
}
