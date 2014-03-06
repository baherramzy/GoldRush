import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.Timer;

public class Boat implements ActionListener {

    int x, y, dy = 0, dx = 0, speed = 30, lives = 3;
    int ux, uy; // initial x and y
    int maxcapacity, currentgold; // gold carrying variables
    int unload_x, load_x; // loading/unloading x positions
    Timer timer = new Timer(3, this);
    Image img_r, img_l; // left/right boat images
    
    // for interfacing with Pictures class
    URL url;
    GoldRushApplet a;
    
    // status booleans
    boolean right = true, gameOver = false, win = false;

    Boat(int i, int j, GoldRushApplet a) {
        ux = x = i;
        uy = y = j;

        maxcapacity = 1000;
        currentgold = 0;

        this.a = a;

        img_r = Pictures.boat_r;
        img_l = Pictures.boat_l;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public void moveRight() {
        dx = speed;
        x += dx;
        right = true;
    }

    public void moveLeft() {
        dx = -speed;
        x += dx;
        right = false;
    }

    public void moveUp() {
        dy = -speed;
        y += dy;
    }

    public void moveDown() {
        dy = speed;
        y += dy;
    }

    public void update(GoldRushApplet app, Mine m) {
        if (Collision(m)) {
            app.ir.goldcount += currentgold;
            currentgold = 0;
            
            x = ux;
            y = uy;
            if (!(lives - 1 < 0)) {
                lives--;
            }
            if (lives == 0) {
                gameOver = true;
            }
        }
        
        if(a.il.goldcount == 5000 && currentgold == 0){
            win = true;
        }

        if (x + dx < 120) { // if close to left island
            x = 120;
            unload_x = x; // for unloading later
            timer.start();

        } else if (x + dx > app.ir.x - app.ir.width - 30) { // if close to right island
            x = app.ir.x - app.ir.width - 30;
            load_x = x;
            timer.start();
        }

        if (y + dy < 200) { // location of water edge
            y = 200;
        } else if (y + dy > app.getHeight() - img_r.getHeight(null)) {
            y = app.getHeight() - img_r.getHeight(null);
        }
    }

    public boolean Collision(Mine m) {
        if (right) {
            if (m.getX() <= x + img_r.getWidth(null) && m.getX() > x) { // BAHER: still not working properly
                if (m.getY() + m.getRadius() <= y + img_r.getHeight(null) && m.getY() + m.getRadius() > y) {
                    m.delete();
                    return true;
                }
            } else {
                return false;
            }
        } else { // left
            if (m.getX() + m.getRadius() <= x + img_l.getWidth(null) && m.getX() + m.getRadius() > x) { // BAHER: still not working properly
                if (m.getY() <= y + img_l.getHeight(null) && m.getY() > y) {
                    m.delete();
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void stopX() {dx = 0;}

    public void stopY() {dy = 0;}

    public void paint(Graphics g) {
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("GAME OVER", 360, 300);
        }
        
        if(win){
            g.setColor(new Color(0,100,0));
            g.drawString("YOU WIN!", 360, 300);
        }

        if (right) {
            g.drawImage(img_r, x, y, Pictures.app);
        } else {
            g.drawImage(img_l, x, y, Pictures.app);
        }

        g.setColor(Color.BLACK);
        Font f = new Font("Calibri", Font.PLAIN, 18);
        g.setFont(f);
        g.drawString("Gold onboard: " + Integer.toString(currentgold), 580, 30);
        g.drawString("Gold Remaining: " + Integer.toString(a.ir.goldcount), 580, 50);
        g.drawString("Lives: " + Integer.toString(lives), 580, 70);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x == load_x && y > a.ir.y && y < a.ir.y + 170) { // loading gold, right island
            if (currentgold + 5 <= maxcapacity && a.ir.goldcount > 0) {
                currentgold += 5;
                a.ir.goldcount -= 5;
            }
        } else if (x == unload_x && y > a.il.y && y < a.il.y + 170) { // unloading gold, left island
            if (currentgold - 5 >= 0) {
                currentgold -= 5;
                a.il.goldcount += 5;
            }
        }
    }
}
