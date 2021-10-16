import java.net.*;
import java.io.*;
import java.util.*;

public class HTTP
{
	private OutputStream os;
	final String CRLF = "\r\n";
	String filename;
	String statusLine;
	String headers;
	String entityBody = "<HTML>" + "<HEAD><TITLE>NOT Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";
	
	public HTTP(OutputStream o)
	{
		try {
			os = o;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String parseRequest(String message)
	{
		StringTokenizer st = new StringTokenizer(message, " ");
		if(st.nextToken().compareToIgnoreCase("GET") == 0)
		{
			filename = st.nextToken();
			filename = "." + filename;
		}
		return filename;
	}
	
	public void composeResponse(String filename, boolean fileExists)
	{
		try {
			if(fileExists == true)
			{
				System.out.println("getting " + filename);
				statusLine = "HTTP/1.0 200 OK" + CRLF;
				os.write(statusLine.getBytes());
			}else
			{
				statusLine = "HTTP/1.0 404 Not Found" + CRLF;
				os.write(statusLine.getBytes());
			}
			if(filename.endsWith(".htm") || filename.endsWith(".html"))
			{
				headers = "Content-type: text/html" +CRLF;
				os.write(headers.getBytes());
			}else if(filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
			{
				headers = "Content-type: image/jpeg" + CRLF;
				os.write(headers.getBytes());
			}else
			{
				headers = "Content-type: application/octet-stream" + CRLF;
				os.write(headers.getBytes());
			}
			
			if(fileExists == false)
			{
				os.write(CRLF.getBytes());
				os.write(entityBody.getBytes());
				os.write(CRLF.getBytes());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}