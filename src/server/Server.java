/*
* Course:       CNT 4504
* Assignment:   Project 1
* Members:      Liam Clarke, Kaleb LaBarrie,
*               William Mejia, Trang Nguyen,
*               Michael Turner, Mathew Waroff
*
* Description:  --Server--
*               This class is responsible for sending information to the clients through a serverSocket
 */
package server;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.lang.StringBuilder;
import java.util.ArrayList;


//Receiving Server
public class Server {
    private static int PORT = 5013;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Trying to connect to new client");
                new ServerThread(serverSocket.accept());
                System.out.println("Connected to client");
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + PORT
                               + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

class ServerThread implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerThread(Socket s) throws IOException {
        socket = s;
        // Prints to socket
        out = new PrintWriter(socket.getOutputStream(), true);
        // Receives from socket
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(this).start();

    }
    private Date startTime = new Date();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private SimpleDateFormat timeDifFormat = new SimpleDateFormat("mm:ss;SSS");

    @Override
    public void run() {
        try {
            String inputLine = in.readLine();
            System.out.println("Received: " + inputLine);
            StringBuilder outputStr = new StringBuilder("Received string: " + inputLine + "\n");
            switch (inputLine) {
            // add api cases here
            case "date":
                outputStr.append(dateFormat.format(new Date()));
                break;
            case "uptime":
                outputStr.append(timeDifFormat.format(new Date().getTime() - startTime.getTime()) + " uptime");
                break;
            case "memory":
                outputStr.append(memoryUsage());
                break;
            case "netstat":
                outputStr.append(runCommand("netstat"));
                break;
            case "users":
                outputStr.append(runCommand("who"));
                break;
            case "processes":
                outputStr.append(runCommand("ps -aux"));
                break;
            case "close":
                socket.close();
                outputStr.append("Closed socket");
                break;
            default:
                outputStr.append("Error: not a valid input");
                break;
            }
            // appends 'end' to mark end of transmission
            out.println(outputStr.append("\nend").toString());

            socket.close();
            out.close();
            in.close();

        } catch (Exception ex) {
            ex.toString();
        }

    }


    private static String runCommand(String command) {
        try {
            String line;
            String cmdOutput = "";
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command);

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            while ((line = input.readLine()) != null) {
                cmdOutput += line + "\n";
            }

            input.close();
            pr.destroy();
            return cmdOutput;
        } catch (Exception err) {
            err.printStackTrace();
            return err.toString();
        }
    }

    private static String memoryUsage() {
        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        sb.append("free memory: " + format.format(freeMemory / 1024) + "\n");
        sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + "\n");
        sb.append("max memory: " + format.format(maxMemory / 1024) + "\n");
        sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "\n");
        return sb.toString();
    }

}
