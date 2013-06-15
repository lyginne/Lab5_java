/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.*;

public class Lab5_server {
static Socket clientSocket;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        		try{
			ServerSocket connectionSocket = new ServerSocket(7995);//make a new socket to accept connections on
			System.out.println("Server Running");
			while (true) {//forever!
				clientSocket = connectionSocket.accept();
                                ConnectionMarkChecker connectionChecker = new ConnectionMarkChecker(clientSocket);//wait for a conncetion from a client 
                                new Thread(connectionChecker).start(); //start that threaded object.
			}
		}catch(SocketException e){
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
