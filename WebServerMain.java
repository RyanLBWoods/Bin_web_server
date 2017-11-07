import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * CS5001-P3: A Java web server which can response to some HTTP/1.1 request.
 * 
 * @author bl41
 *
 */
public class WebServerMain {
    /**
     * Main method for running the server.
     * 
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // Print usage message if inadequate arguments
        if (args.length < 2) {
            System.out.println("Usage: java WebServerMain <document_root> <port>");
            System.exit(0);
        }
        // Get port number and path
        int port = Integer.valueOf(args[1]);
        String path = args[0];
        // Run the server
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket socket = ss.accept();
            RequestHandler.requestHandler(path, socket);
            socket.close();
            ss.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
