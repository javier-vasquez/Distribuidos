import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Proceso {
		private String nombre;
		private String direccion;
		private int puerto;		
	
		public Proceso(String nombre, String direccion, int puerto) {
			super();
			this.nombre = nombre;
			this.direccion = direccion;
			this.puerto = puerto;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
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
		
		
}
