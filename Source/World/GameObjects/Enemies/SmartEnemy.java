package Source.World.GameObjects.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.World.Game;
import Source.World.GameObject;
import Source.World.GameObjects.BasicTrail;
import Source.World.GameObjects.Door;

public class SmartEnemy extends GameObject{
  
  Random random = new Random();
  
  public static int SmartEnemySize = 32;
  
  private GameObject player;
  
  Rectangle hitBox;
  
  public SmartEnemy(int x, int y, ID id, Handler handler) {
    super(x, y,SmartEnemySize,SmartEnemySize, id, handler);
    for (int i = 0; i < handler.objects.size(); i++) {
      if(handler.objects.get(i).getID()==ID.Player) player = handler.objects.get(i);
    }
    velX=4;
    velY=4;
    hitBox = new Rectangle(x, y, SmartEnemySize, SmartEnemySize);
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,SmartEnemySize,SmartEnemySize);                                                      //Grenzen werden entnommen
  }
  public void tick() {
    hitBox.x = (int)x; 
    hitBox.y = (int)y; 
    collision();
    x+=velX;
    y+=velY;
    float diffX= x-player.getX() -8;
    float diffY= y-player.getY() -8;
    float distance = (float) Math.sqrt(((x-player.getX())*(x-player.getX()))+((y-player.getY())*(y-player.getY())));
    velX = (float) ((-1/distance)*diffX);
    velY = (float) ((-1/distance)*diffY);                                                                       //Normales Verhalten(Hier: Wird dem Spieler gefolgt)
    if(y<=0 || y>=Game.HEIGHT-SmartEnemySize) velY *=-1;
    if(x<=0 || x>=Game.WIDTH-SmartEnemySize) velX *=-1;
    handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.orange, SmartEnemySize, SmartEnemySize, 0.08f, handler));
  }
  public void collision() {
    hitBox.x += velX; 
    for (int i = 0;i < handler.objects.size();i++) {         
      GameObject tempObject = handler.objects.get(i); 
      if(tempObject.getID()==ID.Wall || tempObject.getID()==ID.Door){ 
        if (hitBox.intersects(tempObject.getBounds())){ 
          hitBox.x -= velX; 
          while (!hitBox.intersects(tempObject.getBounds())){ 
            hitBox.x += Math.signum(velX); 
          } 
          hitBox.x -= Math.signum(velX); 
          velX = 0; 
          x = hitBox.x; 
        } 
      }
      if(tempObject.getID()==ID.Shot){ 
        if (hitBox.intersects(tempObject.getBounds())){ 
          hitBox.x -= velX; 
          while (!hitBox.intersects(tempObject.getBounds())){ 
            hitBox.x += Math.signum(velX); 
          } 
          hitBox.x -= Math.signum(velX); 
          //collision code
          Game.handler.removeEnemy(this);
          for (int e = 0; e < Game.handler.objects.size(); e++) {
            GameObject tempObject2 = Game.handler.objects.get(e);
            if (tempObject2 instanceof Door) {
              Door tempDoor = (Door)tempObject2;
              tempDoor.checkIfOpen();
            }
          }
        } 
      } 
    } 
     
    hitBox.y += velY; 
    for (int i = 0;i < handler.objects.size();i++) {         
      GameObject tempObject = handler.objects.get(i); 
      if(tempObject.getID()==ID.Wall || tempObject.getID()==ID.Door){ 
        if (hitBox.intersects(tempObject.getBounds())){ 
          hitBox.y -= velY; 
          while (!hitBox.intersects(tempObject.getBounds())){ 
            hitBox.y += Math.signum(velY); 
          } 
          hitBox.y -= Math.signum(velY); 
          velY = 0; 
          y = hitBox.y; 
        } 
      }
      if(tempObject.getID()==ID.Shot){ 
        if (hitBox.intersects(tempObject.getBounds())){ 
          hitBox.y -= velY; 
          while (!hitBox.intersects(tempObject.getBounds())){ 
            hitBox.y += Math.signum(velY); 
          } 
          hitBox.y -= Math.signum(velY); 
          //collision code
          Game.handler.removeEnemy(this);
          for (int e = 0; e < Game.handler.objects.size(); e++) {
            GameObject tempObject2 = Game.handler.objects.get(e);
            if (tempObject2 instanceof Door) {
              Door tempDoor = (Door)tempObject2;
              tempDoor.checkIfOpen();
            }
          } 
        } 
      } 
    }
  }
  public void render(Graphics g) {
    g.setColor(Color.orange);
    g.fillRect((int)x, (int)y, SmartEnemySize, SmartEnemySize);                                                       //grafische Darstellung
  }
}
