import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ErrorResponser {

     private int flag;
     private String filename;
     private String protocol;
     private String ftype;
     private long flength;
     private PrintStream out;
//     private PrintStream out;
     private static FileInputStream fis;
     private static byte[] bytes;

     public ErrorResponser(int flag, String filename, String protocol, String ftype, long flength, PrintStream out){
         this.flag = flag;
         this.filename = filename;
         this.protocol = protocol;
         this.ftype = ftype;
         this.flength = flength;
         this.out = out;
     }

     public long getLength(){
         return flength;
     }
    public byte[] errorResponser() {
        String response = "";
        try {
            switch (flag) {
            case Configurations.NOT_EXIST:
                File error404 = new File("www/Errors/404.html");
                FileInputStream fisNE = new FileInputStream(error404);
                byte[] bytes = new byte[fisNE.available()];
                fisNE.read(bytes);

                HttpResponser nt_resp = new HttpResponser(flag, protocol, ftype, error404.length());
                response = nt_resp.toString(null, filename);
                out.write(response.getBytes());
                return bytes;
            // out.write(bytes);
            // fis.close();
            // break;
            case Configurations.NOT_IMPLEMENTED:
                File error501 = new File("www/Errors/501.html");
                FileInputStream fisNI = new FileInputStream(error501);
                byte[] bytesNI = new byte[fisNI.available()];
                fisNI.read(bytesNI);
                HttpResponser ni_resp = new HttpResponser(flag, protocol, ftype, error501.length());
                response = ni_resp.toString(null, filename);
                out.write(response.getBytes());
                // out.write(bytes);
                // fis.close();
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
