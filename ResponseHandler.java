
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            ErrorResponser er = new ErrorResponser(flag, f.getName(), protocol, ftype, flength, out);
            try {
                out.write(er.errorResponser());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
//            HttpResponser httpresp = new HttpResponser(flag, protocol, ftype, errorFile.length());
//            response = httpresp.toString(null, f.getName());
//            out.write(er.errorResponser());
//            File errorFile = new File("www/Errors/404.html");
//            try {
//                FileInputStream efis = new FileInputStream(errorFile);
//                byte[] errorBytes = new byte[efis.available()];
//                efis.read(errorBytes);
//                
//                HttpResponser httpresp = new HttpResponser(flag, protocol, ftype, errorFile.length());
//                response = httpresp.toString(null, f.getName());
//                System.out.println(response);
////              out.println(response);
//                out.write(response.getBytes());
////                ErrorResponser er = new ErrorResponser(flag, out);
////                er.errorResponser();
//                out.write(errorBytes);
////                out.write(ErrorResponser.errorResponser(flag));
//                efis.close();
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                System.out.println(e.getMessage());
//            }

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
//                response = protocol + " " + Configurations.CODE_NOT_IMPLEMENTED;
//                out.println(response);
                int not_imp = Configurations.NOT_IMPLEMENTED;
                ErrorResponser er = new ErrorResponser(not_imp, f.getName(), protocol, ftype, flength, out);
                try {
                    out.write(er.errorResponser());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
//                File NI_file = new File("www/Errors/501.html");
//                try {
//                    FileInputStream NI_fis = new FileInputStream(NI_file);
//                    byte[] NI_bytes = new byte[NI_fis.available()];
//                    NI_fis.read(NI_bytes);
//                    HttpResponser notImplement = new HttpResponser(not_imp, protocol, ftype, NI_file.length());
//                    response = notImplement.toString(null, f.getName());
//                    out.write(response.getBytes());
//                    out.write(NI_bytes);
//                    NI_fis.close();
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                
//                ErrorResponser er = new ErrorResponser(not_imp, out);
//                er.errorResponser();
                // Log into log file
                LoggingFile.witeLog(" " + Configurations.CODE_NOT_IMPLEMENTED.substring(0, Configurations.CODE_NOT_IMPLEMENTED.lastIndexOf("\r")));
                // Print response in terminal
                System.out.println(response);
                break;
            }
        }
    }

}
