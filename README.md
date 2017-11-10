# Web Server
Simple java web server that can response to some HTTP/1.1 request
# Usage
java WebServerMain <document_root> \<port\>
# Basic
All basic function are implemented. Program is able to get and response to GET and HEAD request. When test the server with browsers, it will response an OK message and browser will open the request file if exists. If the request is invalid (inexistent file or unsupported request type), it will response an error message and browser will show an html page telling the error.
# Attempted extension
Reurning of binary images (GIF, JPEG and PNG)   
Multithreading   
Logging   
Additional method
# Returning binary images
The program is able to return binary file as file reading method implemented is reading file through byte. 
The request images file byte stream will be given to client and client will create a new file in current directory using the same name simulating the download process.   
To test this, start the server first ,open any browser and type in "localhost:12345/\<filename\>". It should be able to show the image on the browser and download it to src directory as well.
# Multithreading
Program used newFixedThreadPool to support multiple concurrent client with a specified limit. the limit was set to be 10. In the WebServerMain class, there is a while loop above a comment box, uncomment it and comment the while loop below to turn the program to support multithreading.
# Logging file
The program invokes a file writing method to log message into a file as it runs. The log file will be created in src directory. Everytime server handle a request, the log file will have a new line of message.   
The message in log file is under format of "\<request_time\> \<request_type\> \<request_file\> \<protocol\> \<response_code\> \<response_time\>"
# Additional method
Support DELETE method.   
To test it, start the server first, open a new terminal window and enter "curl -s -I -X DELETE localhost:12345/\<filename\>", it should delete the request file if succeed. The response message will also be logged into log file.
