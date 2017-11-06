
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Server class that deal with the request.
 * 
 * @author bl41
 *
 */
public class Server {

	private static InputStream ins;
	private static OutputStream os;
	private static BufferedReader br;

	/**
	 * Method to handle the request.
	 * 
	 * @param path
	 *            File path
	 * @param socket
	 *            Client socket
	 */
	public static void requestHandler(String path, Socket socket) {
		try {
			ins = socket.getInputStream();
			os = socket.getOutputStream();
			br = new BufferedReader(new InputStreamReader(ins));
			PrintWriter out = new PrintWriter(os, true);
			String recv = "";
			String line = "";
			// Get request
			while (!(line = br.readLine()).isEmpty()) {
				recv = recv + line;
			}
			// Construct request
			String[] requests = recv.split(" ");
			// Get response message
			String resp = getResponse(path, requests);
			out.println(resp);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println("ConnectionHandler: " + e.getMessage());
		}
	}

	/**
	 * Method to get response.
	 * 
	 * @param path
	 *            File path
	 * @param requests
	 *            An array of string that contains the constructed request
	 * @return Return the response message
	 */
	public static String getResponse(String path, String[] requests) {
		String response = "";
		int flag = 0; // flag to identify existence of file
		try {
			// Get request file
			File f = new File("www" + requests[1]);
			String fname = f.getName();
			String ftype = fname.substring(fname.lastIndexOf(".") + 1);
			long flength = f.length();
			// Check if file exist and response
			if (!f.exists()) {
				flag = Configurations.NOT_EXIST; // File not exist
				HttpResponser httpresp = new HttpResponser(flag, "", ftype, flength);
				response = httpresp.toString();
			} else {
				flag = Configurations.EXIST; // File exist
				// Switch method to make response accordingly
				switch (requests[0]) {
				case "GET":
					FileInputStream fis = new FileInputStream(f);
					byte[] bytes = new byte[fis.available()];
					fis.read(bytes);
					String contentBuffer = new String(bytes, "UTF-8");
					HttpResponser getResp = new HttpResponser(flag, contentBuffer, ftype, flength);
					response = getResp.toString();
					System.out.println(response);
					fis.close();
					break;
				case "HEAD":
					HttpResponser headResp = new HttpResponser(flag, "", ftype, flength);
					response = headResp.toString();
					System.out.println(response);
					break;
				default:
					System.out.println("HTTP/1.1 501 Not Implemented");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return response;
	}
}
