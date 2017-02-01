package client;

import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) throws IOException {
		String hostName = "127.0.0.1";
		int portNumber = 5012;
		try (
			Socket echoSocket = new Socket(hostName, portNumber);
			
			// out : message sent to server
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			//in : message received from server
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			//stdIn : user's message typed in console
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
		) {
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				
				// Print user input to socket
				out.println(userInput);

				System.out.println("Server response:");
				
				// Reads and prints all lines in server response
				String serverRes;
				while ((serverRes = in.readLine()).length() > 0) {
					if(serverRes.equals("end")) {
						break;
					} else {
						System.out.println(serverRes);
					}
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}
}