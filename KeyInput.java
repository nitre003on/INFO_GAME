

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

  
  private Handler handler;
  private boolean[] keyDown = new boolean[4];
  private int rightNow = 0;
  private HUD hud;
  
  public KeyInput(Handler handler) {
    this.handler = handler;
    keyDown[0] = false;
    keyDown[1] = false;
    keyDown[2] = false;
    keyDown[3] = false;
  }
  
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    for(int i=0;i<handler.objects.size();i++) {
      GameObject temObject = handler.objects.get(i);
      if(temObject.getID()==ID.Player) {
        //key events for player1
        /*if(key == KeyEvent.VK_SHIFT) {temObject.dash = true;}   
        if(key == KeyEvent.VK_W) {keyDown[0]= true; rightNow = 0;}
        if(key == KeyEvent.VK_A) {keyDown[1]= true; rightNow = 1;}
        if(key == KeyEvent.VK_S) {keyDown[2]= true; rightNow = 2;}
        if(key == KeyEvent.VK_D) {keyDown[3]= true; rightNow = 3;}                                                                   //KeyEvents falls es ein spieler ist(Die Unterscheidung ist wichtig, falls mann mehrspieler-Modi oder verschiedene Charaktere oder so haben will)
        
        if(keyDown[0]&&keyDown[1]&&!keyDown[2]&&!keyDown[3]){
          temObject.setVelX(-2);
          temObject.setVelY(-2);
          System.out.println("up & left");
        }
          else if(!keyDown[0]&&keyDown[1]&&keyDown[2]&&!keyDown[3]){
            temObject.setVelX(-2);
            temObject.setVelY(2);
            System.out.println("down & left");
          }
            else if(!keyDown[0]&&!keyDown[1]&&keyDown[2]&&keyDown[3]){
              temObject.setVelX(2);
              temObject.setVelY(2);
              System.out.println("down & right");
            }
              else if(keyDown[0]&&!keyDown[1]&&!keyDown[2]&&keyDown[3]){
                temObject.setVelX(2);
                temObject.setVelY(-2);
                System.out.println("up & right");
              }
              else{ 
                if(rightNow == 0) {
                  temObject.setVelY(-2);
                } // end of if
                  else if(rightNow == 1) {
                    temObject.setVelX(-2);
                  } // end of if
                    else if(rightNow == 2) {
                      temObject.setVelY(2);
                    } // end of if
                      else if(rightNow == 3) {
                        temObject.setVelX(2);
                      } // end of if
                
              }
        */
        
        /*
        if(keyDown[0]&&!keyDown[1]&&!keyDown[2]&&!keyDown[3]){
        temObject.setVelX(0);
        temObject.setVelY(-2);
        System.out.println("up");
        }
        if(!keyDown[0]&&!keyDown[1]&&keyDown[2]&&!keyDown[3]){
        temObject.setVelX(0);
        temObject.setVelY(2);
        System.out.println("down");
        }
        if(!keyDown[0]&&keyDown[1]&&!keyDown[2]&&!keyDown[3]){
        temObject.setVelX(-2);
        temObject.setVelY(0);
        System.out.println("left");
        }
        if(!keyDown[0]&&!keyDown[1]&&!keyDown[2]&&keyDown[3]){
        temObject.setVelX(2);
        temObject.setVelY(0);
        System.out.println("right");
        }
        */
        
        
        
        
        
        
        if(key == KeyEvent.VK_SHIFT) {temObject.dash = true;}   
        
        if(key == KeyEvent.VK_W) {temObject.setVelY(-2); keyDown[0]= true;}
        if(key == KeyEvent.VK_A) {temObject.setVelX(-2); keyDown[1]= true;}
        if(key == KeyEvent.VK_S) {temObject.setVelY(+2); keyDown[2]= true;}
        if(key == KeyEvent.VK_D) {temObject.setVelX(+2); keyDown[3]= true;}                                                                   //KeyEvents falls es ein spieler ist(Die Unterscheidung ist wichtig, falls mann mehrspieler-Modi oder verschiedene Charaktere oder so haben will)
        /*
        if(keyDown[0]&&keyDown[1]){
        temObject.setVelY(-2); 
        temObject.setVelX(-2);
        }
        if(keyDown[1]&&keyDown[2]){
        temObject.setVelY(2); 
        temObject.setVelX(-2);
        }
        if(keyDown[2]&&keyDown[3]){
        temObject.setVelY(2); 
        temObject.setVelX(2);
        }
        if(keyDown[3]&&keyDown[0]){
        temObject.setVelY(-2); 
        temObject.setVelX(2);
        }*/        
        
        //if(key == KeyEvent.VK_UP) temObject.shoot(Direction.Up);                                    //WENN DIE TASTEN GEDR�CKT WERDEN
        //if(key == KeyEvent.VK_DOWN) temObject.shoot(Direction.Down);
        //if(key == KeyEvent.VK_LEFT) temObject.shoot(Direction.Left);
        //if(key == KeyEvent.VK_RIGHT) temObject.shoot(Direction.Right);
      }
    }
    if(key == KeyEvent.VK_ESCAPE)System.exit(1);
    //if(hud.HEALTH==0)System.exit(1);
  }
  
  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    for(int i=0;i<handler.objects.size();i++) {
      GameObject temObject = handler.objects.get(i);
      if(temObject.getID()==ID.Player) {
        //key events for player1                                          
        /*if(key == KeyEvent.VK_W) keyDown[0]= false;
        if(key == KeyEvent.VK_A) keyDown[1]= false;                   
        if(key == KeyEvent.VK_S) keyDown[2]= false;
        if(key == KeyEvent.VK_D) keyDown[3]= false;     
        
        if (!keyDown[0]&&!keyDown[2]) temObject.setVelY(0);
        if (!keyDown[1]&&!keyDown[3]) temObject.setVelX(0);
        */
        
        if(key == KeyEvent.VK_SHIFT) {temObject.dash = false;} 
        if(key == KeyEvent.VK_W) {keyDown[0]= false; temObject.velY*=-1;}
        if(key == KeyEvent.VK_A) {keyDown[1]= false; temObject.velX*=-1;}                   
        if(key == KeyEvent.VK_S) {keyDown[2]= false; temObject.velY*=-1;}
        if(key == KeyEvent.VK_D) {keyDown[3]= false; temObject.velX*=-1;}
        if (!keyDown[0]&&!keyDown[2]) temObject.setVelY(0);
        if (!keyDown[1]&&!keyDown[3]) temObject.setVelX(0);                                     //KeyEvents falls es ein spieler ist(Die Unterscheidung ist wichtig, falls mann mehrspieler-Modi oder verschiedene Charaktere oder so haben will)
      }                                                               //WENN DIE TASTEN LOSGELASSEN WERDENs
    }
  }

}
