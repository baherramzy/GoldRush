import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

public class Mine implements ActionListener {

    private int x = 0;  // x-position
    private int y = 0;  // y-position
    private int radius = 12; // pixels
    public int dx = 1; // delta x per frame
    public int dy = 1; // delta y per frame
    
    Image img = Pictures.mine; // load image
    Timer t = new Timer(200, this);

    Mine(int i, int j) { // initialize position
        x = i;
        y = j;

        t.start();
    }

    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getRadius() { return radius; }

    public void update(GoldRushApplet app) {
        if (x + dx > app.getWidth() - 230 || x + dx < 200) {
            dx = -dx;
        } else {
            x += dx;
        }
        
        if (y + dy > app.getHeight() - 30|| y + dy < 200) {
            dy = -dy;
        } else {
            y += dy;
        }
    }

    public void delete() {
        x = 1000;
        y = 1000;
        dx = 0;
        dy = 0;
    }

    public void paint(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random r = new Random();

        int d = r.nextInt() % 5;
        switch (d) {
            case 0:
                break;
            case 1:
                // move right
                if (dx < 0) {
                    dx = -dx; // if moving left, move right
                }
                break;
            case 2:
                //move left
                if (dx > 0) {
                    dx = -dx; // if moving right, move left
                }
                break;
            case 3:
                // move up
                if (dy > 0) {
                    dy = -dy; // if moving down, move up
                }
                break;
            case 4:
                // move down
                if (dy < 0) {
                    dy = -dy; // if moving up, move down
                }
                break;
        }
    }
}