import java.io.UnsupportedEncodingException;

/**
 * Class for HTTP responser.
 * 
 * @author bl41
 *
 */
public class HttpResponser {

    private String status;
    private String metadata;
    private String txtContent;
    private byte[] binaryContent;
    private String body;
    private String type;
    private String length;
    private int format;

    /**
     * Method to response to different HTTP requests.
     * 
     * @param flag
     *            Integer that identify existence of file
     * @param bytes
     *            File content indicated in bytes
     * @param type
     *            Type of file
     * @param length
     *            Content length in bytes
     */
    public HttpResponser(int flag, byte[] bytes, String type, long length) {
        // Construct responser
        this.metadata = "My Java Web Server\r\n";
        setType(type);
        this.length = String.valueOf(length);
        setHeader(flag, type, length);
        setContent(type, bytes);
    }

    /**
     * Method to set type message.
     * 
     * @param type
     *            File type
     */
    public void setType(String type) {
        switch (type) {
        case "txt":
            this.format = Configurations.TXT_FORMAT;
            this.type = "text/html\r\n";
            break;
        case "html":
            this.format = Configurations.TXT_FORMAT;
            this.type = "text/html\r\n";
            break;
        case "jpg":
            this.format = Configurations.BINARY_FORMAT;
            this.type = "image/jpg\r\n";
            break;
        case "jpeg":
            this.format = Configurations.BINARY_FORMAT;
            this.type = "image/jpeg\r\n";
            break;
        case "gif":
            this.format = Configurations.BINARY_FORMAT;
            this.type = "image/gif\r\n";
            break;
        case "png":
            this.format = Configurations.BINARY_FORMAT;
            this.type = "image/png\r\n";
            break;
        default:
            this.format = Configurations.UNKNOWN;
            this.type = "unknown type\r\n";
            break;
        }
    }

    /**
     * Method to set header context.
     * 
     * @param flag
     *            Integer that identify existence of file
     * @param type
     *            Type of file
     * @param length
     *            Content length in bytes
     */
    public void setHeader(int flag, String type, long length) {
        switch (flag) {
        case Configurations.EXIST:
            this.status = Configurations.CODE_OK;
            this.body = "The " + type + " page from the file in this case containing " + this.length + " bytes\r\n";
            break;
        case Configurations.NOT_EXIST:
            this.status = Configurations.CODE_NOT_FOUND;
            this.body = "response message in this case containing " + length + " bytes of error message as an " + type
                    + "page\r\n";
            break;
        default:
            System.out.println("Default output");
            break;
        }
    }

    /**
     * Method to set type context.
     * 
     * @param type
     *            Type of file
     * @param bytes
     *            File content indicated in bytes
     */
    public void setContent(String type, byte[] bytes) {
        if (bytes != null) {
            switch (type) {
            case "txt":
                try {
                    this.txtContent = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "html":
                try {
                    this.txtContent = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "jpg":
                this.binaryContent = bytes;
                break;
            case "jpeg":
                this.binaryContent = bytes;
                break;
            case "gif":
                this.binaryContent = bytes;
                break;
            case "png":
                this.binaryContent = bytes;
                break;
            default:
                break;
            }
        }

    }

    /**
     * Method override toString() method to construct response message.
     * 
     * <protocol><responseCode><cr><lf>
     * <metaData><contentType><contentLength> each tailing with<cr><lf>)
     * <cr><lf>(Importent!)
     * <content>
     * 
     * @param bytes
     *            File content indicated in bytes
     * @param filename
     *            Name of requested file
     * @return Return constructed message
     */
    public String toString(byte[] bytes, String filename) {
        String str = "";
        String header;
        // Set header message
        header = status + metadata + "Content-Type: " + this.type + "Content-Length: " + this.length + "\r\n";
        // If content is not null
        if (bytes != null) {
            switch (format) {
            case Configurations.BINARY_FORMAT:
                str = header + this.body + "\r\n" + binaryContent + "\r\n";
                // Create new file if request is about getting binary file
                ImageWriter.writeImage(filename, bytes);
                break;
            case Configurations.TXT_FORMAT:
                str = header + this.body + "\r\n" + txtContent + "\r\n";
                break;
            default:
                System.out.println("Unknown type");
                break;
            }
        } else {
            str = header + this.body + "\r\n";
        }

        return str;
    }
}
