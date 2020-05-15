

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BasicEnemy extends GameObject{
  
  boolean alive = true;
  Random random = new Random();
  
  public BasicEnemy(int x, int y, ID id, Handler handler) {
    super(x, y, id, handler);
    velX=2;                   //TODO
    velY=2;
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,32,32);                                                    //Grenzen kriegen
  }
  public void tick() {
    x+=velX;
    y+=velY;
    if(y<=0 || y>=Game.HEIGHT-32) velY *=-1;
    if(x<=0 || x>=Game.WIDTH-32) velX *=-1;
    handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.green, 32, 32, 0.08f, handler));
    collision();
  }
  public void render(Graphics g) {
    if(alive) {
      g.setColor(Color.green);
    }
    else {                                                            //arbe und gr��e setzzen
      g.setColor(Color.red);
    }
    g.fillRect((int)x, (int)y, 16, 16);
  }
  public void collision() {
    for (int i = 0; i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject.getID()==ID.Shot) {
        if(getBounds().intersects(tempObject.getBounds())) {
          //collision code
          x = random.nextInt(Game.WIDTH);                                           //Kollision mit Schuss
          y = random.nextInt(Game.HEIGHT);
          int velXr = Game.ranInt(0,1);
          if(velXr == 0) {velX*=-1;}
          int velYr = Game.ranInt(0,1);
          if(velYr == 0) {velX*=-1;}
        }
      }
      if (tempObject.getID()==ID.Wall) {
        if(getBounds().intersects(tempObject.getBounds())) {
          //collision code
          velX*=-0.5;                                                     //Kollision mit Wand
          velY*=-0.5;
        }
      }
    }
  }
}
