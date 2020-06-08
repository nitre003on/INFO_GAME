package Source.Engine.Input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.World.Game;
import Source.World.GameObjects.Player;
import Source.World.GameObjects.BulletTypes.DirectionalShot;

public class MouseInput extends MouseAdapter {
  
  private Handler handler;
  
  public MouseInput(Handler handler) {
    this.handler = handler;
  }
  
  public static MouseEvent d;
  
  public void mousePressed(MouseEvent e) {
    if(Game.player.itemPicked[1] && Game.curState == Game.states.play && !Game.gun.empty){
      handler.addObject(new DirectionalShot(Game.player,new Vector2(0, Game.player.playerHeight / 2),new Vector2(e.getX() - Game.cam.getX(),e.getY() - Game.cam.getY()),ID.Shot,handler));
      Game.gun.magazin-=1;
      if(Game.gun.magazin == 0){
        Game.gun.empty = true; 
      }
    } 
    if(Game.player.itemPicked[2] && Game.curState == Game.states.play && !Game.shotgun.empty){
      handler.addObject(new DirectionalShot(Game.player,new Vector2(0, Game.player.playerHeight / 2),new Vector2(e.getX() - Game.cam.getX(),e.getY() - Game.cam.getY()),ID.Shot,handler));
      handler.addObject(new DirectionalShot(Game.player,new Vector2(0, Game.player.playerHeight / 2),new Vector2(e.getX() - Game.cam.getX()-30,e.getY() - Game.cam.getY()+30),ID.Shot,handler));
      handler.addObject(new DirectionalShot(Game.player,new Vector2(0, Game.player.playerHeight / 2),new Vector2(e.getX() - Game.cam.getX()+30,e.getY() - Game.cam.getY()-30),ID.Shot,handler));
      handler.addObject(new DirectionalShot(Game.player,new Vector2(0, Game.player.playerHeight / 2),new Vector2(e.getX() - Game.cam.getX()-60,e.getY() - Game.cam.getY()+60),ID.Shot,handler));
      handler.addObject(new DirectionalShot(Game.player,new Vector2(0, Game.player.playerHeight / 2),new Vector2(e.getX() - Game.cam.getX()+60,e.getY() - Game.cam.getY()-60),ID.Shot,handler));
      Game.shotgun.magazin-=5;
      if(Game.shotgun.magazin == 0){
      Game.shotgun.empty = true;   
      }
    }
    Game.leftMousePressed = true;
  }
  
  public void mouseReleased(MouseEvent e) {
    Game.leftMousePressed = false;
  }  
}
