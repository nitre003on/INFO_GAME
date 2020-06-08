package Source.World.GameObjects;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import Source.Engine.animationHandler;
import Source.Engine.Direction;
import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.UI.HUD;
import Source.World.Game;
import Source.World.GameObject;
import Source.World.GameObjects.BulletTypes.Shot;

import java.util.Scanner;

public class Player extends GameObject {
  
  Direction direction;
  
  public Rectangle hitBox;
  
  float tempVelX;
  float tempVelY;
  
  private animationHandler ah;
  
  public static boolean[] itemPicked = new boolean[/*Game.amountOfDiffItems*/4];
  public static boolean[] chestOpened = new boolean[1];
  
  public static int playerLength = 30;
  public static int playerHeight = 80;
  
  public Player(int x, int y, ID id, Handler handler, Direction direction) {
    super(x, y, id, handler);
    this.direction = direction;
    hitBox = new Rectangle(x, y, this.playerLength, this.playerHeight);
    
    ah = new animationHandler("Content/playerSprites.png", 35);
    ah.createAnimation("walk", 1, 4);
    ah.createAnimation("idle", 5, 5);
    //ah.playAnimation("walk", 0.05f, false, false);
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,hitBox.width,hitBox.height);                                            //Methode um die Umrisse zu kriegen
  }
  
  public void tick() {
    if(itemPicked[0]){
      HUD.HEALTH += 50;
      itemPicked[0] = false;
      }
    hitBox.x = (int)x; 
    hitBox.y = (int)y;
    tempVelX = velX;
    tempVelY = velY;
    collision();
    if(velX == 0 && velY == 0){
      ah.playAnimation("idle", 0, false, true);
    }else if (ah.curPlaying != "walk"){
        ah.playAnimation("walk", 0.05f, true, false);
      }
    if(velX > 0){ ah.faceLeft(); }
      else if (velX < 0){ ah.faceRight(); }
    ah.tick();
    x+=velX;                                                          //Bewegungsrichtumg
    y+=velY; 
    velX = tempVelX;
    velY = tempVelY;                                                          
    x=Game.clamp(x, 0, Game.WIDTH-this.playerLength);                                              //das innerhalb des Fensters bleiben
    y=Game.clamp(y, 0, Game.HEIGHT-this.playerHeight);
    //handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.blue, 32, 32, 0.08f, handler));             //"Schwanz" ran h�ngen                                                  
  }
  
  public void collision() {
    //Kollision mit Gegnern
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
    
    //Kollision mit wand
    hitBox.x += velX; 
    for (int i = 0;i < handler.objects.size();i++) {         
      GameObject tempObject = handler.objects.get(i); 
      if(handler.objects.get(i) instanceof Wall){ 
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
      if(handler.objects.get(i) instanceof Wall){ 
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
    
    
    //Teleport 
    for (int i = 0;i < handler.objects.size();i++) {         
      GameObject tempObject = handler.objects.get(i); 
      if(handler.objects.get(i) instanceof Door){
        if (hitBox.intersects(tempObject.getBounds())){
          Door tempDoor = (Door)tempObject; 
          tempDoor.teleport(Game.player, i);
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
    
    //g.setColor(Color.white);
    //g.fillRect((int)x + 50, (int)y, 89, 100);                                                   // Form wird ge"zeichnet"
    if(itemPicked[1]){
      g.setColor(Color.orange);
      g.fillRect((int)x+26, (int)y+26, 16,16);
    }
    if(itemPicked[2]){
      g.setColor(Color.orange);
      g.fillRect((int)x+26, (int)y+26, 32,16);
    } 
    ah.draw(g, (int)x, (int)y, -40, -10,100);
  }
  
  
}
