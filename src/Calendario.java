import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.jcraft.jsch.*;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Calendario {
	String id;
	ArrayList<Evento> eventos;
	enum semana{Lunes,Martes,Miércoles,Jueves,Viernes,Sábado,Domingo}; //estoy asumiendo que va como en C, veremos si no me llevo un susto.
	
	public void anadirEvento(Evento e)
	{
		eventos.add(e);
	}
	
	public void eliminarEvento(Evento e)
	{	
		eventos.remove(eventos.indexOf(e));
	}
	
	public int importarCalendario(String directorio)
	{
		    String data;
		    String[] partida;
		    String[] fecha;
		    String[] hora;
	    	File fp = new File(String.format("%s/%s", directorio,id));
	    	if (!fp.exists())
	    		return -1;
		  try {
		      Scanner lector = new Scanner(fp);
		      while (lector.hasNextLine()) {
		        data = lector.nextLine();
		        partida = data.split(";");
		        fecha = partida[2].split("/");
		        hora = partida[3].split(":");
		        Fecha f = new Fecha(Integer.parseInt(hora[0]),Integer.parseInt(hora[1]),Integer.parseInt(hora[2]),Integer.parseInt(fecha[0]),Integer.parseInt(fecha[1]));
		        Evento e = new Evento(partida[1], f, partida[4], Integer.parseInt(partida[0]));
		        eventos.add(e);
		      }
		      lector.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return 0;
	}
	
	public int exportarCalendario(String directorio) throws IOException
	{
	    try {
	    	File dir = new File(String.format("%s", directorio));
	    	if (!dir.exists())
	    		dir.mkdir();
	    	
	        File temp = new File(String.format("%s/%s", directorio,id));
	        File copia = new File(String.format("%s/%s_copia", directorio,id));
	        temp.renameTo(copia);
	        File fin = new File(String.format("%s/%s", directorio,id));
	        if (!fin.createNewFile()) {
	          System.out.println("El archivo ya existe, anulando la creación.");
	        }
	      } catch (IOException e) {
	        System.out.println("Error al crear el archivo");
	        e.printStackTrace();
	        return -1;
	      }
		
		try {
		      FileWriter fp = new FileWriter(String.format("%s/%s", directorio,id));
		      for (Evento e : eventos) {
		    	  fp.write(String.format("%d;%s;%s;%s;%s\n",e.getImportancia(), e.getNombre(), e.getFecha().getFecha(),e.getFecha().getHora(), e.getComentario()));
		      }
		      fp.close();
		} catch (FileNotFoundException e) {
		      System.out.println("Error al exportar.");
		      e.printStackTrace();
		      return -2;
		}
		return 0;
	}

	/*
	 * Por hacer
	 * */
	public int importarSSH()
	{
		  String user = "john";
	        String password = "mypassword";
	        String host = "192.168.100.23";
	        int port = 22;
	        String remoteFile = "/home/john/test.txt";

	        try {
	            JSch jsch = new JSch();
	            Session session = jsch.getSession(user, host, port);
	            session.setPassword(password);
	            session.setConfig("StrictHostKeyChecking", "no");
	            System.out.println("Establishing Connection...");
	            session.connect();
	            System.out.println("Connection established.");
	            System.out.println("Crating SFTP Channel.");
	            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
	            sftpChannel.connect();
	            System.out.println("SFTP Channel created.");

	            InputStream inputStream = sftpChannel.get(remoteFile);

	            try (Scanner scanner = new Scanner(new InputStreamReader(inputStream))) {
	                while (scanner.hasNextLine()) {
	                    String line = scanner.nextLine();
	                    System.out.println(line);
	                }
	            }
	        } catch (JSchException | SftpException e) {
	            e.printStackTrace();
	        }
			return 0;
	}
}