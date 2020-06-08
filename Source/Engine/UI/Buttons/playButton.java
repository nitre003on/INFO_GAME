package Source.Engine.UI.Buttons;

import java.awt.Graphics;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.UI.UIImage;
import Source.Engine.UI.button;
import Source.World.Game;

public class playButton extends button {

  private Game game;

  public playButton(int x, int y, int w, int h, ID id, Handler handler, Game game) {
    super(x, y,w,h, id, handler);
    this.game = game;
    super.img = new UIImage(x, y, w, h, id, "Content/UI/play.png", handler);
  }
    
  public void tick(){
    super.tick();
  }
    
  public void event(){ game.play(); Game.hud.clearQueue(); }

  public void render(Graphics g){
    super.render(g);
  }
}