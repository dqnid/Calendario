import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {
        Calendario c;
        String dir, nombre;
        if (args.length == 2)
        {
            dir=args[1];
            nombre=args[0];
        }else{
            nombre="main";
            dir="/home/danih/.config/calendario";
        }
        c = new Calendario(nombre,dir); 
        c.importarCalendario(dir);
        Ventana v = new Ventana();
        v.muestraCalendario(c);
        try {
            c.exportarCalendario(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
