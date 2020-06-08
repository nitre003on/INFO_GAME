package Source.Engine.UI.Buttons;

import java.awt.Graphics;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.UI.UIImage;
import Source.Engine.UI.button;

public class exitButton extends button {

    public exitButton(int x, int y, int w, int h, ID id, Handler handler) {
      super(x, y,w,h, id, handler);
      super.img = new UIImage(x, y, w, h, id, "Content/UI/Exit.png", handler);
    }
    
    public void tick(){
      super.tick();
    }
    
    public void event(){System.exit(1);}

    public void render(Graphics g){
      super.render(g);
    }
}