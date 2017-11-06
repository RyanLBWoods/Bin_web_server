import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 2){
			System.out.println("Usage: java WebServerMain <document_root> <port>");
			System.exit(1);
		}
		int port = Integer.valueOf(args[1]);
		String path = args[0];
		
//			Server server = new Server();
		try {
			ServerSocket ss = new ServerSocket(port);
			Socket socket = ss.accept();
			Server.requestHandler(path, socket);
			socket.close();
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
