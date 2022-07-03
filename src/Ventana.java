import java.awt.*;
import java.awt.event.*;

public class Ventana {
    
    public Ventana(){}
    public void muestra()
    {
        Frame f = new Frame();
       Button b = new Button("Púlsame!"); 
       b.setBounds(30,100,80,30);
       f.add(b);
       
       f.setTitle("Calendario");
       f.setLayout(new GridBagLayout());
       f.setVisible(true);
       f.setSize(300,300);
    }
    
    public void popUpMenu()
    {
        final Frame f= new Frame("PopupMenu Example");  
        final PopupMenu popupmenu = new PopupMenu("Edit");   
        MenuItem cut = new MenuItem("Cut");  
        cut.setActionCommand("Cut");  
        MenuItem copy = new MenuItem("Copy");  
        copy.setActionCommand("Copy");  
        MenuItem paste = new MenuItem("Paste");  
        paste.setActionCommand("Paste");      
        popupmenu.add(cut);  
        popupmenu.add(copy);  
        popupmenu.add(paste);        
        f.addMouseListener(new MouseAdapter() {  
           public void mouseClicked(MouseEvent e) {              
               popupmenu.show(f , e.getX(), e.getY());  
           }                 
        });  
        f.add(popupmenu);   
        f.setSize(400,400);  
        f.setLayout(null);  
        f.setVisible(true);  
    }
}
