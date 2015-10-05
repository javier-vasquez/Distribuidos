import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Division {

	public static void main(String[] args) throws IOException {
		
		
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(0);
			suscripcion(serverSocket);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10007: " + e);
			System.exit(1);
		}

		Socket clientSocket = null;
		System.out.println("Waiting for connection.....");

		while (true) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			Principal p = new Principal(clientSocket);
			Thread t = new Thread(p);
			t.start();
		}
	}

	private static void suscripcion(ServerSocket serverSocket) throws IOException {
		Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            
            echoSocket = new Socket("localhost", 10007);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost" );
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: localhost ");
            System.exit(1);
        }
        	String ip=InetAddress.getLocalHost().getHostAddress();
			out.println("server division "+ip+" "+serverSocket.getLocalPort());
			


		out.close();
		in.close();
		echoSocket.close();
		
	}

}
