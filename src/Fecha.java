public class Fecha {
	int hora,minuto;
	int dia,mes,ano;
	
	public Fecha(int _dia,int _mes,int _ano)
	{
		dia = _dia; mes = _mes; ano = _ano;
		hora = 0;
		minuto = 0;
	}
	
	public Fecha(int _hora, int _minuto, int _dia,int _mes,int _ano)
	{
		dia = _dia; mes = _mes; ano = _ano;
		hora = _hora; minuto = _minuto;
	}
	
	public void setFecha(int _dia, int _mes, int _ano){dia = _dia; mes = _mes; ano = _ano;}
	
	public void setHora(int _hora, int _minuto){hora = _hora; minuto = _minuto;}
	
	public int getAnno(){return ano;}
	public int getMes(){return mes;}
	public int getDia(){return dia;}
	
	public String getFecha(){return String.format("%d/%d/%d",dia,mes,ano);}
	
	public String getHora(){return String.format("%d:%d", hora, minuto);}
}