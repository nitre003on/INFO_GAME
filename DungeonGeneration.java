import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.Graphics2D;

public class DungeonGeneration {
  //private static Handler handler;
  
  static int wallThicc = 20; 
       
  public static void drawDungeon() {
    //handler = new Handler();
    //Umrandung des Spielfeldes und spawnen einer debug wall
    //Game.handler.addObject(new Wall(100, 100, ID.Wall, Game.handler, 20, 200));
    Game.handler.addObject(new Wall(0, 0, ID.Wall, Game.handler, Game.WIDTH, 20));
    Game.handler.addObject(new Wall(0, Game.HEIGHT - 20, ID.Wall, Game.handler, Game.WIDTH, 20));
    Game.handler.addObject(new Wall(0, 0, ID.Wall, Game.handler, 20, Game.HEIGHT));
    Game.handler.addObject(new Wall(Game.WIDTH - 20, 0, ID.Wall, Game.handler, 20, Game.HEIGHT));
    //Erstellen des ersten Dungeons
    createDungeonLayout(300, 300, 0, 0, 0);
  }
  
  public static void createDungeonLayout(int posX, int posY, int numberOfRooms, int numberOfKeys, int roomDistribution){
    createRoomRect(posX, posY, 400, 400, new int[][]{{100, 100}, {50, 50}}, new int[][]{{250, 50}}, new int[][]{{100, 200}}, new int[][]{{100, 100}, {50, 50}});
  }
  
  public static void createRoomRect(int posX, int posY, int length, int height, int[][] nDoors, int[][] eDoors, int[][] sDoors, int[][] wDoors){
    int tempPos = 0;
    
    Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX + length - wallThicc, posY, ID.Wall, Game.handler, wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX, posY + height - wallThicc, ID.Wall, Game.handler, wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX + length - wallThicc, posY + height - wallThicc, ID.Wall, Game.handler, wallThicc, wallThicc));
    
    for (int i = 0; i < nDoors.length; i++) {
      if (tempPos + wallThicc*2 + nDoors[i][0] + nDoors[i][1] <= length) {
        Game.handler.addObject(new Wall(posX + wallThicc + tempPos, posY, ID.Wall, Game.handler, nDoors[i][0], wallThicc));
        tempPos += (nDoors[i][0] + nDoors[i][1]);
      }
    }
    if (tempPos + wallThicc*2 < length) {
      Game.handler.addObject(new Wall(posX + wallThicc + tempPos, posY, ID.Wall, Game.handler, length - (tempPos + wallThicc*2), wallThicc));
    }
    tempPos = 0;
    for (int i = 0; i < eDoors.length; i++) {
      if (tempPos + wallThicc*2 + eDoors[i][0] + eDoors[i][1] <= height) {
        Game.handler.addObject(new Wall(posX + (length - wallThicc), posY + wallThicc + tempPos, ID.Wall, Game.handler, wallThicc, eDoors[i][0]));
        tempPos += (eDoors[i][0] + eDoors[i][1]);
      }
    }
    if (tempPos + wallThicc*2 < height) {
      Game.handler.addObject(new Wall(posX + (length - wallThicc), posY + wallThicc + tempPos, ID.Wall, Game.handler, wallThicc, height - (tempPos + wallThicc*2)));
    }
    tempPos = 0;
    for (int i = 0; i < sDoors.length; i++) {
      if (tempPos + wallThicc*2 + sDoors[i][0] + sDoors[i][1] <= length) {
        Game.handler.addObject(new Wall(posX + (length - wallThicc) - tempPos - sDoors[i][0], posY + (height - wallThicc), ID.Wall, Game.handler, sDoors[i][0], wallThicc));
        tempPos += (sDoors[i][0] + sDoors[i][1]);
      }
    }
    if (tempPos + wallThicc*2 < length) {
      Game.handler.addObject(new Wall(posX + wallThicc, posY + (height - wallThicc), ID.Wall, Game.handler, length - (tempPos + wallThicc*2), wallThicc));
    }
    tempPos = 0;
    for (int i = 0; i < wDoors.length; i++) {
      if (tempPos + wallThicc*2 + wDoors[i][0] + wDoors[i][1] <= height) {
        Game.handler.addObject(new Wall(posX, posY + height - wallThicc - tempPos - wDoors[i][0], ID.Wall, Game.handler, wallThicc, wDoors[i][0]));
        tempPos += (wDoors[i][0] + wDoors[i][1]);
      }
    }
    if (tempPos + wallThicc*2 < height) {
      Game.handler.addObject(new Wall(posX, posY + wallThicc, ID.Wall, Game.handler, wallThicc, height - (tempPos + wallThicc*2)));
    }
  } 
}  

    
    
    
