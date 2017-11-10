import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Class to response error page to client.
 * 
 * @author bl41
 *
 */
public class ErrorResponser {

    private int flag;
    private String filename;
    private String protocol;
    private String ftype;
    private PrintStream out;

    /**
     * Constructor.
     * 
     * @param flag
     *            Integer to identify error type
     * @param filename
     *            Request file name
     * @param protocol
     *            Request protocol
     * @param ftype
     *            Request file type
     * @param out
     *            PrintStream
     */
    public ErrorResponser(int flag, String filename, String protocol, String ftype, PrintStream out) {
        this.flag = flag;
        this.filename = filename;
        this.protocol = protocol;
        this.ftype = ftype;
        this.out = out;
    }

    /**
     * Method to pass the error page content.
     * 
     * @return Return bytes of error page
     */
    public byte[] errorResponser() {
        String response = "";
        try {
            switch (flag) {
            case Configurations.NOT_EXIST:
                // Return not found error page
                File error404 = new File("www/Errors/404.html");
                FileInputStream fisNE = new FileInputStream(error404);
                byte[] bytes = new byte[fisNE.available()];
                fisNE.read(bytes);
                // Handle HTTP
                HttpResponser nt_resp = new HttpResponser(flag, protocol, ftype, error404.length());
                response = nt_resp.toString(null, filename);
                out.write(response.getBytes());
                return bytes;
            case Configurations.NOT_IMPLEMENTED:
                // Return not implemented error page
                File error501 = new File("www/Errors/501.html");
                FileInputStream fisNI = new FileInputStream(error501);
                byte[] bytesNI = new byte[fisNI.available()];
                fisNI.read(bytesNI);
                // Handle HTTP
                HttpResponser ni_resp = new HttpResponser(flag, protocol, ftype, error501.length());
                response = ni_resp.toString(null, filename);
                out.write(response.getBytes());
                return bytesNI;
            default:
                return null;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
