

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class FastEnemy extends GameObject{
  
  Random random = new Random();
  
  Rectangle hitBox;
  
  public FastEnemy(int x, int y, ID id, Handler handler) {
    super(x, y, id, handler);
    velX=2;
    velY=9;
    hitBox = new Rectangle(x, y, 32, 32);
  }
  
  public Rectangle getBounds() {                                                                            //Grenze wird zur�ckgegeben
    return new Rectangle((int)x,(int)y,32,32);
  }                                                                       
  public void tick() {
    collision();
    x+=velX;
    y+=velY;
    if(y<=0 || y>=Game.HEIGHT-32) velY *=-1;
    if(x<=0 || x>=Game.WIDTH-32) velX *=-1;                                                                           //normale Verhaltensmethode
    handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.cyan, 32, 32, 0.08f, handler));
    hitBox.x = (int)x;
    hitBox.y = (int)y;
  }
  public void collision() {
    for (int i = 0; i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject.getID()==ID.Shot) {
        if(getBounds().intersects(tempObject.getBounds())) {
          //collision code
          x = random.nextInt(Game.WIDTH); 
          y = random.nextInt(Game.HEIGHT);
          int velXr = Game.ranInt(0,1);                                                   //Kollision mit Schuss
          if(velXr == 0) {velX*=-1;}
          int velYr = Game.ranInt(0,1);
          if(velYr == 0) {velX*=-1;}
        }
      }
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
          velX *= (-1);
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
          velY *= (-1);
          y = hitBox.y;
        }
      }
    }
  }
  public void render(Graphics g) {
    g.setColor(Color.cyan);
    g.fillRect((int)x, (int)y, 32, 32);                                                       //wird gezeichnet
  }
}
