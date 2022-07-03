import java.util.HashMap;

/*
 * La idea es usar esta clase únicamente para la representación
 * como una ayuda, no como una estructura de datos
 * */
public class Anno {
	HashMap<String, Integer> meses = new HashMap<String,Integer>();
	int anno;
	
	public Anno(int _anno)
	{
		anno = _anno;
		meses.put("Enero", 31);
		if ((anno-2000)%4==0)
			meses.put("Febrero", 29);
		else
			meses.put("Febrero", 28);
		meses.put("Marzo", 31);
		meses.put("Abril", 30);
		meses.put("Mayo", 31);
		meses.put("Junio", 30);
		meses.put("Julio", 31);
		meses.put("Agosto", 31);
		meses.put("Septiembre", 30);
		meses.put("Octubre", 31);
		meses.put("Noviembre", 30);
		meses.put("Diciembre", 31);
	}
}