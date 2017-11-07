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
     *            Integer that identify existence for file
     * @param content
     *            Content of file
     * @param type
     *            Type of content
     * @param length
     *            Content Length in bytes
     */
    public HttpResponser(int flag, String content, String type, long length) {
        // Construct responser
        this.metadata = "My Java Web Server\r\n";
        this.content = content;
        this.type = type;
        this.length = String.valueOf(length);
        // Switch method to make different answer accordingly
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
        // Set type
        if (this.type.equals("txt") || this.type.equals("html")) {
            this.type = "text/html\r\n";
        }
    }

    /**
     * Method override toString() method to construct response message.
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
