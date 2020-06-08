package Source.Engine.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.PointerInfo;
import java.awt.MouseInfo;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.Engine.Input.MouseInput;
import Source.World.Game;
import Source.World.GameObject;
import Source.World.GameObjects.Player;
import Source.World.GameObjects.BulletTypes.Fireball;

public class KeyInput extends KeyAdapter{
  private Handler handler;
  public static boolean[] keyDown = new boolean[4];
  private String[] axes = new String[2];
  
  public static int playerSpeed = 6;
  
  public KeyInput(Handler handler) {
    this.handler = handler;
    keyDown[0] = false;
    keyDown[1] = false;
    keyDown[2] = false;
    keyDown[3] = false;
    axes[0] = "";
    axes[1] = ""; 
  }
  
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    for(int i=0;i<handler.objects.size();i++) {
      GameObject tempObject = handler.objects.get(i);
      if(tempObject.getID()==ID.Player) {
        //key events for player1
        
        if(key == KeyEvent.VK_SHIFT) {tempObject.dash = true;}   
        
        //axes 1
        if(key == KeyEvent.VK_W) {
          tempObject.setVelY(-playerSpeed); keyDown[0]= true;
          if(keyDown[2]){
            axes[1]="FS";
          }
        }
        if(key == KeyEvent.VK_S){
          tempObject.setVelY(playerSpeed); keyDown[2]= true;
          if(keyDown[0]){
            axes[1]="FW";
          }
        }
        
        //axes 0
        if(key == KeyEvent.VK_A) {
          tempObject.setVelX(-playerSpeed); keyDown[1]= true;
          if(keyDown[3]){
            axes[0]="FD";
          }
        }
        if(key == KeyEvent.VK_D) {
          tempObject.setVelX(playerSpeed); keyDown[3]= true;
          if(keyDown[1]){
            axes[0]="FA";
          }
        }                                                                   //KeyEvents falls es ein spieler ist(Die Unterscheidung ist wichtig, falls mann mehrspieler-Modi oder verschiedene Charaktere oder so haben will)
        
        if(key == KeyEvent.VK_R){
          /*int temp = 30-Game.gun.magazin;
          if(Game.gun.totalAmmo>0){
          if(temp >= Game.gun.totalAmmo){
          Game.gun.magazin+=Game.gun.totalAmmo;
          Game.gun.totalAmmo=0;
          Game.gun.empty = false;                                                                                                         //TO DO !!!
          }else{
          Game.gun.magazin=30;
          Game.gun.totalAmmo-=temp;
          Game.gun.empty = false;
          }
          }*/
          
          if(Game.player.itemPicked[1]){
            if(Game.gun.magazin != 30){
              if(Game.gun.totalAmmo!=0){
                //System.out.println("RELOADING GUN!!!");
                Game.gun.magazin = 30;
                Game.gun.empty = false;
                if(Game.gun.totalAmmo -30 <=0){
                  Game.gun.totalAmmo =0;
                }else{
                  Game.gun.totalAmmo -=30;
                } 
              }
            }
          }
          if(Game.player.itemPicked[2]){
            if(Game.shotgun.magazin != 5){
              if(Game.shotgun.totalAmmo!=0){
                //System.out.println("RELOADING SHOTGUN!!!");
                Game.shotgun.magazin = 5;
                Game.shotgun.empty = false;
                if(Game.shotgun.totalAmmo -5 ==0){
                  Game.shotgun.totalAmmo =0;
                }else{
                  Game.shotgun.totalAmmo -=5;
                } 
              }
            }  
          }
        }
        if(key == KeyEvent.VK_E){
          if(Game.player.itemPicked[3]){
            if(Game.scroll.uses !=0){
              handler.addObject(new Fireball(Game.player,new Vector2(0, Game.player.hitBox.height / 2),new Vector2((float)Game.pi.getLocation().getX() - Game.cam.getX(),(float)Game.pi.getLocation().getY() - Game.cam.getY()),ID.Shot,handler));
              Game.scroll.uses -=1;
              if(Game.scroll.uses ==0){
                Game.player.itemPicked[3] = false;
                }
            }
          }
          
          /*if(keyDown[0]&&keyDown[2]){
          axes[1] = true;
          }
          if(keyDown[1]&&keyDown[3]){
          axes[0] = true;
          }*/
          
          //if(key == KeyEvent.VK_UP) temObject.shoot(Direction.Up);                                    //WENN DIE TASTEN GEDR�CKT WERDEN
          //if(key == KeyEvent.VK_DOWN) temObject.shoot(Direction.Down);
          //if(key == KeyEvent.VK_LEFT) temObject.shoot(Direction.Left);
          //if(key == KeyEvent.VK_RIGHT) temObject.shoot(Direction.Right);
        }
        if(key == KeyEvent.VK_ESCAPE){
          Game.curState = Game.states.menu;
          Game.hud.drawPause();
        }
      }
    }
    if(key == KeyEvent.VK_ESCAPE){
      if(Game.curState == Game.states.play){
        Game.curState = Game.states.paused;
        Game.hud.drawPause();
      }else if(Game.curState == Game.states.paused){
        Game.hud.clearQueue();
        Game.curState = Game.states.play;
      }
    }
  }
  
  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    for(int i=0;i<handler.objects.size();i++) {
      GameObject tempObject = handler.objects.get(i);
      if(tempObject.getID()==ID.Player) {
        //key events for player1                                                  
        if(key == KeyEvent.VK_SHIFT) {tempObject.dash = false;} 
        
        //axes 1
        if(key == KeyEvent.VK_W) {
          if(axes[1]=="FS"){
            tempObject.setVelY(playerSpeed);
            axes[1]="";
          }else if(axes[1]=="FW"){                  
              tempObject.setVelY(playerSpeed);
              axes[1]="";
            }  
          keyDown[0]= false;
        }  
        if(key == KeyEvent.VK_S) {
          if(axes[1]=="FW"){
            tempObject.setVelY(-playerSpeed);
            axes[1]="";
          }else if(axes[1]=="FS"){
              tempObject.setVelY(-playerSpeed);
              axes[1]="";
            }
          keyDown[2]= false;
        }
        
        //axes 0
        if(key == KeyEvent.VK_A) {
          if(axes[0]=="FA"){
            tempObject.setVelX(playerSpeed);
            axes[0]="";
          }else if(axes[0]=="FD"){
              tempObject.setVelX(playerSpeed);
              axes[0]="";
            }
          keyDown[1]= false;
        }                   
        if(key == KeyEvent.VK_D) {
          if(axes[0]=="FA"){
            tempObject.setVelX(-playerSpeed);
            axes[0]="";
          }else if(axes[0]=="FD"){
              tempObject.setVelX(-playerSpeed);
              axes[0]="";
            }
          keyDown[3]= false;
        }  
        
        if (!keyDown[0]&&!keyDown[2]) tempObject.setVelY(0);
        if (!keyDown[1]&&!keyDown[3]) tempObject.setVelX(0);
      }                                     //KeyEvents falls es ein spieler ist(Die Unterscheidung ist wichtig, falls mann mehrspieler-Modi oder verschiedene Charaktere oder so haben will)
    }                                                               //WENN DIE TASTEN LOSGELASSEN WERDENs
  }
}
    
