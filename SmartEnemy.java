

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class SmartEnemy extends GameObject{
  
  Random random = new Random();
  
  private GameObject player;
  
  public SmartEnemy(int x, int y, ID id, Handler handler) {
    super(x, y, id, handler);
    for (int i = 0; i < handler.objects.size(); i++) {
      if(handler.objects.get(i).getID()==ID.Player) player = handler.objects.get(i);
    }
    velX=4;
    velY=4;
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,32,32);                                                      //Grenzen werden entnommen
  }
  public void tick() {
    x+=velX;
    y+=velY;
    float diffX= x-player.getX() -8;
    float diffY= y-player.getY() -8;
    float distance = (float) Math.sqrt(((x-player.getX())*(x-player.getX()))+((y-player.getY())*(y-player.getY())));
    velX = (float) ((-1/distance)*diffX);
    velY = (float) ((-1/distance)*diffY);                                                                       //Normales Verhalten(Hier: Wird dem Spieler gefolgt)
    if(y<=0 || y>=Game.HEIGHT-32) velY *=-1;
    if(x<=0 || x>=Game.WIDTH-32) velX *=-1;
    handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.orange, 32, 32, 0.08f, handler));
    collision();
  }
  public void collision() {
    for (int i = 0; i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject.getID()==ID.Shot) {
        if(getBounds().intersects(tempObject.getBounds())) {
          //collision code
          x = random.nextInt(Game.WIDTH);
          y = random.nextInt(Game.HEIGHT);                                                          //Kollision mit Schuss
          int velXr = Game.ranInt(0,1);
          if(velXr == 0) {velX*=-1;}
          int velYr = Game.ranInt(0,1);
          if(velYr == 0) {velX*=-1;}
        }
      }
      if (tempObject.getID()==ID.Wall) {
        if(getBounds().intersects(tempObject.getBounds())) {
          //collision code                                                                    //Kollision mit Wand
          velX*=-0.5;
          velY*=-0.5;
        }
      }
    }
  }
  public void render(Graphics g) {
    g.setColor(Color.orange);
    g.fillRect((int)x, (int)y, 32, 32);                                                       //grafische Darstellung
  }
}
