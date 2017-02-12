package client;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.awt.*;
import javax.swing.*;
import java.lang.StringBuilder;

public class Client {

    private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;
    public String serverResponse;
    public boolean connected;
    private String hostName;
    private int portNumber;

    public Client(String hostname, int port) {
        hostName = hostname;
        portNumber = port;
        connect();
    }

    public String userInput(String userInput) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (userInput != null) {
            // Print user input to socket
            Date start = new Date();
            System.out.println("Server response:");
            out.println(userInput);
            
            // Reads and prints all lines in server response
            String serverRes;
            while ((serverRes = in.readLine()) != "end" && serverRes != null) {
                System.out.println(serverRes);
                if (serverRes.equals("end")){
                    break;
                } else {
                    sb.append(serverRes + "\n");
                }
                if(new Date().getTime() - start.getTime() > 5000) {
                    return "Server timeout";
                }
            }
            Date end = new Date();
            sb.append("Response time:" + (end.getTime() - start.getTime()) + "ms");
            userInput = null;
        }
        return sb.toString();
    }

    public void connect() {
        try {
                serverSocket = new Socket(hostName, portNumber);
                // out : message sent to server
                out = new PrintWriter(serverSocket.getOutputStream(), true);
                //in : message received from server
                in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                //stdIn : user's message typed in console
                stdIn = new BufferedReader(new InputStreamReader(System.in));
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
