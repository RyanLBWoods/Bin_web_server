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
		this.metadata = "My Java Web Server\n";
		this.content = content;
		this.type = type;
		this.length = String.valueOf(length);
		// Switch method to make different answer accordingly
		switch (flag) {
		case Configurations.EXIST:
			this.status = "HTTP/1.1 200 OK\n";
			this.body = "The" + type + "page from the file in this case containing" + this.length + "bytes";
			break;
		case Configurations.NOT_EXIST:
			this.status = "HTTP/1.1 404 Not Found\n";
			this.body = "response message in this case containing " + length + "bytes of error message as an " + type
					+ "page";
			break;
		default:
			this.status = "HTTP/1.1 501 Not Implemented";
			this.body = "response message in this case containing " + length + "bytes of error message as an " + type
					+ "page";
			break;
		}
		// Set type
		if (this.type.equals("txt") || this.type.equals("html")) {
			this.type = "text/html\n";
		}
	}

	/**
	 * Method override toString() method to construct response message.
	 */
	public String toString() {
		String str = "";
		String header;
		header = status + metadata + "Content-Type: " + type + "Content-Length: " + length + "\n";

		if (this.content.isEmpty() || this.content.equals("")) {
			str = header + this.body + "\r\n";
		} else {
			str = header + this.body + "\n" + content + "\r\n";
		}
		return str;
	}
}
