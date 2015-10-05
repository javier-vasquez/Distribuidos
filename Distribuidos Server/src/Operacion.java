import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class Operacion implements Runnable, Serializable{

	private String direccion;
	private int puerto;
	private String respuesta;
	private String operacion;
	
	
	
	public Operacion(String direccion, int puerto, String operacion) {
		super();
		this.direccion = direccion;
		this.puerto = puerto;
		this.operacion = operacion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getPuerto() {
		return puerto;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	@Override
	public void run() {
		Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            
            echoSocket = new Socket(direccion, puerto);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + direccion);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + direccion +":"+e );
            System.exit(1);
        }

			out.println(operacion);
			String respuesta="";
			try {
				respuesta = "Respuesta de "+operacion+": "+in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Respuesta de "+direccion+":"+puerto+": " + respuesta);

		out.close();
		try {
			in.close();
			echoSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.respuesta=respuesta;
		
	}
	
	
	
}
