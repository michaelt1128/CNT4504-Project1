/*
* Course:       CNT 4504
* Assignment:   Project 1
* Members:      Liam Clarke, Kaleb LaBarrie, 
*               William Mejia, Trang Nguyen,
*               Michael Turner, Mathew Waroff
*           
* Description:  --Client Driver--
*               This Program is responsible for displaying Client functions as a menu item and taking user input commands.
*               Also initiliazes threads based on user input and measures response time for each thread.
*/

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
    private Client[] clients;
    private Thread[] threads;
    private double responseTime;


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

    // Prompt user for number of clients and store in numClients var.
    public void setNumClients(){
        Scanner input = new Scanner(System.in);
        System.out.println("How many clients do you want to run?");
        numClients = input.nextInt();
    }

    // Print basic menu of commands and prompt user for a command
    public int printMenu() {
        responseTime = 0;
        System.out.printf("=============================================\n|%-43s|\n| %-42s|\n| %-42s|\n| %-42s|\n| %-42s|\n| %-42s|\n| %-42s|\n| %-42s|\n| %-42s|\n============================================\n",
		"Enter number corresponding to the command:", "1.Date", "2.Uptime", "3.Memory", "4.Netstat", "5.Users", "6.Processes", "99.Change Number of Clients", "0.Exit");
        System.out.printf("Command: ");
	Scanner input = new Scanner(System.in);
        int nextinput = input.nextInt();
        return nextinput;
    }

    // Initialize threads based on user input and include response time
    public void initThreads(int numClients, String command) {
        try {
            clients = new Client[numClients];
 	    threads = new Thread[numClients];
            new Thread(new Client(command, 0), "Thread").start();
            /*
            for (int i = 0; i < numClients; i++) {
                threads[i] = new Thread(clients[i] = new Client(command, i), "thread" + i);
                threads[i].start();
            }	*/
            System.out.printf("==== Total Duration: %.2f ms ====\n\n", responseTime/1000000);
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
