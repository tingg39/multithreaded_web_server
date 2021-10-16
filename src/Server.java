import java.net.*;

public class Server
{
	
	public static void main(String argv[])
	{
		Server server = new Server();
		System.out.println("Server is running now");
		server.run();
	}
	
	public void run()
	{
		try {
			int portNum = 5520;
			ServerSocket servSock = new ServerSocket(portNum);
			while(true)
			{
				Socket sock = servSock.accept();
				ServerThread servThread = new ServerThread(sock);
				servThread.start();
			}
		}catch (Exception ex)
		{
			ex.printStackTrace();
			
		}
	}
	
}