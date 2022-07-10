import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.jcraft.jsch.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class Calendario {
	String id;
	ArrayList<Evento> eventos;
	public static final String[] semana = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
	public static final String[] meses = {"","Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	
	public Calendario(String _id)
	{
		id = _id;
		eventos = new ArrayList<Evento>();
	}
	
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
	    File fp = new File(String.format("%s/%s.csv", directorio,id));
	    if (!fp.exists())
	    	return -1;
		try {
		  Scanner lector = new Scanner(fp);
		  while (lector.hasNextLine()) {
		    String data = lector.nextLine();
		    String[] partida = data.split(";");
		    String[] fecha = partida[2].split("/");
		    String[] hora = partida[3].split(":");
		    Fecha f = new Fecha(Integer.parseInt(hora[0]),Integer.parseInt(hora[1]),Integer.parseInt(fecha[0]),Integer.parseInt(fecha[1]),Integer.parseInt(fecha[2]));
		    Evento e = new Evento(partida[1], f, partida[4], Integer.parseInt(partida[0]));
			this.anadirEvento(e);
		  }
		  lector.close();
		} catch (FileNotFoundException exc) {
		  System.out.println("An error occurred.");
		  exc.printStackTrace();
		}
		return 0;
	}
	
	public int exportarCalendario(String directorio) throws IOException
	{
	    try {
	    	File dir = new File(String.format("%s", directorio));
	    	if (!dir.exists())
	    		dir.mkdir();
	    	
	        File temp = new File(String.format("%s/%s.csv", directorio,id));
	        File copia = new File(String.format("%s/%s_copia.csv", directorio,id));
	        temp.renameTo(copia);
	        File fin = new File(String.format("%s/%s.csv", directorio,id));
	        if (!fin.createNewFile()) {
	          System.out.println("El archivo ya existe, anulando la creación.");
	        }
	      } catch (IOException e) {
	        System.out.println("Error al crear el archivo");
	        e.printStackTrace();
	        return -1;
	      }
		
		try {
		      FileWriter fp = new FileWriter(String.format("%s/%s.csv", directorio,id));
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

	public static int getDiasMes(int anno, int mes)
	{
		switch (mes)
		{
			case 1:
				return 31;
			case 2:
				if ((anno-2000)%4==0)
					return 29;
				else
					return 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
			default: 
				return -1;
		}
	}
	
	//https://artofmemory.com/blog/how-to-calculate-the-day-of-the-week/
	public static int getDiaSemana(int anno, int mes, int dia)
	{
		StringBuffer temp = new StringBuffer();
		temp.append(anno);
		temp = temp.delete(0, 1);
		anno = Integer.parseInt(temp.toString());
		int y = (anno+(anno/4))%7;
		int m;
		int c=6; //1700=4, 1800=2; 1900=0; 2000=6; 2100=4; 2200=2; 2300=0;
		int l;

		if ((anno)%4==0 && anno%100!=0)
		{
			if (mes == 1 || mes == 2)
				l = 1;
			else 
				l = 0;
		}else{
			l = 0;
		}

		switch (mes)
		{
			case 1:
				m = 0;
				break;
			case 2:
				m = 3;
				break;
			case 3:
				m = 3;
				break;
			case 4:
				m = 6;
				break;
			case 5:
				m = 1;
				break;
			case 6:
				m = 4;
				break;
			case 7:
				m = 6;
				break;
			case 8:
				m = 2;
				break;
			case 9:
				m = 5;
				break;
			case 10:
				m = 0;
				break;
			case 11:
				m = 3;
				break;
			case 12:
				m = 5;
				break;
			default:
				return -1;
		}

		int res = (y + m + c + dia - l)%7;
		if (res == 0)
			return 7;
		else
			return res;

	}
	
	public static final String getFechaActual()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
   		LocalDateTime now = LocalDateTime.now();  
   		return dtf.format(now);  
	}

	public ArrayList<Evento> getEventosMes(int mes)
	{
		ArrayList <Evento> lista = new ArrayList<Evento>();
	
		if (this.eventos.isEmpty())
			return null;
		for (Evento e : this.eventos)	
		{
			if ((e.getFecha().getMes()) == mes)
				lista.add(e);
		}
		return lista;
	}

	public ArrayList<Evento> getEventosFecha(Fecha f)
	{
		ArrayList <Evento> lista = new ArrayList<Evento>();
	
		if (this.eventos.isEmpty())
			return null;
		for (Evento e : this.eventos)	
		{
			if (e.getFecha().getAnno() == f.getAnno() && e.getFecha().getMes() == f.getMes() && e.getFecha().getDia() == f.getDia())
				lista.add(e);
		}
		return lista;
	}
}