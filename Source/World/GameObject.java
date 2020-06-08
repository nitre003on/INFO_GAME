package Source.World;

import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.World.Game;

public abstract class GameObject 
{
  protected ID id;
  protected float velX, velY;
  public float x,y;
  public int w,h;
  public boolean dash;
  public Handler handler;
  
  
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
  
  public abstract void tick();
  public abstract void render(Graphics g);
  
  public abstract Rectangle getBounds();

  public abstract boolean onScreen();

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
