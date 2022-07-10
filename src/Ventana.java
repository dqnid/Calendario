import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;  

import java.io.*;

public class Ventana {
    JFrame f;
    JPanel p, t, principal;
    public Ventana(){}

    public void muestra()
    {
        GridBagConstraints ajustesPrincipal; 
        String temp;
        String[] hoy;
        Calendario c;
        f=new JFrame();

        f.setSize(700,350);
        f.setTitle("Calendario");  
        /* Registro de eventos de ratón en el frame */
        f.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {            
               //editMenu.show(f, e.getX(), e.getY());
        }});              
   
        //f.setIconImage(image);

        principal=new JPanel(new GridBagLayout());
        principal.setBackground(new Color(16,16,16,255));
        principal.setOpaque(true);
        ajustesPrincipal = new GridBagConstraints();  

        p=new JPanel(new GridBagLayout());
        p.setLayout(new GridBagLayout());  
        p.setBackground(new Color(16,16,16,255));
        ajustesPrincipal.fill = GridBagConstraints.HORIZONTAL;
        ajustesPrincipal.gridx = 0;
        ajustesPrincipal.gridy = 0;
        principal.add(p, ajustesPrincipal);
        
        t=new JPanel(new GridBagLayout());
        t.setLayout(new GridBagLayout());  
        t.setBackground(new Color(16,16,16,255));
        ajustesPrincipal.gridx = 0;
        ajustesPrincipal.gridy = 1; 
        principal.add(t, ajustesPrincipal);

        f.add(principal,BorderLayout.CENTER); //PAGE_START, LINE_END, etc.


        temp = Calendario.getFechaActual();  
        hoy = temp.split("/");
        c = new Calendario("main"); 
        c.importarCalendario("/home/danih/.config/calendario");
        generaMes(Integer.parseInt(hoy[0]), Integer.parseInt(hoy[1]), c);
        
        f.setVisible(true);  
        //f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);  
        try {
            c.exportarCalendario("/home/danih/.config/calendario");
        }catch (IOException excepcion)
        {  
	        excepcion.printStackTrace();
        }
        
    }
   
    public void generaMes(int anno, int mes, Calendario c)
    {
        GridBagConstraints gbc; 
        int i,j,z, ndias;
        JButton b;
        JButton[] bDias = new JButton[40];
        JLabel l;
        String temp;
        String[] hoy;

        p.removeAll();
        t.removeAll();
        f.revalidate();
        f.repaint();

        temp = Calendario.getFechaActual();  
        hoy = temp.split("/");

        gbc = new GridBagConstraints();  

        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;  
        gbc.gridy = 0;  
        b = new JButton("*");
        p.add(b, gbc);  
        gbc.gridx = 3;  
        gbc.gridy = 0;  
        b = new JButton("<");
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (mes == 1)
                    generaMes(anno-1, 12, c);
                else 
                    generaMes(anno, mes-1, c);
            }
        });
        p.add(b, gbc);  
        gbc.anchor = GridBagConstraints.PAGE_START;  
        gbc.gridx = 4;  
        gbc.gridy = 0;  
        l = new JLabel(Calendario.meses[mes]);
        p.add(l, gbc); 
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 5;  
        gbc.gridy = 0;  
        b = new JButton(">");
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (mes == 12)
                    generaMes(anno+1, 1, c);
                else 
                    generaMes(anno, mes+1, c);
            }
        });
        p.add(b, gbc);  
        gbc.gridx = 7;  
        gbc.gridy = 0;  
        l = new JLabel(String.format("%d",anno));
        p.add(l, gbc);  
       
        ndias = Calendario.getDiasMes(anno,mes);
        i = Calendario.getDiaSemana(anno, mes, 1);
        z=0;
        for (j=0; j<7;j++)
        {
            gbc.gridx = j+1;  
            gbc.gridy = z;  
            b = new JButton(String.format("%s", Calendario.semana[j]));
            t.add(b, gbc);  
        }
        z++;
        for (j=1; j<=ndias; j++)
        {
            gbc.gridx = i;  
            gbc.gridy = z;  
            bDias[j] = new JButton(String.format("%d", j));
            bDias[j].setActionCommand(String.format("%d",j));
            bDias[j].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    MenuDia(new Fecha(Integer.parseInt(e.getActionCommand()),mes,anno),c);
                }
            });
            if (Integer.parseInt(hoy[2]) == j && mes == (Integer.parseInt(hoy[1])))
                bDias[j].setBackground(Color.red);
            t.add(bDias[j], gbc);  
            
            if (i == 7){
                z++;
                i=1;
            }else{
                i++;
            }
        }
        
        if (c.getEventosMes(mes) != null)
        {
            for (Evento iter : c.getEventosMes(mes))
            {
               bDias[iter.getFecha().getDia()].setBackground(Color.BLACK);
            }
        }
        
        f.setVisible(true);
    }
    
    public void MenuDia(Fecha fecha, Calendario c)
    {
        JLabel dia = new JLabel(fecha.getFecha());
        GridBagConstraints gbc; 
        p.removeAll();
        t.removeAll();
        f.revalidate();
        f.repaint(); 
        
        t.add(dia);  

        f.repaint(); 
        f.setVisible(true);
    }
}
