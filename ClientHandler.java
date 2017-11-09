import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handler to handle client thread (support multiple concurrent client
 * connection request).
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
    private PrintWriter out;

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
            out = new PrintWriter(os, true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Run method invoked when Thread's start method is invoked.
     */
    @Override
    public void run() {
        System.out.println("new ConnctionHandler thread started .... ");
        try {
            requestHanlder();
            os.write(Configurations.ACK);
        } catch (Exception e) { // Exit cleanly for any Exception
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
                // PrintWriter out = new PrintWriter(os, true);
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
                getResponse(path, requests);
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
     */
    public void getResponse(String path, String[] requests) {
        int flag = 0; // flag to identify existence of file
        String request = requests[0];
        String protocol = requests[2];
        // Get request file
        File f = new File(path + requests[1]);
        String fname = f.getName();
        String ftype = fname.substring(fname.lastIndexOf(".") + 1);
        long flength = f.length();
        ResponseHandler.responseHandler(f, flag, ftype, flength, request, protocol, out);
    }

    /**
     * Method to clean and close resources.
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
