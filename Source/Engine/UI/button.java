package Source.Engine.UI;

import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.World.Game;
import Source.World.GameObject;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Color;

public abstract class button extends GameObject {  //generische button Klasse (Piet)
    
    private int w,h;
    private Point p;
    private Color color;
    
    public String txt;
    public int txtW, txtH;
    public Color normColor = new Color(0,0,0,0);
    public Color hoverColor = new Color(50,50,50,100);
    public UIImage img;

    public button(int x, int y, int w, int h, ID id, Handler handler) {
      super(x, y, w, h, id, handler);
      this.w = w;
      this.h = h;
    }

    public Rectangle getBounds() {
      return new Rectangle((int)x,(int)y,w,h);
    }
    
    public void tick(){
      p = MouseInfo.getPointerInfo().getLocation();
      if (p.x > x &&  p.x < x + w && p.y > y && p.y < y + h){ //falls der Mauszeiger inerhalb des Buttons ist
        color = hoverColor;
        if(img != null) img.color = hoverColor;
        if(Game.leftMousePressed) event();
      }else{
        color = normColor;
        if(img != null) img.color = null;
      }
      if (img != null){
        img.setX((int)x);
        img.setY((int)y);
        img.w = w;  //ja, die Bilder werden gestretcht falls sie nicht passen
        img.h = h;
      }
    }

    public abstract void event(); //wird ausgelößt, falls der Knopf gedrückt wird
    
    public void render(Graphics g){
      g.setColor(color);
      g.fillRect((int)x, (int)y, w, h);
      g.setColor(Color.black);
      if (txt != null) g.drawString(txt, (int)x + w / 2, (int)y + h / 2);
      if (img != null) img.render(g);
    }
  
  public boolean onScreen(){
    try{
      return (x - Game.player.x + w > 0 - Game.ScreenWidth / 2 && x - Game.player.x < 0 + Game.ScreenWidth / 2 && y - Game.player.y + h > 0 - Game.ScreenHeight / 2 && y - Game.player.y < 0 + Game.ScreenHeight / 2);
    }catch(Exception e){
      return (x - Game.ScreenWidth / 2 + w > 0 - Game.ScreenWidth / 2 && x - Game.ScreenWidth / 2 < 0 + Game.ScreenWidth / 2 && y - Game.ScreenHeight / 2 + h > 0 - Game.ScreenHeight / 2 && y - Game.ScreenHeight / 2 < 0 + Game.ScreenHeight / 2);
    }
  }
}
