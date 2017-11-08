import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class for writing binary image file.
 * 
 * @author bl41
 *
 */
public class ImageWriter {
    /**
     * Method to write binary content to new image file.
     * 
     * @param filename
     *            Name of file to transfer
     * @param bytes
     *            File content indicated in bytes
     */
    public static void writeImage(String filename, byte[] bytes) {
        // Get exact same name of file being transfered
        String name = filename.substring(filename.lastIndexOf("/") + 1);
        File file = new File(name);
        try {
            if (!file.exists()) {
                file.createNewFile(); // Create file if it does not exist
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
