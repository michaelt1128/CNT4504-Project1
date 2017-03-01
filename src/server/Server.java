/*
* Course:       CNT 4504
* Assignment:   Project 1
* Members:      Liam Clarke, Kaleb LaBarrie, 
*               William Mejia, Trang Nguyen,
*               Michael Turner, Michael Waroff
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

public class Server {
	public static void main(String[] args) throws IOException {

		System.out.println("Starting server");
		Date startTime = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat timeDifFormat = new SimpleDateFormat("mm:ss;SSS");
		boolean connect = true;

		while (connect) {
			System.out.println("trying to connect");
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
							clientSocket.close();
							outputStr.append("Closed socket");
							break;
						default:
							outputStr.append("Error: not a valid input");
							break;
					}
					// appends 'end' to mark end of transmission
					out.println(outputStr.append("\nend").toString());

					clientSocket.close();
					out.close();
					in.close();
					break;
				}
			} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port " + portNumber
						+ " or listening for a connection");
				System.out.println(e.getMessage());
			}

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
