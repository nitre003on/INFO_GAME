

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
  
  public static float HEALTH = 100;                         //Leben
  
  private float greenValue = 255;
  private int score = 0;
  private int level = 1;                                        //Gr�nwert, score, level
  
  public void tick() {
    HEALTH = Game.clamp(HEALTH, 0, 100);
    greenValue = Game.clamp(greenValue, 0, 255);                            //Gr�nwert und Leben werden nur in ihren Grenzen bleiben
    greenValue = HEALTH*2;
    //score++;
    
  }
  
  public void render(Graphics g) {
    g.setColor(Color.gray);
    g.fillRect(15, 15, 200, 32);
    g.setColor(new Color(175,(int)greenValue,0));                         // das Darstellen von Leben und Level
    g.fillRect(15, 15, (int)HEALTH*2, 32);
    g.setColor(Color.white);
    g.drawRect(15, 15, 200, 32);
    //g.drawString("Score: "+score, 15, 64);
    g.drawString("Level: "+level, 15, 80);
  }

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
}
