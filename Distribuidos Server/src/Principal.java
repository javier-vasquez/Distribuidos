import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.Thread.State;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Principal implements Runnable {

	Socket clientSocket;
	List<Proceso> directorio;
	List<String> respuestas;
	List<Operacion> lo = null;

	public Principal(Socket clientSocket, List<Proceso> directorio) {
		super();
		this.clientSocket = clientSocket;
		this.directorio = directorio;

	}

	@Override
	public void run() {
		respuestas = new ArrayList<String>();
		System.out.println("Connection successful");
		System.out.println("Waiting .....");
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Input : " + inputLine);
				String[] a = inputLine.split(" ");
				String tipo = a[0];
				if (tipo.equalsIgnoreCase("server")) {
					String nombre = a[1];
					String direccion = a[2];
					int puerto = Integer.parseInt(a[3]);
					Proceso p = new Proceso(nombre, direccion, puerto);
					directorio.add(p);
				} else if (tipo.equalsIgnoreCase("client")) {
					List<Thread> lt = new ArrayList<Thread>();
					lo = new ArrayList<Operacion>();
					for (int i = 1; i < a.length; i++) {
						String nombre = a[i];
						

						if (nombre.equalsIgnoreCase("historial")) {

							List<Operacion> op = new ArrayList<Operacion>();
							op = leer();
							String s = "HISTORIAL::::::";

							for (Operacion o : op) {

								s += "------- Operacion: " + o.getOperacion() + " Respuesta: " + o.getRespuesta();
							}

							out.println(s);
							continue;
						} else {
							i++;
							String operacion = a[i];
							for (Proceso p : directorio) {
								if (p.getNombre().equalsIgnoreCase(nombre)) {

									Operacion o = new Operacion(p.getDireccion(), p.getPuerto(), operacion);
									lo.add(o);
									Thread t1 = new Thread(o);
									t1.start();
									lt.add(t1);
									break;
									
								}
							}
							serializar();
						}
					}
					int i = 0;
					for (Thread t : lt) {
						t.join();
						// out.println(((Operacion)((Object)(t.getContextClassLoader()))).getRespuesta());

						out.println(lo.get(i).getRespuesta());
						i++;
					}

				}

				if (inputLine.equals("Bye."))
					break;
			}
		} catch (IOException | InterruptedException e) {
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

	private void serializar() {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(clientSocket.getInetAddress().getHostAddress() + ".bin");
			out = new ObjectOutputStream(fos);

			for (Operacion o : lo) {
				out.writeObject(o);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private List<Operacion> leer() {

		FileInputStream fis = null;
		ObjectInputStream in = null;
		List<Operacion> operaciones = new ArrayList<Operacion>();
		try {

			fis = new FileInputStream(clientSocket.getInetAddress().getHostAddress() + ".bin");
			in = new ObjectInputStream(fis);

			// Leer el objeto del fichero (en el mismo orden !!)
			while (true) {
				try {
					Operacion ope = (Operacion) in.readObject();

					operaciones.add(ope);
				} catch (EOFException e) {
					// If there are no more objects to read, return what we
					// have.
					return operaciones;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return operaciones;
				}

			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			// Close the stream.
			try {
				in.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return operaciones;

	}

}
