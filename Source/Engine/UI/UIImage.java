package Source.Engine.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.World.GameObject;

public class UIImage extends GameObject{

    private Image img;
    private int w,h;

    public UIImage(int x, int y, int w, int h, ID id, String imgURL, Handler handler) {
        super(x, y, id, handler);
        this.w = w;
        this.h = h;
        try {
            img = loadImage(imgURL);
          } catch (IOException e) {
            e.printStackTrace();
          }
      }

      private Image loadImage(String path) throws IOException {   //Spritesheet laden
        return ImageIO.read(new FileInputStream("INFO_GAME_NKPLP\\" + path));
      }
  
      public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y);
      }
      
      public void tick(){

      }
      
      public void render(Graphics g){
        g.drawImage(img, (int)x, (int)y, w, h, Color.white, null);
      }
}