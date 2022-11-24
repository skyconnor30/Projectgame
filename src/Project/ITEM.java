package Project;

import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public class ITEM {
    public ImageIcon pic = new ImageIcon(this.getClass().getResource("item.png"));
    public int height;
    public int width;
    public int y;
    public int x;
    public int speed;
    ITEM(){
        this.height = 70;
        this.width = 70;
        this.y = 100;
        this.x = 1500;
        this.speed = 0;
    }
    public Rectangle2D getbound(){
    	return (new Rectangle2D.Double(this.x,this.y,this.width,this.height));
    }
}
