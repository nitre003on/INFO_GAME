package Source.World.GameObjects;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Direction;
import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Graphics.animationHandler;
import Source.Engine.UI.HUD;
import Source.World.Game;
import Source.World.GameObject;
import Source.World.GameObjects.BulletTypes.Shot;

public class Player extends GameObject {
  
  Direction direction;
  
  
  public int[] roomBounds = new int[] {0, 0, 0};
  
  float tempVelX;
  float tempVelY;
  
  private animationHandler ah;
  
  public static boolean[] itemPicked = new boolean[2];
  
  public static int playerLength = 30;
  public static int playerHeight = 80;
  
  public Player(int x, int y, ID id, Handler handler, Direction direction) {
    super(x, y,playerLength,playerHeight, id, handler);
    this.direction = direction;
    
    
    ah = new animationHandler("Content/playerSprites.png", 35);
    ah.createAnimation("walk", 1, 4);
    ah.createAnimation("idle", 5, 5);
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,playerLength,playerHeight);                                            //Methode um die Umrisse zu kriegen
  }
  
  public void tick() {
    if(itemPicked[0]) HUD.HEALTH += 50;
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
    //handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.white, 32, 32, 0.08f, handler));             //"Schwanz" ran h�ngen                                                  
  }
  
  public void collision() {
    //Kollision mit Gegnern
    for (int i = 0; i < handler.enemies.size(); i++) {
      GameObject tempObject = handler.enemies.get(i);
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
    
    //Kollision mit wand oder tuer
    this.x += velX; 
    for (int i = 0; velX != 0 && i < handler.objects.size();i++) {         
      GameObject tempObject = handler.objects.get(i); 
      if(tempObject instanceof Wall) { 
        if (this.getBounds().intersects(tempObject.getBounds())) {
          if (Game.debug) {
            System.out.println("Hit Wall");
          } 
          this.x -= velX; 
          while (!this.getBounds().intersects(tempObject.getBounds())) { 
            this.x += Math.signum(velX);                           //Die Kollision prüft ob der Spieler nächsten Frame in einer Wand sein würde. 
          }                                                          //Wenn dies der Fall ist, dann nähert sie den Spieler an die Wand ran.
          this.x -= Math.signum(velX);  
          velX = 0;
        }
      }
    }
    
    for (int i = 0; velX != 0 && i < handler.objects.size();i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject instanceof Door) {
        if (this.getBounds().intersects(tempObject.getBounds())) {
          if (Game.debug) {
            System.out.println("Hit Door");
          } 
          Door tempDoor = (Door)tempObject;
          if (tempDoor.isUnlocked()) {
            tempDoor.teleport(Game.player, i);
          }
          else {
            this.x -= velX;
            while (!this.getBounds().intersects(tempDoor.getBounds())) { 
              this.x += Math.signum(velX);
            }
            this.x -= Math.signum(velX);  
            velX = 0;
          }
        }
      }
    }
    this.x -= velX;
    
    
    this.y += velY; 
    for (int i = 0; velY != 0 && i < handler.objects.size();i++) {         
      GameObject tempObject = handler.objects.get(i); 
      if(tempObject instanceof Wall) { 
        if (this.getBounds().intersects(tempObject.getBounds())) {
          if (Game.debug) {
            System.out.println("Hit Wall");
          }
          this.y -= velY; 
          while (!this.getBounds().intersects(tempObject.getBounds())) { 
            this.y += Math.signum(velY); 
          } 
          this.y -= Math.signum(velY); 
          velY = 0;
        } 
      }
    }
    for (int i = 0; velY != 0 && i < handler.objects.size();i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject instanceof Door) {
        if (this.getBounds().intersects(tempObject.getBounds())) {
          if (Game.debug) {
            System.out.println("Hit Door");
          }
          Door tempDoor = (Door)tempObject;
          if (tempDoor.isUnlocked()) {
            tempDoor.teleport(Game.player, i);
          }
          else {
            this.y -= velY;
            while (!this.getBounds().intersects(tempDoor.getBounds())) { 
              this.y += Math.signum(velY);
            }
            this.y -= Math.signum(velY);  
            velY = 0;
          }
        }
      }
    } 
    this.y -= velY;
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
    ah.draw(g, (int)x, (int)y, -40, -10,100);
    
    if (Game.debug) {
      g.setColor(Color.WHITE);
      g.drawString("X:" ,(int)x - 30, (int)y + 100);
      g.drawString("Y:" ,(int)x + 15, (int)y + 100);
      g.drawString(Integer.toString((int)this.x), (int)x - 20, (int)y + 100); 
      g.drawString(Integer.toString((int)this.y), (int)x + 25, (int)y + 100);
    }
  }
}
