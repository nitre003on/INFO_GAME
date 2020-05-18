
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
  
  public boolean rightClick;

  private Handler handler;
  
  public MouseInput(Handler handler) {
    this.handler = handler;
  }
  
  public void mousePressed(MouseEvent e) {
    handler.addObject(new DirectionalShot(Game.player,new Vector2(e.getX() - Game.cam.getX(),e.getY() - Game.cam.getY()),ID.Shot,handler));
  }
}
