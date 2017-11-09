
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class that handle the request.
 * 
 * @author bl41
 *
 */
public class SingleRequestHandler {

    private static InputStream ins;
    private static OutputStream os;
    private static BufferedReader br;
    private static PrintWriter out;

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
            // PrintWriter out = new PrintWriter(os, true);
            ins = socket.getInputStream();
            os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(ins));
            out = new PrintWriter(os, true);
            String recv = "";
            String line = "";
            // Get request
            line = br.readLine();
            if (!line.isEmpty()) {
                LoggingFile.witeLog(line);
                recv = recv + line;
            }
            // if (line == null || line.equals("null")) {
            // throw new Exception("... client has closed the connection ... ");
            // }
            // while (!line.isEmpty()) {
            // recv = recv + line;
            // line = br.readLine();
            // }
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

    /**
     * Method to get response.
     * 
     * @param path
     *            File path
     * @param requests
     *            An array of string that contains the constructed request
     */
    public static void getResponse(String path, String[] requests) {
        // String response = "";
        int flag = 0; // flag to identify existence of file
        String request = requests[0];
        String protocol = requests[2];
        // Get request file
        File f = new File(path + requests[1]);
        String fname = f.getName();
        String ftype = fname.substring(fname.lastIndexOf(".") + 1);
        long flength = f.length();
        // response = ResponseHandler.responseHandler(f, flag, ftype, flength,
        // request);
        ResponseHandler.responseHandler(f, flag, ftype, flength, request, protocol, out);
        // responseHandler(f, flag, ftype, flength, request);
    }
}
