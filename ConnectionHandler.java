import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Method for request handler.
 * 
 * @author bl41
 *
 */
public class ConnectionHandler {

    private Socket conn;
    private InputStream ins;
    private OutputStream os;
    private BufferedReader br;

    /**
     * Method that handle the request.
     * 
     * @param conn
     *            Client socket
     */
    public ConnectionHandler(Socket conn) {
        this.conn = conn;
        try {
            ins = conn.getInputStream();
            os = conn.getOutputStream();
            br = new BufferedReader(new InputStreamReader(ins));
        } catch (IOException e) {
            System.out.println("ConnectionHandler: " + e.getMessage());
        }
    }
}
