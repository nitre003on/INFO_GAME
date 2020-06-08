package Source.Engine.Graphics;

import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.World.GameObject;
import java.awt.Color;
import Source.World.Game;

public class particle extends GameObject {
  
  private Vector2 velocity;
  private int lifespan, maxLifespan;
  private Color startColor, endColor;
  private particleHandler ph;
  private squareImage si;
  private int width, height;
  
  public particle(float x, float y, int w, int h, ID id, Handler handler) {
    super(x, y, w, h, id, handler);
    this.width = w;
    this.height = h;
  }
  
  public particle(float x, float y, int w, int h, int lifespan, Vector2 velocity, Color startColor,Color endColor,ID id, Handler handler, particleHandler ph) {
    super(x, y, w, h, id, handler);
    this.velocity = velocity;
    this.lifespan = lifespan;
    this.maxLifespan = lifespan;
    this.startColor = startColor;
    this.endColor = endColor;
    this.ph = ph;
    float size = (float)(Math.random() * 29 + 40);
    si = new squareImage(new Vector2(x, y), new Vector2(size, size),startColor, id, handler);
  }
  
  float lerp(float a, float b, float f)   //linear interpolation; a = min, b = max, f = %
  {
    return a + f * (b - a);
  }
  
  public void tick() {
    lifespan -= 1;
    if(lifespan < 1) ph.removeParticle(this);
    Color tempColor = new Color((int)lerp(startColor.getRed(),endColor.getRed(),(float)lifespan / maxLifespan), (int)lerp(startColor.getGreen(),endColor.getGreen(),(float)lifespan / maxLifespan), (int)lerp(startColor.getBlue(),endColor.getBlue(),(float)lifespan / maxLifespan), 255);
    si.color = tempColor;
    si.x += velocity.x;
    si.y += velocity.y;
  }
  
  public void render(Graphics g) {
    si.render(g);
  }
  
  public Rectangle getBounds() {
    return null;
  }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + width > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + height > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + width > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + height > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }  
}
