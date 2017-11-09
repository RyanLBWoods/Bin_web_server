# Web Server
Simple java web server that can response to some HTTP/1.1 request
# Usage
java WebServerMain <document_root> \<port\>
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
In the WebServerMain class, the is a for loop above a comment box, uncomment it and comment the while loop below to turn the program to support multithreading.
# Logging file
The program invokes a file writing method to log message into a file as it runs. The log file will be created in src directory. Everytime server handle a request, the log file will have a new line of message.   
The message in log file is under format of "\<request_time\> \<request_type\> \<request_file\> \<protocol\> \<response_code\> \<response_time\>"
# Additional method
Support DELETE method.   
To test it, start the server first, open a new terminal window and enter "curl -s -I -X DELETE localhost:12345/\<filename\>", it should delete the request file if succeed. The response message will also be logged into log file.
