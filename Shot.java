package actualGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Shot extends GameObject{
  
  private Direction direction;
  
  public Shot(int x, int y, Direction direction, ID id, Handler handler) {
    super(x, y, id, handler);
    this.direction = direction;
    velX=7;
    velY=7;                                               //Schussgeschwindigkeit
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,16,16);                              //Grenzen werden hierdurch entnommen
  }
  
  public void tick() {
    
    if (direction == Direction.Up) {
      //x+=velX;
      y-=velY;
    } else if (direction == Direction.Down) {
      //x+=velX;
      y+=velY;
    } else if (direction == Direction.Left) {
      x-=velX;                                                                                  // Schüsse in die 4 Möglichen Richtungen
      //y+=velY;
    } else if (direction == Direction.Right) {
      x+=velX;
      //y+=velY;
    }
    collision();
    handler.addObject(new BasicTrail((int)x+4, (int)y+4, ID.Trail, Color.blue, 8, 8, 0.08f, handler));
  }
  
  public void collision() {
    for (int i = 0; i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject.getID()==ID.Wall) {
        if(getBounds().intersects(tempObject.getBounds())) {
          velY = 0;
          velX = 0;
          handler.removeObject(this);
          //handler.addObject(new Shot((int)x-16, (int)y-16, direction, ID.Shot, handler));
        }
      }
    }
  }
  
  public void render(Graphics g) {
    g.setColor(Color.blue);
    g.fillRect((int)x, (int)y, 16, 16);                                                                   //Darstellung
  }
}
