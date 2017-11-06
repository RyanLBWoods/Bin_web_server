

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket socket = new Socket("localhost", 12345);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			String requestStr = "GET index.html HTTP/1.1";
			out.println(requestStr);
			String host = "Host: localhost: 8080";
			out.println(host);
			out.println("\r\n");
			out.flush();
			String recv = "";
			Scanner sc = new Scanner(socket.getInputStream());
			while(sc.hasNext()){
				recv = recv + sc.nextLine() + "\n";
			}
			System.out.println(recv);
			sc.close();
			out.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
