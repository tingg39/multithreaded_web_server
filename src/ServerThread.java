import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread
{
	private Socket sock;
	private BufferedReader readSock;
	public static boolean fileExists;
	private FileInputStream fis;
	public OutputStream os;
	
	public ServerThread (Socket clientSock)
	{
		try
		{
			sock = clientSock;
			readSock = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			os = sock.getOutputStream();
			
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			fileExists = true;
			HTTP http = new HTTP(os);
			String inLine = readSock.readLine();
			String filename = http.parseRequest(inLine);
			try
			{
				fis = new FileInputStream(filename);
			}catch(FileNotFoundException ex)
			{
				fileExists = false;
				System.out.println(filename + " does not exist");
			}
			
			http.composeResponse(filename, fileExists);
			if(fileExists == true)
			{
				os.write(http.CRLF.getBytes());
				byte[] buffer = new byte[1024];
				int bread;
				while((bread = fis.read(buffer)) != -1)
				{
					os.write(buffer,0,bread);
				}
				os.write(http.CRLF.getBytes());
			}
			System.out.println("HTTP GET Successful");
			sock.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}