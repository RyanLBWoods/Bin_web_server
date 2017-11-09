import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class to create logging file.
 * 
 * @author bl41
 *
 */
public class LoggingFile {
    /**
     * Method to write content into logging file.
     * 
     * @param content
     *            The content to write
     */
    public static void witeLog(String content) {
        File file = new File("log.txt");
        try {
            if (!file.exists()) {
                file.createNewFile(); // Create file if it does not exist
            }
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(content.getBytes());
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
