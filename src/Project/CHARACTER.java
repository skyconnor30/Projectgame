package Project;

import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

abstract class GraphicObject{
    abstract void run();
    abstract void jump();
    abstract void slide();
    abstract Rectangle2D getbound();
}

public class CHARACTER extends GraphicObject {
    public ImageIcon[] runpic = new ImageIcon[12];
    public ImageIcon[] slidepic = new ImageIcon[6];
    public ImageIcon[] jumppic = new ImageIcon[6];
    public ImageIcon[] fallpic = new ImageIcon[6];
    public int height;
    public int width;
    public int y;
    public int x;
    public int status;
    CHARACTER(){
        for (int i = 0; i < 12; i++) {
            String name;
            if (i < 10) {
                name = "0_Cute_Koala_Run_00"+i+".png";
            }
            else{
                name = "0_Cute_Koala_Run_0"+i+".png";
            }
            runpic[i] = new ImageIcon(this.getClass().getResource(name));
        }
        for (int i = 0; i < 6; i++) {
            String name = "0_Cute_Koala_Slide_00"+i+".png";
            slidepic[i] = new ImageIcon(this.getClass().getResource(name));
        }
        for (int i = 0; i < 6; i++) {
            String name = "0_Cute_Koala_Jump_00"+i+".png";
            jumppic[i] = new ImageIcon(this.getClass().getResource(name));
        }
        for (int i = 0; i < 6; i++) {
            String name = "0_Cute_Koala_Fall_00"+i+".png";
            fallpic[i] = new ImageIcon(this.getClass().getResource(name));
        }
        this.height = 160;
        this.width = 120;
        this.status = 0;
    }
    public void slide(){
        this.status = 2;
        this.height = 120;
        this.width = 160;
    }
    public void run(){
        this.status = 0;
        this.height = 160;
        this.width = 120;
    }
    public void jump(){
        this.status = 1;
        this.height = 160;
        this.width = 120;
    }
    public Rectangle2D getbound(){
        if (this.status == 0 || this.status == 1) {
            return (new Rectangle2D.Double(this.x,this.y,this.width-10,this.height));
        }
        else {
            return (new Rectangle2D.Double(this.x,this.y,this.width-30,this.height));
        }
    }
}
