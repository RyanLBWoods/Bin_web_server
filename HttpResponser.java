/**
 * Class for HTTP responser.
 * 
 * @author bl41
 *
 */
public class HttpResponser {

    private String status;
    private String metadata;
    private String content;
    private String body;
    private String type;
    private String length;

    /**
     * Method to response to different HTTP requests.
     * 
     * @param flag
     *            Integer that identify existence of file
     * @param content
     *            Content of file
     * @param type
     *            Type of file
     * @param length
     *            Content length in bytes
     */
    public HttpResponser(int flag, String content, String type, long length) {
        // Construct responser
        this.metadata = "My Java Web Server\r\n";
        this.content = content;
        this.type = type;
        this.length = String.valueOf(length);
        setHeader(flag, type, length);
        setType(this.type);
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
        // Switch method to make different response accordingly
        switch (flag) {
        case Configurations.EXIST:
            this.status = "HTTP/1.1 200 OK\r\n";
            this.body = "The " + type + " page from the file in this case containing " + this.length + " bytes\r\n";
            break;
        case Configurations.NOT_EXIST:
            this.status = "HTTP/1.1 404 Not Found\r\n";
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
     */
    public void setType(String type) {
        // Set type
        switch (type) {
        case "txt":
            this.type = "text/html\r\n";
            break;
        case "html":
            this.type = "text/html\r\n";
            break;
        case "jpg":
            this.type = "jpg\r\n";
            break;
        case "jpeg":
            this.type = "jpeg\r\n";
            break;
        case "gif":
            this.type = "gif\r\n";
            break;
        case "png":
            this.type = "png\r\n";
            break;
        default:
            this.type = "unknown type\r\n";
            break;
        }
    }

    /**
     * Method override toString() method to construct response message.
     * 
     * Structure: <protocol><responseCode><cr><lf>
     * <responseText>(<metaData><contentType><contentLength> each tailing with
     * <cr><lf>) <cr><lf>(Importent!) <content>
     * 
     * @return Return constructed message
     */
    public String toString() {
        String str = "";
        String header;
        header = status + metadata + "Content-Type: " + type + "Content-Length: " + length + "\r\n";

        if (this.content.isEmpty() || this.content.equals("")) {
            str = header + this.body + "\r\n";
        } else {
            str = header + this.body + "\r\n" + content + "\r\n";
        }
        return str;
    }
}
