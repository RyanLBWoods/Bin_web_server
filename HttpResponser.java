

public class HttpResponser {

	private String status;
	private String metadata;
	private String content;
	private String body;
	private String type;
	private String length;
	
	public HttpResponser(int flag, String content, String type, long length){
		this.metadata = "My Java Web Server\n";
		this.content = content;
		this.type = type;
		this.length = String.valueOf(length);
		
		switch(flag){
		case 1:
			this.status = "HTTP/1.1 200 OK\n";
			this.body = "The" + type + "page from the file in this case containing" + this.length + "bytes";
			break;
		case -1:
			this.status = "HTTP/1.1 404 Not Found\n";
			this.body = "response message in this case containing " + length + "bytes of error message as an " + type + "page";
		}
		
		if(this.type.equals("txt") || this.type.equals("html")){
			this.type = "text/html\n";
		}
	}
	
	public String toString(){
		String str = "";
		String header;
		header = status + metadata + "Content-Type: " + type + "Content-Length: " + length + "\n";
		
		if(!content.isEmpty() || content != null){
			str = header + this.body + "\n" + content + "\r\n";
		} else {
			str = header + this.body + "\r\n";
		}
		return str;
	}
}
