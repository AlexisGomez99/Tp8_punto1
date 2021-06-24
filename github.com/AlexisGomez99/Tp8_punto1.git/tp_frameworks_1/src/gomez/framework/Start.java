package gomez.framework;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import gomez.utilizacion.Accion;

public class Start {

	private Map<Integer, Accion> acciones = new HashMap<Integer, Accion>();
	private Accion accion;
	public Start(String path) {
		Properties prop= new Properties();
		try (InputStream configFile = getClass().getResourceAsStream(path);){
			prop.load(configFile);
			String valor= prop.getProperty("acciones");
			String[] valores =  valor.split(";");
			Class<?> clase;
			for(int i=0;i<valores.length;i++) {
				clase= Class.forName(valores[i]);
				acciones.put(i+1,(Accion) clase.getDeclaredConstructor().newInstance());
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
		
	}
	

	public void desplegarMenu() {
		
		try(Scanner sn= new Scanner(System.in)){
			boolean salir= false;
			int opcion;
			while(!salir) {
				System.out.println("\nElija una opcion para ejecutar una accion.");
				acciones.forEach((k,v) -> System.out.println(k + "."+ v.nombreItemMenu()+" = "+v.descripcionItemMenu()));
				System.out.println("0. Para salir \n\nIngrese una opcion:\n");
				opcion=sn.nextInt();
				
				if(opcion==0) {
					System.out.println("Fin.\n");
					salir=true;
				}
				else 
					if(acciones.containsKey(opcion)) {
						accion= acciones.get(opcion);
						imprimir();
					}
					else {
						System.out.println("No existe esa opcion...");
					}			
			}
		}
		
	}
	
	private void imprimir() {
		accion.ejecutar();
	}
	
	
}
	
