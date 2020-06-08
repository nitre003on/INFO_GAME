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
import Source.World.GameObjects.Items.Ammo;
import Source.World.GameObjects.Items.ShotgunAmmo;

public class FastEnemy extends GameObject{
  Random random = new Random();
  
  public static int FastEnemySize = 32;
  
  private int hp = 10;
  
  Rectangle hitBox;
  
  public FastEnemy(int x, int y, ID id, Handler handler) {
    super(x, y,FastEnemySize,FastEnemySize, id, handler);
    velX=2;
    velY=9;
    hitBox = new Rectangle(x, y, FastEnemySize, FastEnemySize);
  }
  
  public Rectangle getBounds() {                                                                            //Grenze wird zur�ckgegeben
    return new Rectangle((int)x,(int)y,FastEnemySize,FastEnemySize);
  }                                                                       
  public void tick() {
    if (hp <= 0) {
      int r = Game.ranInt(0, 3);
      switch (r) {
        case 0 : 
          handler.addObject(new Ammo((int)x, (int)y, ID.Item, handler));
          break;
        case 1 : 
          handler.addObject(new ShotgunAmmo((int)x, (int)y, ID.Item, handler));
          break;
        default: 
          
      }
      
      handler.removeEnemy(this);
      for (int e = 0; e < Game.handler.objects.size(); e++) {
        GameObject tempObject2 = Game.handler.objects.get(e);
        if (tempObject2 instanceof Door) {
          Door tempDoor = (Door)tempObject2;
          tempDoor.checkIfOpen();
        }
      }
    }
    hitBox.x = (int)x; 
    hitBox.y = (int)y;
    collision();
    x+=velX;
    y+=velY;
    if(y<=0 || y>=Game.HEIGHT-FastEnemySize) velY *=-1;
    if(x<=0 || x>=Game.WIDTH-FastEnemySize) velX *=-1;                                                                           //normale Verhaltensmethode
    handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.cyan, FastEnemySize, FastEnemySize, 0.08f, handler));
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
          velX *= -1; 
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
          hp -= 5;
          handler.removeObject(tempObject);
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
          velY *= -1; 
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
          hp -= 5;
          handler.removeObject(tempObject);
        } 
      } 
    } 
  }
  
  public void render(Graphics g) {
    g.setColor(Color.cyan);
    g.fillRect((int)x, (int)y, FastEnemySize, FastEnemySize);                                                       //wird gezeichnet
  }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + FastEnemySize > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + FastEnemySize > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + FastEnemySize > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + FastEnemySize > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }
}
