package Project;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class PLAYGAME extends JPanel implements ActionListener {

    private ImageIcon[] background = new ImageIcon[3];
    private ImageIcon pause = new ImageIcon(this.getClass().getResource("pause.png"));
    private ImageIcon play = new ImageIcon(this.getClass().getResource("play.png"));
    private ImageIcon close = new ImageIcon(this.getClass().getResource("close.png"));
    public int random = 0;
    public int score = 0;
    private int jumph = 300;
    private int high = 0;
    private int stj = 0;
    private boolean playpause = true;
    public CHARACTER charac = new CHARACTER();
    public ENEMY enemy = new ENEMY();
    public ENEMY skyenemy = new ENEMY();
    private ITEM item = new ITEM();
    public int globalspeed = 10;
    public boolean alive = true;
    Timer time = new Timer(100, this);
    int x = 0, x1 = 1000;

    Thread stage = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    if (x <= -1000) {
                        x = getWidth();
                    } else {
                        x -= 1;
                    }
                    if (x1 <= -1000) {
                        x1 = getWidth();
                    } else {
                        x1 -= 1;
                    }
                    repaint();
                    Thread.sleep(3);
                } catch (Exception e) {
                }   
            }
        }
    });

    Thread showitem = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    if (score % 750 <= 5 && score >= 100) {
                        int x = (int) (Math.random() * 10) % 10;
                        if (x >= 8) {
                            item.speed = 0;
                        } else {
                            item.speed = globalspeed;
                        }
                    }
                    if (item.x <= 5) {
                        item.x = getWidth();
                    } else {
                        item.x -= item.speed;
                    }
                    if (Intersect(charac.getbound(), item.getbound())) {
                        score += (int) (Math.random() * 1000) % 500;
                        item.x = getWidth();
                        item.speed = 0;
                    } else if (item.x <= 5) {
                        item.speed = 0;
                    }
                    Thread.sleep(20);
                } catch (Exception e) {
                }
            }
        }
    });

    private int crun = 0;
    private int runc = 0;
    private int cjump = 0;
    private int jumpc = 0;
    private int cslide = 0;
    private int slidec = 0;
    private int cfall = 0;
    private int fallc = 0;
    Thread actor = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    if (charac.status == 0) {
                        crun += 1;
                        runc = crun % 12;
                        Thread.sleep(20);
                    }
                    if (charac.status == 1 && stj == 1) {
                        high += 15;
                        if (high >= jumph) {
                            stj = 0;
                        }
                        if (cjump < 6) {
                            cjump += 1;
                            jumpc = cjump % 6;
                        } else {
                            jumpc = 5;
                        }
                        Thread.sleep(20);
                    } else if (charac.status == 1 && stj == 0) {
                        high -= 15;
                        if (high <= 0) {
                            charac.run();
                            jumpc = 0;
                            cjump = 0;
                            high = 0;
                        }
                        cfall += 1;
                        fallc = cfall % 6;
                        Thread.sleep(40);
                    }
                    if (charac.status == 2) {
                        cslide += 1;
                        slidec = cslide % 6;
                        Thread.sleep(20);
                    }

                } catch (Exception e) {
                }
                repaint();
            }
        }
    });
    private int enwalkc = 0;
    private int enpic = 0;
    Thread enm = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    enwalkc++;
                    enpic = enwalkc % 12;
                    enemy.enland();
                    if (score == 0) {
                        enemy.x = getWidth();
                        enemy.baseyland();
                    } else if (score <= 10) {
                        enemy.x = getWidth();
                        enemy.baseyland();
                    }
                    if (enemy.x == getWidth()) {
                        int x = (int) (Math.random() * 10) % 10;
                        if (x >= 4) {
                            enemy.x = getWidth() - 1;
                            enemy.speed = globalspeed;
                        } else {
                            enemy.speed = 0;
                            if (score != 0) {
                                Thread.sleep(1000);
                            }
                        }
                    } else if (enemy.x <= 10) {
                        enemy.x = getWidth();
                        cout++;
                    } else {
                        enemy.x -= enemy.speed;
                    }
                    if (Intersect(charac.getbound(), enemy.getbound())) {
                        enemy.x = getWidth();
                        time.stop();
                        alive = false;
                    }
                    Thread.sleep(25);
                } catch (Exception e) {
                }
                repaint();
            }
        }
    });

    private int enskyc = 0;
    private int skypic = 0;
    Thread ensky = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    enskyc++;
                    if (enskyc % 2 == 0) {
                        skypic++;
                        if (skypic == 5) {
                            skypic = 0;
                        }
                    }
                    skyenemy.ensky();
                    if (score == 0) {
                        skyenemy.speed = 0;
                        skyenemy.x = getWidth();
                        skyenemy.baseysky();
                    }
                    if (score < 500) {
                        skyenemy.speed = 0;
                        skyenemy.x = getWidth();
                        skyenemy.baseysky();
                    }
                    if (skyenemy.x - enemy.x < 400 && skyenemy.x == getWidth() ) {
                            if (score > 500) {
                                skyenemy.x = getWidth();
                                continue;
                            }
                    }
                    else if (skyenemy.x == getWidth()) {
                        int x = (int) (Math.random() * 10) % 10;
                        if (x >= 6) {
                            skyenemy.x = getWidth() - 1;
                            skyenemy.y = 280 - ((int) (Math.random() * 100) % 280);
                            skyenemy.speed = globalspeed;
                        } else {
                            skyenemy.speed = 0;
                            if (score != 0) {
                                Thread.sleep(1000);
                            }
                        }
                    } else if (skyenemy.x <= 10) {
                        skyenemy.x = getWidth();
                    } else {
                        skyenemy.x -= skyenemy.speed;
                    }
                    if (enemy.x == getWidth()-1 && enemy.x - skyenemy.x < 300 && skyenemy.x >=10 && skyenemy.x <= getWidth()-1) {
                        enemy.x = getWidth();
                    }
                    if (Intersect(charac.getbound(), skyenemy.getbound())) {
                        skyenemy.x = getWidth();
                        time.stop();
                        alive = false;
                    }
                    Thread.sleep(25);
                } catch (Exception e) {
                }
                repaint();
            }
        }
    });
    public int cout = 0;

    PLAYGAME() {
        this.setFocusable(true);
        this.setLayout(null);
        for (int i = 0; i < 3; i++) {
            String name = "state" + (int) (i + 1) + ".png";
            background[i] = new ImageIcon(this.getClass().getResource(name));
        }
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (charac.status == 0) {
                        charac.jump();
                        stj = 1;
                        repaint();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    charac.slide();
                    high = 0;
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    score+=200;
                    globalspeed++;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    charac.run();
                    repaint();
                }
            }

        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getX() >= 920 && e.getX() <= 970 && e.getY() >= 20 && e.getY() <= 60) {
                    if (playpause == true) {
                        playpause = false;
                        time.stop();
                        stage.suspend();
                        actor.suspend();
                        enm.suspend();
                        ensky.suspend();
                        showitem.suspend();
                        repaint();
                    } else {
                        time.start();
                        playpause = true;
                        stage.resume();
                        actor.resume();
                        ensky.resume();
                        enm.resume();
                        showitem.resume();
                        repaint();
                    }
                }
                if (e.getX() >= 920 && e.getX() <= 970 && e.getY() >= 80 && e.getY() <= 130) {
                    System.exit(0);
                }
            }
        });
        this.score = 0;
        enemy.x = getWidth();
        skyenemy.x = getWidth();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background[random].getImage(), x, 0, 1000, 600, this);
        g.drawImage(background[random].getImage(), x1, 0, 1000, 600, this);
        g.setFont(new Font("2005_iannnnnTKO", Font.CENTER_BASELINE, 60));
        g.drawString("SCORE : " + this.score, 50, 50);
        if (playpause == true) {
            g.drawImage(pause.getImage(), 835, 10, 200, 100, this);
        } else if (playpause == false) {
            g.drawImage(play.getImage(), 835, 5, 200, 100, this);
        }
        if (charac.status == 0) {
            g.drawImage(charac.runpic[runc].getImage(), 50, 400, charac.width, charac.height, this);
            charac.x = 50;
            charac.y = 400;
        } else if (charac.status == 1) {
            if (stj == 1) {
                g.drawImage(charac.jumppic[jumpc].getImage(), 50, 400 - this.high, charac.width, charac.height, this);
            } else if (stj == 0) {
                g.drawImage(charac.fallpic[fallc].getImage(), 50, 400 - this.high, charac.width, charac.height, this);
            }
            charac.x = 50;
            charac.y = 400 - this.high;
        } else if (charac.status == 2) {
            g.drawImage(charac.slidepic[slidec].getImage(), 50, 440, charac.width, charac.height, this);
            charac.x = 50;
            charac.y = 440;
        }
        g.drawImage(enemy.land[enpic].getImage(), enemy.x, enemy.y, enemy.width, enemy.height, this);
        g.drawImage(skyenemy.sky[skypic].getImage(), skyenemy.x, skyenemy.y, skyenemy.width, skyenemy.height, this);
        g.drawImage(item.pic.getImage(), item.x, item.y, item.width, item.height, this);
        if (playpause == false) {
            g.drawImage(close.getImage(), 835, 60, 200, 100, this);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        score += 1;
        if (this.score % 200 == 0 && score >= 100) {
            this.random = (int) (Math.random() * 100) % 3;
        }
        if (this.score % 300 == 0) {
            this.globalspeed += 1;
        }
    }

    public void start() {
        this.time.start();
        stage.start();
        enm.start();
        ensky.start();
        showitem.start();
        actor.start();
    }

    public void stop() {
        this.time.stop();
    }

    public boolean Intersect(Rectangle2D a, Rectangle2D b) {
        return (a.intersects(b));
    }
}
