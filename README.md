# Web_server
Simple java web server that can response to some HTTP/1.1 request
# Usage
java WebServerMain <document_root> \<port\>
# Attempted extension
Reurning of binary images (GIF, JPEG and PNG)   
Multithreading   
Logging
# Returning binary images
The program is able to return binary file as file reading method implemented is reading file through byte.   
The request images file byte stream will be given to client and client will create a new 
file in current directory using the same name simulating the download process.   
To test this, start the server first ,open any browser and type in "localhost:12345/\<filename\>"
# Multithreading
In the WebServerMain class, the is a for loop beneath a comment box, uncomment it and comment the socket accept code above 
to turn the program to support multithreading.
# Logging file
The program invokes a file writing method to log message into a file as it runs. The log file will be created in src directory.
