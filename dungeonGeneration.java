import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.Graphics2D;

public class DungeonGeneration {
  //private static Handler handler;
  
  public static void drawDungeon() {
    //handler = new Handler();
    Game.handler.addObject(new Wall(100, 100, ID.Wall, Game.handler, 20, 200));
    Game.handler.addObject(new Wall(0, 0, ID.Wall, Game.handler, Game.WIDTH, 20));
    Game.handler.addObject(new Wall(0, Game.HEIGHT - 20, ID.Wall, Game.handler, Game.WIDTH, 20));
    Game.handler.addObject(new Wall(0, 0, ID.Wall, Game.handler, 20, Game.HEIGHT));
    Game.handler.addObject(new Wall(Game.WIDTH - 20, 0, ID.Wall, Game.handler, 20, Game.HEIGHT));
  }
}   
