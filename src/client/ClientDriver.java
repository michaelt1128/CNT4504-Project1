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
import java.net.SocketException;

class ClientFunc {

    private int numClients;
    private Thread[] clients;
    private int responseTime;

    public ClientFunc() throws InterruptedException {
        setNumClients();

        while (true) {
            switch (printMenu()) {
                case 1:
                    initThreads(numClients, "date");
                    break;
                case 2:
                    initThreads(numClients, "uptime");
                    break;
                case 3:
                    initThreads(numClients, "memory");
                    break;
                case 4:
                    initThreads(numClients, "netstat");
                    break;
                case 5:
                    initThreads(numClients, "users");
                    break;
                case 6:
                    initThreads(numClients, "processes");
                    break;
                case 99:
                    setNumClients();
                    break;
                case 0:
                    System.out.println("Exiting Environment...");
                    System.exit(0);
                    
            }
        }

    }

    public void setNumClients(){
        Scanner input = new Scanner(System.in);
        System.out.println("How many clients do you want to run?");
        numClients = input.nextInt();
    }

    public int printMenu() {
        responseTime = 0;
        System.out.printf("Enter number corresponding to the command:"
                + "\n1.Date\n2.Uptime\n3.Memory\n4.Netstat\n5.Users\n6.Processes\n99.Change Number of Clients\n0.Exit\n");
        Scanner input = new Scanner(System.in);
        int nextinput = input.nextInt();
        return nextinput;
    }

    public void initThreads(int numClients, String command) {
        try {
            clients = new Thread[numClients];
            for (int i = 0; i < numClients; i++) {
                Client client = null;
                clients[i] = new Thread(client = new Client(command), "thread" + i);
                clients[i].start();
                clients[i].join();
                responseTime += client.getResponseTime();
            }
            System.out.printf("==== Total Duration: %d ms ====\n\n", responseTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

public class ClientDriver {

    public static void main(String[] args) {

        try {
            ClientFunc cf = new ClientFunc();
            cf.printMenu();

        } catch (InterruptedException ie) {
            ie.notifyAll();
        }

    }
}
