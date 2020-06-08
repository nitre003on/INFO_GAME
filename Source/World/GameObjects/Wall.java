package Source.World.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.World.GameObject;
import Source.World.Game;

public class Wall extends GameObject{

  int width, height;
  public Wall(float x, float y, ID id, Handler handler, int width, int height) {
    super(x, y, width, height, id, handler);
    this.width = width;
    this.height = height;
  }
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,width,height);                         //Grenzen werden HierarchyBoundsAdapter entnommen
  }
  public void tick() {
    //collision();
  }
  public void render(Graphics g) {
    g.setColor(Color.darkGray);
    g.fillRect((int)x, (int)y, width, height);                                    //Graphische Darstellung
  }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + width > 0 - Game.ScreenWidth / 2 || x - Game.player.x < 0 + Game.ScreenWidth / 2 || y - Game.player.y + height > 0 - Game.ScreenHeight / 2 || y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + width > 0 - Game.ScreenWidth / 2 || x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 || y - Game.ScreenHeight / 2 + height > 0 - Game.ScreenHeight / 2 || y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  } 
}
