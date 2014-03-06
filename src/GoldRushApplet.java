import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GoldRushApplet extends Applet implements Runnable, KeyListener, MouseListener {

    Boat player;
    Island ir, il;
    Image bg; // background image
    Mine mine[]; // array of mines
    private Image i; // temporary image loading variable
    private Graphics dg;
    boolean selection = false; // difficulty selection flag
    int diff = 0; // difficulty valye

    @Override
    public void init() {
        setSize(800, 600);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        new Pictures(this);
    }

    @Override
    public void start() {
        if (selection) {
            il = new Island(0, 250);
            ir = new Island(630, 250);

            bg = Pictures.bg;

            player = new Boat(il.x + 170, il.y + 50, this);

            switch (diff) {
                case 0:
                    break;
                case 1:
                    mine = new Mine[3];
                    break;
                case 2:
                    mine = new Mine[4];
                    break;
                case 3:
                    mine = new Mine[5];
                    break;
            }
            Random r = new Random();
            for (int j = 0; j < mine.length; j++) {
                //mine[j] = new Mine(Math.abs(170 + (r.nextInt() % (this.getWidth() - 170))), 600 - 250 + Math.abs(r.nextInt() % 250));
                mine[j] = new Mine(170 + r.nextInt(this.getWidth()-190*2), 200 + Math.abs(r.nextInt() % 250));
            }

            Thread t1 = new Thread(this);
            t1.start();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (selection) {
                il.update(this);
                ir.update(this);

                for (int j = 0; j < mine.length; j++) {
                    mine[j].update(this);
                    player.update(this, mine[j]);
                }
            }

            repaint();

            try {
                Thread.sleep(1000 / 60); // frame rate
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(Graphics g) {

        if (player.gameOver || player.win) {
            player.speed = 0;
            for(int i = 0; i < mine.length; i++){
                mine[i].dx = mine[i].dy = 0;
            }
        }
        
        if (i == null) {
            i = createImage(this.getWidth(), this.getHeight());
            dg = i.getGraphics();
        }

        dg.setColor(getForeground());
        paint(dg);

        g.drawImage(i, 0, 0, this);

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        Font f = new Font("Calibri", Font.BOLD, 32);
        g.setFont(f);
        g.setColor(Color.BLACK);
        g.drawString("WELCOME TO GOLD RUSH!", 198, 58);
        g.setColor(Color.YELLOW);
        g.drawString("WELCOME TO GOLD RUSH!", 200, 60);
        
        f = new Font("Calibri", Font.PLAIN, 26);
        g.setFont(f);
        g.setColor(Color.BLACK);
        g.drawString("The aim of the game is to get all the gold", 60, 90);
        g.drawString("from the island on the right to the island on the left.", 60, 120);
        g.drawString("Be careful not to hit with the mines!", 60, 150);
        g.drawString("Three collisions and the game ends.", 60, 180);
        
        g.drawString("EASY", 350, 250);
        g.drawString("MEDIUM", 350, 300);
        g.drawString("HARD", 350, 350);
        
        g.setColor(Color.YELLOW);
        g.drawString("EASY", 351, 251);
        //g.drawRect(340, 220, 80, 40);

        g.drawString("MEDIUM", 351, 301);
        
        //g.drawRect(340, 270, 120, 40);

        g.drawString("HARD", 351, 351);
        //g.drawRect(340, 320, 90, 40);

        if (selection) {
            g.drawImage(bg, 0, 0, null);

            il.paint(g);
            ir.paint(g);

            player.paint(g);

            for (int k = 0; k < mine.length; k++) {
                mine[k].paint(g);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;
            case KeyEvent.VK_UP:
                player.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown();
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() > 340 && e.getX() < 340 + 80) {
            if (e.getY() > 220 && e.getY() < 220 + 40) {
                diff = 1;
                selection = true;
                removeMouseListener(this);
            }
        }
        if (e.getX() > 340 && e.getX() < 340 + 120) {
            if (e.getY() > 270 && e.getY() < 270 + 40) {
                diff = 2;
                selection = true;
                removeMouseListener(this);
            }
        }
        if (e.getX() > 340 && e.getX() < 340 + 90) {
            if (e.getY() > 320 && e.getY() < 320 + 40) {
                diff = 3;
                selection = true;
                removeMouseListener(this);
            }
        }
        
        start();
    }
    
    // Unused, obligatory override methods (Listeners are abstract classes)
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
}

