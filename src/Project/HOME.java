package Project;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HOME extends JPanel{
    private ImageIcon background = new ImageIcon(this.getClass().getResource("background.png"));
    private ImageIcon start = new ImageIcon(this.getClass().getResource("play.png"));
    private ImageIcon exit = new ImageIcon(this.getClass().getResource("close.png"));
    public int x = 0;
    public int y = 0;
    
    HOME(){
        setLayout(null);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getX() >= 425 && e.getX() <= 575 && e.getY() >= 225 && e.getY() <= 375){
                    x = e.getX();
                    y = e.getY();
                }
                else if(e.getX() >= 850 && e.getX() <= 940 && e.getY() >= 450 && e.getY() <= 540){
                    System.exit(0);
                }
            }
            
        });
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background.getImage(),0,0,1000,600,this);
        g.drawImage(start.getImage(),155,95,650,300,this);
        g.drawImage(exit.getImage(),575,345, 650,300 ,this);
        g.setFont(new Font("2005_iannnnnTKO",Font.CENTER_BASELINE,90));	
        g.drawString("FLYING KOALA",160,100);
    }
}