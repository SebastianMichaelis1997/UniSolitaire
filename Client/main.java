import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		try {
			System.out.println("connect");
			Socket client = new Socket("localhost", 1345);
			System.out.println("connected");
			Scanner     in  = new Scanner( client.getInputStream() );
		    PrintWriter out = new PrintWriter( client.getOutputStream(), true);
		    
		    
		    if(args.length<1 || args[0].equals("seed")) {
			    System.out.println("SendData");
			    out.println("seed");
			    System.out.println("Data1 sent");
			    out.println("0");
		    }
		    else
		    {
			    System.out.println("SendData");
			    out.println("register");
			    System.out.println("Data1 sent");
			    out.println("Bjarne");	
		    }
		    
		    System.out.println(in.nextLine());
		    System.out.println("finish");
		    
		    client.close();
			
		} catch (UnknownHostException e) {
			System.out.println("Host not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		
	}

}
