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

public class Gun extends GameObject {
  
  boolean picked = false;
  Direction direction;
  public int totalAmmo = 90;
  public int magazin = 30;
  public boolean empty = false;
  
  public int width, height = 16;
  
  public Gun(int x, int y, ID id, Handler handler) {
    super(x, y,16,16, id, handler);
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,16,16);                                            //Methode um die Umrisse zu kriegen
  }
  
  public void tick() {
    collision();
    
    if(picked){
      Game.player.itemPicked[1] = true;
      handler.removeObject(this);
      if(Game.player.itemPicked[2]){
        int tempMag = Game.shotgun.magazin;
        int tempTA = Game.shotgun.totalAmmo;
        Game.shotgun = new Shotgun((int)Game.player.getX()+30,(int)Game.player.getY(),ID.Item,handler);
        Game.shotgun.magazin = tempMag;
        Game.shotgun.totalAmmo = tempTA; 
        handler.addObject(Game.shotgun);
        Game.player.itemPicked[2] = false;
        }
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
  
  
  public void render(Graphics g) {
    
    /*Graphics2D g2d = (Graphics2D) g;
    g.setColor(Color.green);
    g2d.draw(getBounds());*/
    /*if(id == ID.Player)*/g.setColor(Color.orange);
    g.fillRect((int)x, (int)y, 16, 16);                                                   // Form wird ge"zeichnet"
    
  }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + width > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + height > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + width > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + height > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }
}
