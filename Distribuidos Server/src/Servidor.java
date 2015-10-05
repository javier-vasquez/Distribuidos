import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	static List<Proceso> directorio= new ArrayList<Proceso>();
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(10007);
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
			Principal p = new Principal(clientSocket,directorio);
			Thread t = new Thread(p);
			t.start();
		}
	}
}
