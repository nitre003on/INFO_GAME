import java.awt.Graphics;

public class resumeButton extends button {

    public resumeButton(int x, int y, int w, int h, ID id, Handler handler) {
      super(x, y,w,h, id, handler);
      super.txt = "resume";
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