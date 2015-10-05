import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Principal implements Runnable {

	Socket clientSocket;

	public Principal(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;

	}

	@Override
	public void run() {

		System.out.println("Connection successful");
		System.out.println("Waiting for input.....");
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server: " + inputLine);
				String[] a = inputLine.split("\\*");
				float primero = Float.parseFloat(a[0]);
				float segundo = Float.parseFloat(a[1]);
				float respuesta = primero * segundo;
				out.println("" + respuesta);

				if (inputLine.equals("Bye."))
					break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();

				in.close();
				clientSocket.close();
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
