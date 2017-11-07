
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
public class RequestHandler {

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
            line = br.readLine();
            while (!line.isEmpty()) {
                recv = recv + line;
                line = br.readLine();
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
        String request = requests[0];
        // Get request file
        File f = new File("www" + requests[1]);
        String fname = f.getName();
        String ftype = fname.substring(fname.lastIndexOf(".") + 1);
        long flength = f.length();
        response = ResponseHandler.responseHandler(f, flag, ftype, flength, request);
        return response;
    }
}
