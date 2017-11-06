

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
//	private ServerSocket ss;
	private static InputStream ins;
	private static OutputStream os;
	static BufferedReader br;
	
//	public Server(int port){
//		try{
//			ss = new ServerSocket(port);
//			System.out.println("Server started ... listening on port " + port + "...");
//			Socket conn = ss.accept();
//			System.out.println("Connection request from " + conn.getInetAddress());
//			conn.close();
//		} catch (IOException e){
//			System.out.println("Ooops" + e.getMessage());
//		}
//	}
	
	public static void requestHandler(String path, Socket socket){
		try{
			ins = socket.getInputStream();
			os = socket.getOutputStream();
			br = new BufferedReader(new InputStreamReader(ins));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			String recv = "";
			String line = "";
			while((line = br.readLine()) != null){
				recv = recv + line;
			}
			String[] requests = recv.split(" ");
			String resp = getResponse(path, requests);
			out.println(resp);
			out.flush();
			out.close();
		} catch(IOException e){
			System.out.println("ConnectionHandler: " + e.getMessage());
		}
	}
	
	public static String getResponse(String path, String[] requests){
		String response = "123";
		int flag = 0;
		try{
			File f = new File("www" + requests[1]);
			String fname = f.getName();
			String ftype = fname.substring(fname.lastIndexOf(".") + 1);
			long flength = f.length();
			if(!f.exists()){
				flag = -1;
				HttpResponser httpresp = new HttpResponser(flag, null, ftype, flength);
				response = httpresp.toString();
			} else {
				flag = 1;
				switch (requests[1]) {
					case "GET":
						FileInputStream fis = new FileInputStream(f);
						byte[] bytes = new byte[fis.available()];
						fis.read(bytes);
						String contentBuffer = new String(bytes, "UTF-8");
						HttpResponser GETresp = new HttpResponser(flag, contentBuffer, ftype, flength);
						response = GETresp.toString();
						fis.close();
						break;
					case "HEAD":
						HttpResponser HEADresp = new HttpResponser(flag, null, ftype, flength);
						response = HEADresp.toString();
						break;
				}
			}
		} catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return response;
	}
}
