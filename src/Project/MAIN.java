package Project;

import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class MAIN extends JFrame implements ActionListener{
    public HOME home = new HOME();
    public PLAYGAME letplay = new PLAYGAME();
    public GAMEOVER over = new GAMEOVER();
    public int letplaystatus = 0;
    Timer time = new Timer(100,this); 
    MAIN(){
        ImageIcon icon = new ImageIcon(this.getClass().getResource("koala.png"));
        this.setIconImage(icon.getImage());
        add(home);
        time.start();
    }   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (home.x >= 425 && home.x <= 575 && home.y >= 225 && home.y <= 375){
            home.x = 0;
            home.y = 0;
            this.remove(home);
            this.add(letplay);
            letplay.requestFocusInWindow();
            letplay.start();
        }
        if (over.x >= 425 && over.x <= 575 && over.y >= 225 && over.y <= 375){
            over.x = 0;
            over.y = 0;
            this.remove(over);
            letplay = new PLAYGAME();
            this.add(letplay);
            letplay.requestFocusInWindow();
            letplay.start();
        }
        if (letplay.alive == false) {
            over.last = letplay.score;
            this.remove(letplay);
            this.add(over);
            over.requestFocusInWindow();
        }
        this.validate();
        this.repaint();
    }
    public static void main(String[] args) throws IOException {
        JFrame jframe = new MAIN();
        jframe.setSize(1000,600);
        jframe.setTitle("FLYING KOALA");
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);
    }
}
