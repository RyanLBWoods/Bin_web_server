import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handler to handle client thread.
 * 
 * @author bl41
 *
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private String path;
    private InputStream is;
    private OutputStream os;
    private BufferedReader br;

    /**
     * Constructor for client handler.
     * 
     * @param path
     *            File path
     * @param socket
     *            Socket for client
     */
    public ClientHandler(String path, Socket socket) {
        this.socket = socket;
        this.path = path;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Run method invoked when Thread's start method is invoked.
     */
    public void run() {
        System.out.println("new ConnctionHandler thread started .... ");
        try {
            // RequestHandler.requestHandler(path, socket);
            requestHanlder();
            os.write(Configurations.ACK);
            // cleanup();
        } catch (Exception e) {
            System.out.println("ConnectionHandler: run " + e.getMessage());
            cleanup();
        }
    }

    /**
     * Method to handle the request.
     * 
     * @throws Exception
     *             Throw exception when client is broken
     */
    public void requestHanlder() throws Exception {
        while (true) {
            try {
                PrintWriter out = new PrintWriter(os, true);
                String recv = "";
                String line = "";
                // Get request
                line = br.readLine();
                if (line == null || line.equals("null")) {
                    throw new Exception("... client has closed the connection ... ");
                }
                while (!line.isEmpty()) {
                    recv = recv + line;
                    line = br.readLine();
                }
                // Construct request
                String[] requests = recv.split(" ");
                // Get response message
                String resp = getResponse(path, requests);
                System.out.println(resp);
                out.println(resp);
                out.flush();
                out.close();
            } catch (IOException e) {
                System.out.println("ConnectionHandler: " + e.getMessage());
            }
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
    public String getResponse(String path, String[] requests) {
        String response = "";
        int flag = 0; // flag to identify existence of file
        String request = requests[0];
        // Get request file
        File f = new File(path + requests[1]);
        String fname = f.getName();
        String ftype = fname.substring(fname.lastIndexOf(".") + 1);
        long flength = f.length();
        response = ResponseHandler.responseHandler(f, flag, ftype, flength, request);
        return response;
    }

    /**
     * Method to close resources.
     */
    public void cleanup() {
        System.out.println("ClientHandler: ... cleaning up and exiting ...");
        try {
            br.close();
            is.close();
            socket.close();
        } catch (IOException ioe) {
            System.out.println("ClientHandler: cleanup " + ioe.getMessage());
        }
    }
}
