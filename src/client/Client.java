/*
* Course:       CNT 4504
* Assignment:   Project 1
* Members:      Liam Clarke, Kaleb LaBarrie, 
*               William Mejia, Trang Nguyen,
*               Michael Turner, Mathew Waroff
*           
* Description:  --Client.java--
*               Client program is responsible for creating a serverSocket as well as printWriters and BufferedReaders in order to 
*               exchange data between client and server.  The client will issue commands set by the user as a request to the server,
*               and server will respond with appropriate commands and response time.
*               
*/


package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.StringBuilder;
import java.util.Date;

public class Client extends Thread {
    
    // Initialize variables 
    private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    public String serverResponse;
    public boolean connected;
    private String hostName = "127.0.0.1";
    private int portNumber = 10000;
    private String command;
    private double responseTime;

    public Client(){
    }
    public Client(String command, int count){
        this.command = command;
        portNumber += count;
        System.out.println(portNumber);
    }
    
    
    @Override
    public void run(){
        StringBuilder sb = new StringBuilder();
        try{
            connect();
            double start = (double)System.nanoTime();
            out.println(command);
            // Reads and prints all lines in server response
            String serverRes;
            
            // While server response is not empty and doesn't read "end", 
            // append server response time on new line after each command sent by the client.
            System.out.println(this.getName());
            while ((serverRes = in.readLine()) != null) {
                if (serverRes.equals("end")){
                	responseTime = (double)System.nanoTime() - start;

                    serverSocket.close();
                    in.close();
                    out.close();
                    break;
                } else {
                    sb.append(serverRes + "\n");
               }
            }
		System.out.println(sb.toString());
	        System.out.printf("Run Time: %f \n", responseTime/1000000);
    
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public double getResponseTime() {
        return responseTime;
    }

     
     // Connect method creates a new serverSocket with hostname and port# paramaters
     // Printwriter and BufferedReader objects included to send / receive data between client and server
    public void connect() {
        try {
                Socket testSocket = new Socket(hostName, 11000);
                /*serverSocket = new Socket(hostName, portNumber);
                // out : message sent to server
                out = new PrintWriter(serverSocket.getOutputStream(), true);
                //in : message received from server
                in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                */
                System.out.println(testSocket.toString());
                connected = true;
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
