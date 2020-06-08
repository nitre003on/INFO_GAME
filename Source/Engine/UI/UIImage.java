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
import Source.World.Game;


public class UIImage extends GameObject{  // nachfragen an Piet

    private Image img;
    public int w,h;
    public Color color;

    public UIImage(int x, int y, int w, int h, ID id, String imgURL, Handler handler) { //Ein Bild, das unabhängig von der Camera benutzt werden kann
        super(x, y, w, h, id, handler);
        this.w = w;
        this.h = h;
        try {
            img = loadImg(imgURL);
          } catch (IOException e) {
            e.printStackTrace();
          }
      }

      private Image loadImg(String path) throws IOException {   //Spritesheet laden
        return ImageIO.read(new FileInputStream(path));
      }
  
      public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y);
      }
      
      public void tick(){}
      
      public void render(Graphics g){
        g.drawImage(img, (int)x, (int)y, w, h, color, null);
        if(color != null){
          g.setColor(color);
          g.fillRect((int)x, (int)y, w, h);
        }
      }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + w > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + h > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + w > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + h > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }
}

