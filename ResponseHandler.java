
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
            // Handle error
            ErrorResponser er = new ErrorResponser(flag, f.getName(), protocol, ftype, out);
            try {
                out.write(er.errorResponser());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

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
                out.println(response);
                System.out.println(response);
                if (f.delete()) {
                    System.out.println("File deleted");
                    out.println("File deleted");
                } else {
                    out.println("File delete fail");
                    System.out.println("File delete fail");
                }
                break;

            default:
                // Unsupported request type
                int not_imp = Configurations.NOT_IMPLEMENTED;
                ErrorResponser er = new ErrorResponser(not_imp, f.getName(), protocol, ftype, out);
                try {
                    out.write(er.errorResponser());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // Print response in terminal
                System.out.println(response);
                break;
            }
        }
    }

}
