public class Evento {
	String nombre, comentario;
	Fecha fecha;
	int importancia;
	
	public Evento(String _nombre, Fecha _fecha, String _comentario, int _importancia)
	{
		nombre = _nombre;
		fecha = _fecha;
		comentario = _comentario;
		importancia = _importancia;
	}
	
	public void setComentario(String _comentario){comentario = _comentario;}
	
	public void setNombre(String _nombre){nombre = _nombre;}
	
	public void setFecha(Fecha _fecha){fecha = _fecha;}
	
	public String getComentario(){return comentario;}
	
	public String getNombre(){return nombre;}
	
	public Fecha getFecha(){return fecha;}
	
	public int getImportancia(){return importancia;}	
}