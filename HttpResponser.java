/**
 * Class for HTTP responser.
 * 
 * @author bl41
 *
 */
public class HttpResponser {

    private String status;
    private String protocol;
    private String metadata;
    private String body;
    private String type;
    private String length;
    private int format;

    /**
     * Method to response to different HTTP requests.
     * 
     * @param flag
     *            Integer that identify existence of file
     * @param protocol
     *            Protocol code get from request
     * @param type
     *            Type of file
     * @param length
     *            Content length in bytes
     */
    public HttpResponser(int flag, String protocol, String type, long length) {
        // Construct responser
        this.protocol = protocol;
        this.metadata = "My Java Web Server\r\n";
        setType(type);
        this.length = String.valueOf(length);
        setHeader(flag, type, length);
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
                    + " page\r\n";
            break;
        default:
            System.out.println("Default output");
            break;
        }
    }

    /**
     * Method override toString() method to construct response message.
     * 
     * <protocol><responseCode><cr><lf>
     * <metaData><contentType><contentLength>(each tailing with<cr><lf>)
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
        header = protocol + " " + status + metadata + "Content-Type: " + this.type + "Content-Length: " + this.length
                + "\r\n";
        // If content is not null
        if (bytes != null) {
            switch (format) {
            case Configurations.BINARY_FORMAT:
                str = header + this.body + "\r\n";
                // Create new file if request is about getting binary file
                ImageWriter.writeImage(filename, bytes);
                break;
            case Configurations.TXT_FORMAT:
                str = header + this.body + "\r\n";
                break;
            default:
                System.out.println("Unknown type");
                break;
            }
        } else {
            str = header + this.body + "\r\n";
        }
        // Write response status to log file
        LoggingFile.witeLog(" " + this.status.substring(0, this.status.lastIndexOf("\r")));
        return str;
    }
}
