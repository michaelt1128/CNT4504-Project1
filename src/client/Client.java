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
     StringBuilder sb = new StringBuilder();
    
    private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;
    public String serverResponse;
    public boolean connected;
    private String hostName = "127.0.0.1";
    private int portNumber = 5012;
    private String command;
    private long responseTime;

    public Client(){
    }
    public Client(String command){
        this.command = command;
    }
    /*public Client(String hostname, int port) {
        hostName = hostname;
        portNumber = port;
        //connect();
    }*/
    
    @Override
    public void run(){
        try{
            connect();
            Date start = new Date();
            out.println(command);
            // Reads and prints all lines in server response
            String serverRes;
            
            System.out.println(this.getName());
            while ((serverRes = in.readLine()) != null) {
                responseTime = new Date().getTime() - start.getTime();
                if (serverRes.equals("end")){
                    break;
                } else {
                    sb.append(serverRes + "\n");
                }
                
            }
            System.out.printf(sb.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public long getResponseTime() {
        return responseTime;
    }

    public void connect() {
        try {
                
                serverSocket = new Socket(hostName, portNumber);
                // out : message sent to server
                out = new PrintWriter(serverSocket.getOutputStream(), true);
                //in : message received from server
                in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                //stdIn : user's message typed in console
                
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