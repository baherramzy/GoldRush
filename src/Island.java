import java.awt.Graphics;
import java.awt.Image;

public class Island {

    int x, y;
    public int goldcount;
    Image il, ir, pot[];
    int width, height;
    boolean right = false;

    Island(int i, int j) {
        x = i;
        y = j;

        il = Pictures.island_l;
        ir = Pictures.island_r;
        
        pot = new Image[5];
        for (int k = 0; k < 5; k++) {
            pot[k] = Pictures.pot;
        }
        if (i > 400) {
            goldcount = 5000;
        } else if (i < 400) {
            goldcount = 0;
        }

        this.width = ir.getWidth(null);
        this.height = ir.getHeight(null);
    }

    public void update(GoldRushApplet app) {
        if (x < app.getWidth() / 2) {
            right = false;
        } else if (x > app.getWidth() / 2) {
            right = true;
        }
    }

    public void paint(Graphics g) {
        if (!right) {
            g.drawImage(il, x, y, null);

            if (goldcount > 0) {
                g.drawImage(pot[0], x + 15, y + 120, null);
            }
            if (goldcount > 1000) {
                g.drawImage(pot[1], x + 35, y + 130, null);
            }
            if (goldcount > 2000) {
                g.drawImage(pot[2], x + 60, y + 120, null);
            }
            if (goldcount > 3000) {
                g.drawImage(pot[3], x + 85, y + 130, null);
            }
            if (goldcount == 5000) {
                g.drawImage(pot[4], x + 115, y + 125, null);
            }
        } else {
            g.drawImage(ir, x, y, null);
            g.drawImage(pot[0], x + 15, y + 120, null);
            g.drawImage(pot[1], x + 35, y + 130, null);
            g.drawImage(pot[2], x + 60, y + 120, null);
            g.drawImage(pot[3], x + 85, y + 130, null);
            g.drawImage(pot[4], x + 115, y + 125, null);

            if (goldcount == 0) {
                pot[0] = null;
            }
            if (goldcount > 1000 && goldcount < 2000) {
                pot[1] = null;
            }
            if (goldcount > 2000 && goldcount < 3000) {
                pot[2] = null;
            }
            if (goldcount > 3000 && goldcount < 4000) {
                pot[3] = null;
            }
            if (goldcount > 4000 && goldcount < 5000) {
                pot[4] = null;
            }
        }
    }
}
