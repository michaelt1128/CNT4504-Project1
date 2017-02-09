package server;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Server {
    public static void main(String[] args) throws IOException {	
        int portNumber = 5012;
        try (
        	// Creates socket
            ServerSocket serverSocket = new ServerSocket(portNumber);
        	
            Socket clientSocket = serverSocket.accept();     
        		
        	// Prints to socket
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
        	
        	// Receives from socket
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
            	String outputStr = "Received string: " + inputLine + "\n";
                switch (inputLine){
                    //add api cases here
                    case "date": 
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        outputStr += dateFormat.format(new Date());
                        break;
                    case "uptime":
                    	break;
                    case "memory":
                    	break;
                    case "netstat":
                        try {
                        String line;
                        String netstat = "";
                        Runtime rt = Runtime.getRuntime();
                        Process pr = rt.exec("ps");
                        
                        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                        while((line=input.readLine()) != null) {
                            System.out.println(line);
                            netstat += line + "\n";
                        }
                       System.out.print(netstat);
                       outputStr += netstat;
                       input.close();
                        } catch(Exception err){
                            err.printStackTrace();
                        }
                       
                        break;
                    case "users":
                    	break;
                    case "processes":
                    	break;
                    case "close":
                    	clientSocket.close();
                    	outputStr += "Closed socket";
                    	break;
                    default:
                        outputStr += "Error: not a valid input";
                        break;
                }
                //appends 'end' to mark end of transmission
                out.println(outputStr + "\nend");
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}