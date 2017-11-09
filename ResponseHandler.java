
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

/**
 * Class to handle the response.
 * 
 * @author bl41
 *
 */
public class ResponseHandler {
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
     * @param protocol
     *            Protocol code get from request
     * @param out
     *            PrintWriter
     */
    public static void responseHandler(File f, int flag, String ftype, long flength, String request, String protocol,
            PrintStream out) {
        String response = "";
        // Check if file exist and response
        if (!f.exists()) {
            flag = Configurations.NOT_EXIST; // File not exist
            HttpResponser httpresp = new HttpResponser(flag, protocol, ftype, flength);
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
                    HttpResponser getResp = new HttpResponser(flag, protocol, ftype, flength);
                    response = getResp.toString(bytes, f.getName());
//                    out.println(response);
//                    out.print(new String(bytes, "UTF-8"));
                    out.write(response.getBytes());
                    out.write(bytes);
                    // Print response in terminal
                    System.out.println(response);
                    fis.close();
                } catch (Exception e) {
                    e.getMessage();
                }
                break;
            case "HEAD":
                HttpResponser headResp = new HttpResponser(flag, protocol, ftype, flength);
                response = headResp.toString(null, f.getName());
                out.println(response);
                // Print response in terminal
                System.out.println(response);
                break;
            case "DELETE":
                HttpResponser deleteResp = new HttpResponser(flag, protocol, ftype, flength);
                response = deleteResp.toString(null, f.getName());
                f.delete();
                break;
            default:
                // Unsupported request type
                response = protocol + " " + Configurations.CODE_NOT_IMPLEMENTED;
                out.println(response);
                LoggingFile.witeLog(" " + Configurations.CODE_NOT_IMPLEMENTED);
                // Print response in terminal
                System.out.println(response);
                break;
            }
        }
    }

}
