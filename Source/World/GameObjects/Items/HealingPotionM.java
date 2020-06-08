package Source.World.GameObjects.Items;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Direction;
import Source.Engine.Handler;
import Source.Engine.ID;
import Source.World.Game;
import Source.World.GameObject;
import Source.World.GameObjects.Player;
import Source.World.GameObjects.BulletTypes.Shot;

public class HealingPotionM extends GameObject {
  
  boolean picked = false;
  Direction direction;
  
  
  public HealingPotionM(int x, int y, ID id, Handler handler) {
    super(x, y,16,16, id, handler);
    imgUrl = "Content/items/healing.png";
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,16,16);                                            //Methode um die Umrisse zu kriegen
  }
  
  public void tick() {
    collision();
    if(picked){
      Player.itemPicked[0] = true;
      handler.removeObject(this);
      }
    x+=velX;                                                          //Bewegungsrichtumg
    y+=velY;                                                          
    x=Game.clamp(x, 0, Game.WIDTH-16);                                              //das innerhalb des Fensters bleiben
    y=Game.clamp(y, 0, Game.HEIGHT-16);
  }
  
  public void collision() {
    for (int i = 0; i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject.getID()==ID.Player) {
        if(getBounds().intersects(tempObject.getBounds())) {
          picked = true;
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
  
  
  public void render(Graphics g) {                                                  // Form wird ge"zeichnet"
    drawSprite(g, 2);
  }
  
  
}
