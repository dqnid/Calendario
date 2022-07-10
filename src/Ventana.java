import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;  

public class Ventana {
    JFrame f;
    JPanel p, t, principal;
    public Ventana(){
        f=new JFrame();

        f.setSize(700,350);
        f.setTitle("Calendario");  
        /* Registro de eventos de ratón en el frame */
        f.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {            
               //editMenu.show(f, e.getX(), e.getY());
        }});              
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
        //f.setIconImage(image); 
    }

    public void muestra()
    {
        GridBagConstraints ajustesPrincipal; 
        String temp;
        String[] hoy;
        Calendario c;

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
        //c.exportarCalendario("/home/danih/.config/calendario");
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
        GridBagConstraints gbc = new GridBagConstraints(); 
        JButton b;
        int contador;

        p.removeAll();
        t.removeAll();
        f.revalidate();
        f.repaint(); 
         
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.insets = new Insets(5,5,5,5);

        b = new JButton("X");
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                generaMes(fecha.getAnno(), fecha.getMes(), c);
            }
        });
        gbc.gridx = 0;  
        gbc.gridwidth = 1;
        gbc.gridy = 0;  
        t.add(b,gbc);

        gbc.gridx = 1;  
        gbc.gridwidth = 1;
        gbc.gridy = 0;  
        t.add(dia,gbc);


        contador = 1;
        if (c.getEventosFecha(fecha) != null)
        {
            for (Evento iter : c.getEventosFecha(fecha))
            {
                b = new JButton(String.format("%s - %s", iter.getNombre(), iter.getFecha().getHora()));
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                gbc.gridy = contador;
                t.add(b,gbc);
                contador++;
            }
        }
        
        b = new JButton(String.format("Añadir evento (+)"));
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                MenuAnadirEvento(fecha, c);
            }
        });
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = contador;
        t.add(b,gbc);

        f.repaint(); 
        f.setVisible(true);
    }
    
    public void MenuAnadirEvento(Fecha fecha, Calendario c)
    {
        JTextField tN,tH,tM,tC;
        JLabel txt;
        JComboBox<String> imp;
        JButton bt,bc;
        GridBagConstraints gbc = new GridBagConstraints();

        p.removeAll();
        t.removeAll();
        f.revalidate();
        f.repaint(); 

        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;  
        gbc.gridy = 0;  
        gbc.gridwidth = 1;

        bc = new JButton("X");
        bc.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                MenuDia(fecha,c);
            }
        });
        p.add(bc,gbc);

        gbc.gridx = 1;  
        gbc.gridy = 0;  
        gbc.gridwidth = 2;
        txt = new JLabel(fecha.getFecha());
        p.add(txt,gbc);
        
        gbc.gridx = 0;  
        gbc.gridy = 1;  
        gbc.gridwidth = 3;
        tN = new JTextField("Nombre");
        t.add(tN,gbc);

        gbc.gridx = 0;  
        gbc.gridy = 2;  
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        tH = new JTextField("0");
        t.add(tH,gbc);
        gbc.gridx = 1;  
        gbc.gridy = 2;  
        gbc.gridwidth = 1;
        txt = new JLabel(":");
        t.add(txt,gbc);
        gbc.gridx = 2;  
        gbc.gridy = 2;  
        gbc.gridwidth = 1;
        tM = new JTextField("0");
        t.add(tM,gbc);

        gbc.gridx = 0;  
        gbc.gridy = 3;  
        gbc.gridwidth = 3;
        imp = new JComboBox<String>(new String[]{"1","2","3","4","5"});
        t.add(imp,gbc);
        
        gbc.gridx = 0;  
        gbc.gridy = 4;  
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        tC = new JTextField("Comentario");
        t.add(tC,gbc);
        
        gbc.gridx = 0;  
        gbc.gridy = 5;  
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        bt = new JButton("Añadir evento");
        bt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fecha.setHora(Integer.parseInt(tH.getText()),Integer.parseInt(tM.getText()));
                c.anadirEvento(new Evento(tN.getText(), fecha, tC.getText(), Integer.parseInt(imp.getSelectedItem().toString())));
                try {
                    c.exportarCalendario("/home/danih/.config/calendario");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                MenuDia(fecha,c);
            }
        });
        t.add(bt,gbc);

        f.repaint(); 
        f.setVisible(true);
    }

    public void MenuEditarEvento()
    {

    }
}
