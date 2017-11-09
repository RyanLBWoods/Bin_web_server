import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to create logging file.
 * 
 * @author bl41
 *
 */
public class LoggingFile {

    private String date;
    private String content;

    /**
     * Constructor.
     * @param content Logging file content including header and request file content
     */
    public LoggingFile(String content) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        this.date = format.format(now);
        this.content = content;
    }

    /**
     * Method to create a log file.
     */
    public void createFile() {
        String log = this.date + "\r\n" + this.content;
        File file = new File("log.txt");
        try {
            if (!file.exists()) {
                file.createNewFile(); // Create file if it does not exist
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(log.getBytes());
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
