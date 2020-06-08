package Source.World.GameObjects.BulletTypes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Direction;
import Source.Engine.Handler;
import Source.Engine.ID;
import Source.World.GameObject;
import Source.World.GameObjects.BasicTrail;
import Source.World.Game;

public class Shot extends GameObject{
  
  private Direction direction;
  public boolean spell = false;
  
  
  public Shot(int x, int y, Direction direction, ID id, Handler handler) {
    super(x, y,16,16, id, handler);
    this.direction = direction;
    velX=7;
    velY=7;                                          //Schussgeschwindigkeit
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,16,16);                              //Grenzen werden hierdurch entnommen
  }
  
  public void tick() {
    collision();
    if (direction == Direction.Up) {
      //x+=velX;
      y-=velY;
    } else if (direction == Direction.Down) {
        //x+=velX;
        y+=velY;
      } else if (direction == Direction.Left) {
          x-=velX;                                                                                  // Sch�sse in die 4 M�glichen Richtungen
          //y+=velY;
        } else if (direction == Direction.Right) {
            x+=velX;
            //y+=velY;
          }
    handler.addObject(new BasicTrail((int)x+4, (int)y+4, ID.Trail, Color.blue, 8, 8, 0.08f, handler));
  }
  
  public void collision() {
    
  }
  
  public void render(Graphics g) {
  }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + w > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + h > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + w > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + h > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }
}
