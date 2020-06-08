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
import Source.Engine.Vector2;
import Source.World.GameObjects.BulletTypes.DirectionalShot;
import Source.World.GameObjects.Door;
import Source.World.GameObjects.Items.Ammo;
import Source.World.GameObjects.Items.ShotgunAmmo;

public class RangedEnemy extends GameObject{
  Rectangle hitBox;
  int range = 400;
  int hp = 15;
  float ms;
  int cooldown;
  public static int usualCooldown = 60;
  boolean aggroed = false;
  int cd;
  
  public int RangedEnemySize;
  
  public RangedEnemy(float x, float y, ID id, Handler handler, int cooldown, int size) {
    super(x,y,size,size,id,handler);
    this.RangedEnemySize = size;
    hitBox = new Rectangle((int)x,(int)y,size,size);
    velX = 2;
    velY = 2;
    Vector2 v = new Vector2(velX,velY);
    ms = (float) v.getLength();
    this.cooldown = cooldown;
    cd = cooldown;
  }
  
  public RangedEnemy(float x, float y, ID id, Handler handler, int cooldown, int size, int hp) {
    super(x,y,size,size,id,handler);
    this.RangedEnemySize = size;
    hitBox = new Rectangle((int)x,(int)y,size,size);
    velX = 2;
    velY = 2;
    Vector2 v = new Vector2(velX,velY);
    ms = (float) v.getLength();
    this.cooldown = cooldown;
    this.hp = hp;
    cd = cooldown;
  }
  
  public void collision() {
    hitBox.x += velX;
    hitBox.y += velY;
    for (int i = 0;i < handler.objects.size();i++) {         
      GameObject tempObject = handler.objects.get(i); 
      if(tempObject.getID()==ID.Wall || tempObject.getID()==ID.Door){ 
        if (hitBox.intersects(tempObject.getBounds())){ 
          hitBox.x -= velX;
          hitBox.y -= velY;
          while (!hitBox.intersects(tempObject.getBounds())){ 
            hitBox.x += Math.signum(velX);
            hitBox.y += Math.signum(velY); 
          } 
          hitBox.x -= Math.signum(velX);
          hitBox.y -= Math.signum(velY); 
          velX *= -1;
          velY *= -1;
        } 
      }
      if(tempObject.getID()==ID.Shot){ 
        if (hitBox.intersects(tempObject.getBounds())){ 
          
          while (!hitBox.intersects(tempObject.getBounds())){ 
            hitBox.x += Math.signum(velX);
            hitBox.y += Math.signum(velY); 
          } 
          hitBox.x -= Math.signum(velX); 
          hitBox.y -=Math.signum(velY);
          this.hp -= 5;
          handler.removeObject(tempObject);
        } 
      }
    } 
    x = hitBox.x;
    y = hitBox.y;  
  }
  public void attackmovement() {
    Vector2 direction = Vector2.directionalvector(new Vector2(Game.player.getX()+Game.player.playerLength/2, Game.player.getY()+Game.player.playerHeight/2),new Vector2(x,y));
    velX= (int)(direction.x*ms);     // Bewegt sich in richtung des Spielers ohner Ruecksicht auf Waende 
    velY=(int) (direction.y*ms);
  }
  public void kite() {
    //Bewegt sich vom Gegner Weg
    Vector2 direction = Vector2.directionalvector(new Vector2(Game.player.getX()+Game.player.playerLength/2, Game.player.getY()+Game.player.playerHeight/2),new Vector2(x,y));
    velX = -1*direction.x*ms;
    velY = -1* direction.y*ms;
  }
  public void shootatplayer () {
    Vector2 v =  Vector2.directionalvector(new Vector2(Game.player.getX()+Game.player.playerLength/2, Game.player.getY()+Game.player.playerHeight/2),new Vector2(x,y));
    Vector2 offset = v;
    offset.normalize();
    offset.scale(50);
    handler.addObject(new DirectionalShot(this,offset,v,new Vector2 (x,y),ID.EnemyShot,handler));  // ersellte einen neues Schussobjekt in Richting des Spielers
  }   
  public void checkhp () {
    if (hp<1) {
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
    } // end of if
  }
  public void render(Graphics g) {
    g.setColor(Color.red);
    g.fillRect((int)x, (int)y, RangedEnemySize, RangedEnemySize);
    if (Game.debug) {
      g.drawString("X: " + x + "Y: " + y, (int)x, (int)y);
      System.out.println("Ist da");
    }
  }
  public Rectangle getBounds() {
    return new Rectangle(hitBox.x,hitBox.y,RangedEnemySize,RangedEnemySize);                                                    //Grenzen kriegen
  }
  public void sethp (int damage){ 
    this.hp -= damage;     
  }
  public void tick(){  //Decsiontree
    checkhp();
    if ((int)x-range<Game.player.getX()+40 && Game.player.getX() +40<(int)x+range && (int)y-range<Game.player.getY()+15 && Game.player.getY()+15<(int)y+range) {
      if (((int)x-range/2)<Game.player.getX()+40 && Game.player.getX()+40<((int)x+range/2) && ((int)y-range/2)<Game.player.getY() && Game.player.getY()<((int)y+range/2)) {
        // Gegner handlet je nach Entfernung zum Spieler unterschiedlich
        aggroed=true;
        if (cd==0) {  // Cooldown im Schuesse zu limentieren
          shootatplayer();
          cd = this.cooldown;
        } // end of if 
        else {
          kite();
          cd--;
        } // end of if-else 
      } // end of if
      else if (((int)x-50-range/2)<Game.player.getX()+40 && Game.player.getX()+40<((int)x+50+range/2) && ((int)y-50-range/2)<Game.player.getY() && Game.player.getY()<((int)y+50+range/2)) {
        // Zwischenzone ohne Bewegung
        if (cd==0) {
          shootatplayer();
          cd = this.cooldown;
        } // end of if 
        else {
          cd--;
        } // end of if-else 
      }
      else {
        // Beegung auf den Spieler
        attackmovement();
      } // end of if-else
    } // end of if
    else {
      if (aggroed==true) {
        velX=2;
        velY=2;
      } // end of if
    } // end of if-else
    collision();
  }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + RangedEnemySize > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + RangedEnemySize > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + RangedEnemySize > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + RangedEnemySize > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }  
}
  
