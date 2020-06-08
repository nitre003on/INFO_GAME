package Source.World.GameObjects;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import Source.Engine.Direction;
import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.Engine.Graphics.animationHandler;
import Source.Engine.UI.HUD;
import Source.World.Game;
import Source.World.GameObject;
import Source.World.GameObjects.BulletTypes.Shot;

public class Player extends GameObject {

  Direction direction;

  public int[] roomBounds = new int[] { 0, 0, 0 };

  float tempVelX;
  float tempVelY;

  private animationHandler ah;

  public static boolean[] itemPicked = new boolean[/* Game.amountOfDiffItems */4];
  public static boolean[] chestOpened = new boolean[1];

  public static int playerLength = 30;
  public static int playerHeight = 80;

  public Player(int x, int y, ID id, Handler handler, Direction direction) {
    super(x, y, playerLength, playerHeight, id, handler);
    this.direction = direction;

    ah = new animationHandler("Content/playerSprites.png", 35);
    ah.createAnimation("walk", 1, 4);
    ah.createAnimation("idle", 5, 5);
  }

  public Rectangle getBounds() {
    return new Rectangle((int) x, (int) y, playerLength, playerHeight); // Methode um die Umrisse zu kriegen
  }

  public void tick() {
    if (itemPicked[0]) {
      HUD.HEALTH += 50;
      itemPicked[0] = false;
    }
    tempVelX = velX;
    tempVelY = velY;
    collision();
    if (velX == 0 && velY == 0) {
      ah.playAnimation("idle", 0, false, true);
    } else if (ah.curPlaying != "walk") { // starte die animation nicht neu, falls sie schon läuft
      ah.playAnimation("walk", 0.05f, true, false);
    }
    if (velX > 0) {
      ah.faceLeft();
    } else if (velX < 0) {
      ah.faceRight();
    }
    ah.tick();
    x += velX; // Bewegungsrichtumg
    y += velY;
    velX = tempVelX;
    velY = tempVelY;
    x = Game.clamp(x, 0, Game.WIDTH - this.playerLength); // das innerhalb des Fensters bleiben
    y = Game.clamp(y, 0, Game.HEIGHT - this.playerHeight);
    // handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.white, 32,
    // 32, 0.08f, handler)); //"Schwanz" ran haengen
  }

  public void collision() {
    // Kollision mit Gegnern
    for (int i = 0; i < handler.enemies.size(); i++) {
      GameObject tempObject = handler.enemies.get(i);
      if (tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy
          || tempObject.getID() == ID.SmartEnemy || tempObject.getID() == ID.RangedEnemy) {
        if (getBounds().intersects(tempObject.getBounds())) {
          // collision code
          HUD.HEALTH -= 2; // Das passiert, wenn man mit einer Art Gegner "kollidiert"(sich ueberschneidet)
          if (tempObject.getID() == ID.EnemyShot) {
            HUD.HEALTH -= 8;
          }
        }
      }
      /*
       * if(tempObject.getID()==ID.Item){
       * if(getBounds().intersects(tempObject.getBounds())) { itemPicked = true; } }
       */
    }

    // Kollision mit wand und Tuer
    this.x += velX; // X Kollision
    for (int i = 0; i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject instanceof Wall) {
        if (this.getBounds().intersects(tempObject.getBounds())) {
          if (Game.debug) {
            System.out.println("Hit Wall");
          }
          this.x -= velX;
          while (!this.getBounds().intersects(tempObject.getBounds())) {
            this.x += Math.signum(velX); // Die Kollision prüft ob der Spieler nächsten Frame in einer Wand sein würde.
          } // Wenn dies der Fall ist, dann nähert sie den Spieler an die Wand ran.
          this.x -= Math.signum(velX);
          velX = 0;
        }
      }

      if (tempObject.getID() == ID.EnemyShot) {
        if (this.getBounds().intersects(tempObject.getBounds())) {
          HUD.HEALTH -= 10;
          Game.handler.removeObject(tempObject);
        }
      }
    }

    for (int i = 0; velX != 0 && i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject instanceof Door) {
        if (this.getBounds().intersects(tempObject.getBounds())) {
          if (Game.debug) {
            System.out.println("Hit Door");
          } // Die Kollision der Tuer wird nach der Kollision der Waende gemacht um auf die
          Door tempDoor = (Door) tempObject; // Position in der Liste objects zu achten
          if (tempDoor.isUnlocked()) { // Wenn die Tuer offen ist, wird der Spieler teleportiert
            tempDoor.teleport(Game.player, i);
          } else { // Wenn die Tuer geschlossen ist, wird der die Tuer wie eine Wand behandelt
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

    this.y += velY; // Y Kollision genause wie X Kollision nur alle "X" wurden mit "Y" ersetzt
    for (int i = 0; velY != 0 && i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject instanceof Wall) {
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
    for (int i = 0; velY != 0 && i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject instanceof Door) {
        if (this.getBounds().intersects(tempObject.getBounds())) {
          if (Game.debug) {
            System.out.println("Hit Door");
          }
          Door tempDoor = (Door) tempObject;
          if (tempDoor.isUnlocked()) {
            tempDoor.teleport(Game.player, i);
          } else {
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
    handler.addObject(new Shot((int) x, (int) y, direction, ID.Shot, handler)); // Schuss methode(ein Schuss Object wird
                                                                                // erstellt
  }

  public void interact() {
    x = x * (-1);
    y = y * (-1);
  }

  public void render(Graphics g) {
    /*
     * Graphics2D g2d = (Graphics2D) g; g.setColor(Color.green);
     * g2d.draw(getBounds());
     */

    // g.setColor(Color.white);
    // g.fillRect((int)x + 50, (int)y, 89, 100); // Form wird ge"zeichnet"
    Vector2 itemPos;
    if(ah.flippedX){
      itemPos = new Vector2(26, 26);
    }else{
      itemPos = new Vector2(-38, 26);
    }

    if (itemPicked[1]) {
      try {
        if(ah.flippedX)
          img = loadImage("Content/items/gun.png");
        else
          img = SpriteFlipX(loadImage("Content/items/gun.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }

      drawSprite(g, 2,itemPos);
    }
    if(itemPicked[2]){
      try {
          img = loadImage("Content/items/shotgun.png");
      } catch (IOException e) {
        e.printStackTrace();
      }
      drawSprite(g, 2,new Vector2(26, 26));
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
