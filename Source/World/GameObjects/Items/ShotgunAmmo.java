package Source.World.GameObjects.Items;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Direction;
import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.Engine.Graphics.Image;
import Source.Engine.Graphics.Image.displayTypes;
import Source.World.Game;
import Source.World.GameObject;
import Source.World.GameObjects.Player;
import Source.World.GameObjects.BulletTypes.Shot;

public class ShotgunAmmo extends GameObject {
  
  boolean picked = false;
  Direction direction;
  public int amm = 60;
  public boolean empty = false;
  //String imgURL, Vector2 pos, Vector2 size, float zoomLvl, displayTypes displayType, ID id, Handler handler
  
  public int width, height = 8;
  
  public ShotgunAmmo(int x, int y, ID id, Handler handler) {
    super(x, y,8,8, id, handler);
    imgUrl = "Content/items/gun.png";
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y,width,height);                                            //Methode um die Umrisse zu kriegen
  }
  
  public void tick() {
    collision();
    
    if(picked){
      Game.shotgun.totalAmmo+=10;
    }
    x+=velX;                                                          //Bewegungsrichtumg
    y+=velY;                                                          
    x=Game.clamp(x, 0, Game.WIDTH-width);                                              //das innerhalb des Fensters bleiben
    y=Game.clamp(y, 0, Game.HEIGHT-height);
  }
  
  public void collision() {
    for (int i = 0; i < handler.objects.size(); i++) {
      GameObject tempObject = handler.objects.get(i);
      if (tempObject.getID()==ID.Player) {
        if(getBounds().intersects(tempObject.getBounds())) {
          if(Game.player.itemPicked[2]){
            picked = true;
            handler.removeObject(this);
          }
        }
      }
    } 
  } 
  
  public void interact() { 
    x = x*(-1);
    y = y*(-1);
  }
  
  
  public void render(Graphics g) { 
    g.setColor(Color.green);
    g.fillRect((int)x, (int)y, 8,8);
    
  }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + width > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + height > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + width > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + height > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }
}
