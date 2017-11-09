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
    private PrintWriter out;/* = new PrintWriter(os, true);*/

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
//                PrintWriter out = new PrintWriter(os, true);
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
     * @return Return the response message
     */
    public void getResponse(String path, String[] requests) {
//        String response = "";
        int flag = 0; // flag to identify existence of file
        String request = requests[0];
        // Get request file
        File f = new File(path + requests[1]);
        String fname = f.getName();
        String ftype = fname.substring(fname.lastIndexOf(".") + 1);
        long flength = f.length();
//        response = ResponseHandler.responseHandler(f, flag, ftype, flength, request);
        responseHandler(f, flag, ftype, flength, request);
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
    
    /**
     * Method to handle the request and give response.
     * 
     * @param f
     *            File to handle
     * @param flag
     *            Flag to identify existence of file
     * @param ftype
     *            Type of file
     * @param flength
     *            Length of file
     * @param request
     *            Type of request
     * @return Return the constructed response
     */
    public void responseHandler(File f, int flag, String ftype, long flength, String request) {
        String response = "";
        // Check if file exist and response
        if (!f.exists()) {
            flag = Configurations.NOT_EXIST; // File not exist
            HttpResponser httpresp = new HttpResponser(flag, ftype, flength);
            response = httpresp.toString(null, f.getName());
            out.println(response);
        } else {
            flag = Configurations.EXIST; // File exist
            // Switch method to make response accordingly
            switch (request) {
            case "GET":
                try {
                    // Read file stream by bytes
                    FileInputStream fis = new FileInputStream(f);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    HttpResponser getResp = new HttpResponser(flag, ftype, flength);
                    response = getResp.toString(bytes, f.getName());
                    out.println(response);
                    out.print(new String(bytes, "UTF-8"));
                    // Print response in terminal
                    System.out.println(response);
                    fis.close();
                } catch (Exception e) {
                    e.getMessage();
                }
                break;
            case "HEAD":
                HttpResponser headResp = new HttpResponser(flag, ftype, flength);
                response = headResp.toString(null, f.getName());
                out.println(response);
                // Print response in terminal
                System.out.println(response);
                break;
            default:
                response = Configurations.CODE_NOT_IMPLEMENTED;
                // Print response in terminal
                System.out.print(response);
                out.println(response);
                break;
            }
        }
    }
}
