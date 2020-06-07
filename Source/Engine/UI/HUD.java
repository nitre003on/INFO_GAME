package Source.Engine.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.UI.Buttons.exitButton;
import Source.Engine.UI.Buttons.playButton;
import Source.Engine.UI.Buttons.resumeButton;
import Source.World.Game;
import Source.World.GameObject;

public class HUD {
  
  public static float HEALTH = 100;                         //Leben
  LinkedList<GameObject> UIobj = new LinkedList<GameObject>();

  private Handler handler;
  private float greenValue = 255;
  private int score = 0;
  private int level = 1;                                        //Gr�nwert, score, level
  
  public void tick() {
    HEALTH = Game.clamp(HEALTH, 0, 100);
    greenValue = Game.clamp(greenValue, 0, 255);                            //Gr�nwert und Leben werden nur in ihren Grenzen bleiben
    greenValue = HEALTH*2;
    //score++;

    for(int i = 0; i<UIobj.size(); i++) {
      GameObject tempObject = UIobj.get(i);
      tempObject.tick();
    }
  }
  
  public void render(Graphics g) {
    for (int i = 0; i < UIobj.size(); i++) {
      GameObject tempObject = UIobj.get(i);
      tempObject.render(g);
    }
    if(Game.curState == Game.states.menu)
      return;
    g.setColor(Color.gray);
    g.fillRect(15, 15, 200, 32);
    g.setColor(new Color(175,(int)greenValue,0));                         // das Darstellen von Leben und Level
    g.fillRect(15, 15, (int)HEALTH*2, 32);
    g.setColor(Color.white);
    g.drawRect(15, 15, 200, 32);
    //g.drawString("Score: "+score, 15, 64);
    g.drawString("Level: "+level, 15, 80);
  }

  public void drawPause(){
    int width = Game.ScreenWidth;
    int height = Game.ScreenHeight;
    button resume = new resumeButton(width / 2 - 170*2 / 2,(int)(height * 0.5),170 * 2,38 * 2,ID.UI, handler);
    button exit = new exitButton(width / 2 - 170*2 / 2,(int)(height * 0.6),170 * 2,38 * 2,ID.UI, handler);
    addObject(new UIImage(0,0, width,height, ID.UI, "Content/UI/pausePng.png", handler));
    addObject(new UIImage(width / 2 - 170 * 2 / 2, (int)(height * 0.2), 170 * 2,38 * 2, ID.UI, "Content/UI/Pause.png", handler));
    addObject(exit);
    addObject(resume);
  }

  public void drawMenu(Game game){
    int width = Game.ScreenWidth;
    int height = Game.ScreenHeight;
    button exit = new exitButton(width / 3 - 170*2 / 2,(int)(height * 0.7),170 * 2,38 * 2,ID.UI, handler);
    button play = new playButton(width / 3 - 170*2 / 2,(int)(height * 0.5),170 * 2,38 * 2,ID.UI, handler, game);
    addObject(new UIImage(0,0, width,height, ID.UI, "Content/UI/GenericDungeonText.png", handler));
    addObject(exit);
    addObject(play);
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
