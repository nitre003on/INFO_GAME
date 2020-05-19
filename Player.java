

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import java.util.Scanner;

public class Player extends GameObject {
  
  Direction direction;
  
  Rectangle hitBox;
  
  float tempVelX;
  float tempVelY;
  
  public static boolean[] itemPicked = new boolean[2];
  
  public Player(int x, int y, ID id, Handler handler, Direction direction) {
    super(x, y, id, handler);
    this.direction = direction;
    hitBox = new Rectangle(x, y, 32, 32);
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,32,32);                                            //Methode um die Umrisse zu kriegen
  }
  
  public void tick() {
    if(itemPicked[0]) HUD.HEALTH += 50;
    hitBox.x = (int)x; 
    hitBox.y = (int)y;
    tempVelX = velX;
    tempVelY = velY;
    collision();
    x+=velX;                                                          //Bewegungsrichtumg
    y+=velY; 
    velX = tempVelX;
    velY = tempVelY;                                                          
    x=Game.clamp(x, 0, Game.WIDTH-32);                                              //das innerhalb des Fensters bleiben
    y=Game.clamp(y, 0, Game.HEIGHT-32);
    handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.white, 32, 32, 0.08f, handler));             //"Schwanz" ran h�ngen                                                  
  }
  
  public void collision() {
    for (int i = 0; i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject.getID()==ID.BasicEnemy || tempObject.getID()==ID.FastEnemy||tempObject.getID()==ID.SmartEnemy) {
        if(getBounds().intersects(tempObject.getBounds())) {
          //collision code
          HUD.HEALTH -=2;                                                   //Das passiert, wenn man mit einer Art Gegner "kollidiert"(sich �berschneidet)
        }
      }
      /*if(tempObject.getID()==ID.Item){
        if(getBounds().intersects(tempObject.getBounds())) {
          itemPicked = true;
          }  
        }*/
      }
      hitBox.x += velX; 
      for (int i = 0;i < handler.objects.size();i++) {         
        GameObject tempObject = handler.objects.get(i); 
        if(handler.objects.get(i).getID() == ID.Wall){ 
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
      } 
      
      hitBox.y += velY; 
      for (int i = 0;i < handler.objects.size();i++) {         
        GameObject tempObject = handler.objects.get(i); 
        if(handler.objects.get(i).getID() == ID.Wall){ 
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
      } 
    }
    
  public void shoot() {
    handler.addObject(new Shot((int) x,(int) y, direction, ID.Shot, handler));                                //Schuss methode(ein Schuss Object wird erstellt
  } 
  
  public void interact() { 
    x = x*(-1);
    y = y*(-1);
  }

  
  public void render(Graphics g) {
    
    /*Graphics2D g2d = (Graphics2D) g;
    g.setColor(Color.green);
    g2d.draw(getBounds());*/
     
    g.setColor(Color.white);
    g.fillRect((int)x, (int)y, 32, 32);                                                   // Form wird ge"zeichnet"
    if(itemPicked[1]){
      System.out.println("GUN!!");
      g.setColor(Color.orange);
      g.fillRect((int)x+26, (int)y+26, 16,16);
    } 
  }
  
  
}
