package Source.Engine.Graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.World.Game;
import Source.World.GameObject;
import java.awt.Color;

public class Image extends GameObject{
  private BufferedImage img;
  private int borderW, borderH;
  private float zoomLvl;
  public static enum displayTypes{normal,stretched,tiled};
  public Color color;
  private displayTypes displayType;
  private int width, height;
  
  public Image(String imgURL, Vector2 pos, Vector2 size, float zoomLvl, displayTypes displayType, ID id, Handler handler){   //frag Piet bei nachfragen
    super(pos.x, pos.y,(int)size.y,(int)size.x, id, handler);
    this.displayType = displayType;
    this.zoomLvl = zoomLvl;
    this.borderH = (int)size.x;
    this.borderW = (int)size.y;
    try { img = loadImage(imgURL); } 
    catch (IOException e) { e.printStackTrace(); }
    color = Color.WHITE;
    this.width = (int)size.y;
    this.height = (int)size.x;
  }
  
  private BufferedImage loadImage(String path) throws IOException {
    return ImageIO.read(new FileInputStream(path));
  }

  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,16,16);                              //Grenzen werden hierdurch entnommen
  }
  public void tick(){}
  
  public void render(Graphics g){
    switch(displayType){
      case normal :   //Bild wird normal gezeichnet (originale Größe)
        g.drawImage(img, (int)x, (int)y,(int)(img.getWidth() * zoomLvl),(int)(img.getHeight() * zoomLvl),color, null);
        break;
      case stretched : //Bild wird gezeichnet, ohne die originale Größe zu beachten
        g.drawImage(img, (int)x, (int)y, borderW, borderH, null);
      case tiled :    //Bild wird wiederholend gezeichnet um einen Bereich auszufüllen (kein clipping!!)
        for(int i = 0; i < Math.floor(borderH / (img.getHeight() * zoomLvl)+1);i++){
          for(int j = 0; j < Math.floor(borderW / (img.getWidth() * zoomLvl)+1);j++){
            if((int)x + j * img.getWidth() * zoomLvl - Game.player.x + img.getWidth() * zoomLvl > 0 - Game.ScreenWidth / 2 && (int)x + j * img.getWidth() * zoomLvl - Game.player.x < 0 + Game.ScreenWidth / 2 && (int)y  + i * img.getHeight() * zoomLvl - Game.player.y + img.getHeight() * zoomLvl > 0 - Game.ScreenHeight / 2 && (int)y  + i * img.getHeight() * zoomLvl - Game.player.y < 0 + Game.ScreenHeight / 2)
              g.drawImage(img, (int)x + j * (int)(img.getWidth() * zoomLvl),(int)y  + i * (int)(img.getHeight() * zoomLvl),(int)(img.getWidth() * zoomLvl),(int)(img.getHeight() * zoomLvl) ,color, null);
          }
        }
        if(Game.debug){
          g.setColor(Color.RED);
          g.drawRect((int)x, (int)y, borderW, borderH);
        }
    }
  }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + width > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + height > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + width > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + height > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }
}

