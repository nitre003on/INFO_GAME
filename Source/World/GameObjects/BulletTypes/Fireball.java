package Source.World.GameObjects.BulletTypes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.World.GameObject;
import Source.World.GameObjects.BasicTrail;

public class Fireball extends Shot{  //Schuss in richtung Maus (Piet)
  
  private Vector2 dir = new Vector2(0,0);
  private GameObject owner;
  private float speed, despawnTimer = 100;
  
  Rectangle hitBox;
  
  public Fireball(GameObject owner, Vector2 target, ID id, Handler handler) {
    super((int)owner.getX(), (int)owner.getY(), null, id, handler);
    dir = Vector2.subtract(target, Vector2.getPos(owner));
    speed = 15;
    this.owner = owner;
    dir.normalize();
    hitBox = new Rectangle((int)x, (int)y, 64, 64);
  }

  public Fireball(GameObject owner,Vector2 offset, Vector2 target, ID id, Handler handler) {
    super((int)owner.getX() + (int)offset.x, (int)owner.getY() + (int)offset.y, null, id, handler);
    dir = Vector2.subtract(target, Vector2.getPos(owner));
    speed = 15;
    this.owner = owner;
    dir.normalize();
    hitBox = new Rectangle((int)x, (int)y, 64, 64);
  }
  
  public void tick() {
    hitBox.x = (int)x; 
    hitBox.y = (int)y;
    collision();
    x += dir.x * speed;
    y += dir.y * speed;
    handler.addObject(new BasicTrail((int)x+16, (int)y+16, ID.Trail, Color.red, 32, 32, 0.08f, handler));
    if(despawnTimer < 0){handler.removeObject(this);}
    else{despawnTimer--;}
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
          handler.removeObject(this); 
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
          handler.removeObject(this); 
        } 
      } 
    } 
  }
  public void render(Graphics g) {
    g.setColor(Color.red);
    g.fillRect((int)x, (int)y, 64, 64);                                                                   //Darstellung
  }
}
