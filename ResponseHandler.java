import java.io.File;
import java.io.FileInputStream;

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
     * @return Return the constructed response
     */
    public static String responseHandler(File f, int flag, String ftype, long flength, String request) {
        String response = "";
        // Check if file exist and response
        if (!f.exists()) {
            flag = Configurations.NOT_EXIST; // File not exist
            HttpResponser httpresp = new HttpResponser(flag, null, ftype, flength);
            response = httpresp.toString(null, f.getName());
        } else {
            flag = Configurations.EXIST; // File exist
            // Switch method to make response accordingly
            switch (request) {
            case "GET":
                try {
                    // Read file by bytes
                    FileInputStream fis = new FileInputStream(f);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    HttpResponser getResp = new HttpResponser(flag, bytes, ftype, flength);
                    response = getResp.toString(bytes, f.getName());
                    System.out.println(response);
                    fis.close();
                } catch (Exception e) {
                    e.getMessage();
                }
                break;
            case "HEAD":
                HttpResponser headResp = new HttpResponser(flag, null, ftype, flength);
                response = headResp.toString(null, f.getName());
                System.out.println(response);
                break;
            default:
                response = Configurations.CODE_NOT_IMPLEMENTED;
                System.out.println(response);
                break;
            }
        }
        return response;
    }

}
