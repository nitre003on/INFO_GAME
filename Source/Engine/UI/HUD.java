package Source.Engine.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.World.Game;
import Source.World.GameObject;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class HUD {
  
  public static float HEALTH = 10;                         //Leben
  public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
  LinkedList<GameObject> UIobj = new LinkedList<GameObject>();
  
  private Handler handler;
  private float greenValue = 255;
  private float lengthOfHealth;
  private int score = 0;
  private int level = 1;                                        //Gr�nwert, score, level
  
  public void tick() {
    HEALTH = Game.clamp(HEALTH, 0, 100);
    greenValue = Game.clamp(greenValue, 0, 255);                            //Gr�nwert und Leben werden nur in ihren Grenzen bleiben
    greenValue = HEALTH*2;
    lengthOfHealth = Game.clamp(lengthOfHealth, 0, 200);
    lengthOfHealth = HEALTH*2;
    //score++;
    
    for(int i = 0; i<UIobj.size(); i++) {
      GameObject tempObject = UIobj.get(i);
      tempObject.tick();
    }
  }
  
  public void render(Graphics g) {
    g.setColor(Color.gray);
    g.fillRect(15, 15, 200, 32);
    g.setColor(new Color(175,(int)greenValue,0));                         // das Darstellen von Leben und Level
    g.fillRect(15, 15, (int)lengthOfHealth, 32);
    g.setColor(Color.white);
    g.drawRect(15, 15, 200, 32);
    //g.drawString("Score: "+score, 15, 64);
    g.drawString("Level: "+level, 15, 80);
    g.drawString("x: " + Game.player.getX(), 15, 110);
    g.drawString("y: " + Game.player.getY(), 15, 130);
    if(Game.player.itemPicked[1])g.drawString(Game.gun.magazin + "/" + Game.gun.totalAmmo, 1800, 950);
    if(Game.player.itemPicked[2])g.drawString(Game.shotgun.magazin + "/" + Game.shotgun.totalAmmo, 1800, 1000);
    if(Game.player.itemPicked[3])g.drawString(Integer.toString(Game.scroll.uses),100, 1000); 
    
    for (int i = 0; i < UIobj.size(); i++) {
      GameObject tempObject = UIobj.get(i);
      tempObject.render(g);                             // Hier alle rendermethoden
    }
  }
  
  public void drawPause(){
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    button resume = new resumeButton(width / 2 - width / 4 / 2,(int)(height * 0.5),width / 4,50,ID.UI, handler);
    button exit = new exitButton(width / 2 - width / 4 / 2,(int)(height * 0.6),width / 4,50,ID.UI, handler);
    addObject(new UIImage(width / 2 - 250, (int)(height * 0.2), 500, 250, ID.UI, "Content/UI/Pause.png", handler));
    addObject(exit);
    addObject(resume);
  }
    
  public void clearQueue(){UIobj.clear();}
    
  public void score(int score) {
    this.score = score;
  }
  public int getScore() {
    return score;                                                         //Get und Setmethoden f�r Level und Score
  }
  public int getLevel() {
    return level;
  }
  public void setLevel(int level) {
    this.level = level;
  }
    
  public void addObject(GameObject object) {
    this.UIobj.add(object);
  }
  public void removeObject(GameObject object) {
    this.UIobj.remove(object);
  }
}
