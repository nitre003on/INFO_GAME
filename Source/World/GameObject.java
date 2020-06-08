package Source.World;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import Source.Engine.Handler;
import Source.Engine.ID;

public abstract class GameObject 
{
  protected ID id;
  protected float velX, velY;
  public float x,y;
  public int w,h;
  public boolean dash;
  public Handler handler;
  public String imgUrl;
  public BufferedImage img;
  
  public GameObject(float x, float y, int w, int h, ID id, Handler handler) 
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.id = id;
    this.handler = handler;
  }
  public GameObject(float x, float y, ID id, Handler handler) 
  {
    this.x = x;
    this.y = y;
    this.id = id;
    this.handler = handler;
  }
  
  private BufferedImage loadImage(String path) throws IOException {
    return ImageIO.read(new FileInputStream(path));
  }

  public void drawSprite(Graphics g,int zoomLvl){
    if(img != null){
      g.drawImage(img, (int)x, (int)y,(int)(img.getWidth() * zoomLvl),(int)(img.getHeight() * zoomLvl), null);
    }else{
      try {
        img = loadImage(imgUrl);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public abstract void tick();
  public abstract void render(Graphics g);
  
  public abstract Rectangle getBounds();

  public boolean onScreen(){
    try{
      return (x - Game.player.x + w > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + h > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + w > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + h > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }

  public boolean inRoom(){
    return (inRange((int)x, Game.player.roomBounds[0], Game.player.roomBounds[0] + Game.player.roomBounds[2]) && inRange((int)y, Game.player.roomBounds[1], Game.player.roomBounds[1] + Game.player.roomBounds[3]));
  }

  public boolean inRange(int toCheck, int start, int end){ return start <= toCheck && toCheck <= end; }

  public void setX(int x) {
    this.x = x; 
  }
  
  public void setY(float y) {
    this.y = y;
  }

  public void setID(ID id) {
    this.id = id;
  }
  
  public void setVelX(int velX) {
    this.velX = velX;
  }
  
  public void setVelY(int velY) {
    this.velY = velY;
  }
  
  public float getX() {
    return x;
  }
  
  public float getY() {
    return y;
  }

  public ID getID() {
    return id;
  }
  
  public float getVelX() {
    return velX;
  }
  
  public float getVelY() {
    return velY;
  }
}

// Hier von werden alle Spielobjekte abgeleitet. Und hier werden alle Variabeln, die sie gemein haben(x,y, GeschwindigketX, GeschwindigketY, ID und Handler) festgelegt. AUch die dazu geh�rigen Methoden(get- und Set-methoden).
