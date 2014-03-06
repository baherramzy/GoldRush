import java.awt.Image;
import java.net.URL;

public class Pictures {
    
    static Image island_l,island_r,boat_r,boat_l,pot,bg,mine;
    URL url;
    static GoldRushApplet app;
    
    public Pictures(GoldRushApplet app) {
        try {
            url = app.getCodeBase();
        } catch (Exception e){
            System.out.println("EXCEPTION!");
        }
        
        island_l = app.getImage(url, "pngs/island_l.png");
        island_r = app.getImage(url, "pngs/island_r.png");
        mine = app.getImage(url, "pngs/mine.png");
        boat_r = app.getImage(url, "pngs/boat_r.png");
        boat_l = app.getImage(url, "pngs/boat_l.png");
        pot = app.getImage(url, "pngs/gold.png");
        bg = app.getImage(url, "pngs/bg.png");
        this.app = app;
    }
}
