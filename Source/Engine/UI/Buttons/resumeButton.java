package Source.Engine.UI.Buttons;

import java.awt.Graphics;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.UI.UIImage;
import Source.Engine.UI.button;
import Source.World.Game;

public class resumeButton extends button {

    public resumeButton(int x, int y, int w, int h, ID id, Handler handler) {
      super(x, y,w,h, id, handler);
      super.img = new UIImage(x, y, w, h, id, "Content/UI/Resume.png", handler);
    }
    
    public void tick(){
      super.tick();
    }
    
    public void event()
    { 
      Game.hud.clearQueue();
      Game.curState = Game.states.play; 
    }

    public void render(Graphics g){
      super.render(g);
    }
}