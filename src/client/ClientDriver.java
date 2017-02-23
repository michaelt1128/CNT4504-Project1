package client;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.awt.*;
import javax.swing.*;
import java.lang.StringBuilder;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.Scanner;

class ClientFunc{
    private int numClients;
    private Thread[] clients;
    
    public ClientFunc(int numclients){
        numClients = numclients;
        
        while(true){
            switch(printMenu()){
                case 1:
                    initThreads(numclients, "date");
                    break;
                    
            }
           
        }
    }
    
    private void runCommand(String command){
        long start = System.currentTimeMillis();
        for(Thread c : clients){
            c.start();
        }
        long end = System.currentTimeMillis() - start;
        System.out.println(end + "ms");
    }
    
    public int printMenu(){
        System.out.printf("Enter number corresponding to the command:\n1.date\n2.Uptime\n3.Memory\n4.Netstat\n5.Users\n6.Processes\n0.Exit\n");
        Scanner input = new Scanner(System.in);
        int nextinput = input.nextInt();
        return nextinput;
    }
    public void initThreads(int numClients, String command){
        clients = new Thread[numClients];
        for(int i = 0; i < numClients; i++){
            clients[i] = new Thread(new Client(command), "thread" + i);
            
        }
    }
}

public class ClientDriver {
    
    private Menu m = new Menu();
    
    public static void main(String[] args){
        System.out.println("How many clients do you want to run?");
        Scanner input = new Scanner(System.in);
        int numClients = input.nextInt();
        ClientFunc cf = new ClientFunc(numClients);
        cf.printMenu();
        
    }

    /*public String userInput(String userInput) throws IOException {
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
*/
}
