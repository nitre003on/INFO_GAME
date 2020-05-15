import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.Graphics2D;

public class dungeonGeneration {
  private static Handler handler;
  
  
  public static void drawDungeon() {
    handler = new Handler();
    handler.addObject(new Wall(100, 100, ID.Wall, handler, 20, 200));
    handler.addObject(new Wall(0, 0, ID.Wall, handler, 20, 20));
    handler.addObject(new Wall(0, Game.HEIGHT - 20, ID.Wall, handler, 20, 20));
    handler.addObject(new Wall(Game.WIDTH - 20, 0, ID.Wall, handler, 20, 20));
    handler.addObject(new Wall(Game.WIDTH - 20, Game.HEIGHT - 20, ID.Wall, handler, 20, 20));
  }
}   
