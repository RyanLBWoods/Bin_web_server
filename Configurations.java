/**
 * Class for defining configurations.
 * 
 * @author bl41
 *
 */
public class Configurations {
    /**
     * Specific port.
     */
    public static final int PORT = 12345;
    /**
     * File exist.
     */
    public static final int EXIST = 1;
    /**
     * File not exist.
     */
    public static final int NOT_EXIST = -1;
    /**
     * ACK byte to client.
     */
    public static final byte ACK = 1;
    /**
     * TXT format file.
     */
    public static final int TXT_FORMAT = 1;
    /**
     * Binary format file (JPEG, PNG and GIF).
     */
    public static final int BINARY_FORMAT = 0;
    /**
     * Unknown format file.
     */
    public static final int UNKNOWN = -1;
    /**
     * Response code of everything work.
     */
    public static final String CODE_OK = "HTTP/1.1 200 OK\r\n";
    /**
     * Response code of resource not found.
     */
    public static final String CODE_NOT_FOUND = "HTTP/1.1 404 Not Found\r\n";
    /**
     * Response code of not implemented request type.
     */
    public static final String CODE_NOT_IMPLEMENTED = "HTTP/1.1 501 Not Implemented";
}
