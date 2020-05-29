package Source.Engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.Graphics2D;
import java.util.Random;

import Source.World.Game;
import Source.World.GameObjects.Wall;

public class DungeonGeneration {
  //private static Handler handler;
  
  static int wallThicc = 20;
  static int doorWidth = 100; 
  
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
    createRoomAnyRect(posX, posY, 400, 400, new int[][]{{100, doorWidth}, {50, doorWidth}}, new int[][]{{250, doorWidth}}, new int[][]{{100, doorWidth}}, new int[][]{{100, doorWidth}, {50, doorWidth}});
    createRoomRect(posX + 500, posY, 500, 500, new boolean[]{true, true, true ,true});
    createRoomRect(posX + 500, posY + 600, 500, 500, new boolean[]{false, false, false ,false});
    createRoomCross(posX - 150, posY + 500, 200, 200, new boolean[]{false, false, false ,false});
    createRoomCross(posX - 150, posY + 1300, 200, 200, new boolean[]{true, true, true ,true});
    createRoomCross(posX + 2000, posY + 2000, 500, 500, new boolean[]{true, true, true ,true});
  }
  
  public static void createRoomAnyRect(int posX, int posY, int length, int height, int[][] nDoors, int[][] eDoors, int[][] sDoors, int[][] wDoors){
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
  
  public static void createRoomRect (int posX, int posY, int length, int height, boolean[] doorsFacing){
    if (doorsFacing[0] == true) {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
      Game.handler.addObject(new Wall(posX + length/2 + doorWidth/2, posY, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, length, wallThicc));
    }
    if (doorsFacing[2] == true) {
      Game.handler.addObject(new Wall(posX, posY - wallThicc + height, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
      Game.handler.addObject(new Wall(posX + length/2 + doorWidth/2, posY - wallThicc + height, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY - wallThicc + height, ID.Wall, Game.handler, length, wallThicc));
    }
    
    if (doorsFacing[1] == true) {
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY + wallThicc, ID.Wall, Game.handler, wallThicc, height/2 - wallThicc - doorWidth/2));
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY + height/2 + doorWidth/2, ID.Wall, Game.handler, wallThicc, height/2 - wallThicc - doorWidth/2));
    }
    else {
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY + wallThicc, ID.Wall, Game.handler, wallThicc, height - wallThicc*2));
    }
    if (doorsFacing[3] == true) {
      Game.handler.addObject(new Wall(posX, posY + wallThicc, ID.Wall, Game.handler, wallThicc, height/2 - wallThicc - doorWidth/2));
      Game.handler.addObject(new Wall(posX, posY + height/2 + doorWidth/2, ID.Wall, Game.handler, wallThicc, height/2 - wallThicc - doorWidth/2));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY + wallThicc, ID.Wall, Game.handler, wallThicc, height - wallThicc*2));
    }
  }
  
  public static void createRoomCross(int posX, int posY, int length, int height, boolean[] doorsFacing){
    Game.handler.addObject(new Wall(posX + length - wallThicc, posY, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length*2, posY, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length - wallThicc, posY + height*2, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length*2, posY + height*2, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX, posY + height - wallThicc, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX + length*2 + wallThicc, posY + height - wallThicc, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX, posY + height*2, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX + length*2 + wallThicc, posY + height*2, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    //Nördliche Tür
    if (doorsFacing[0] == true) {
      Game.handler.addObject(new Wall(posX + length, posY, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
      Game.handler.addObject(new Wall(posX + length + (length - doorWidth)/2 + doorWidth, posY, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX + length, posY, ID.Wall, Game.handler, length, wallThicc));
    }
    //Südliche Tür
    if (doorsFacing[2] == true) {
      Game.handler.addObject(new Wall(posX + length, posY + height*3 - wallThicc, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
      Game.handler.addObject(new Wall(posX + length + (length - doorWidth)/2 + doorWidth, posY + height*3 - wallThicc, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX + length, posY + height*3 - wallThicc, ID.Wall, Game.handler, length, wallThicc));
    }
    //Östliche Tür
    if (doorsFacing[1] == true) {
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2));
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height + (height - doorWidth)/2 + doorWidth, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2));
    }
    else {
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height, ID.Wall, Game.handler, wallThicc, height));
    }
    //Westliche Tür
    if (doorsFacing[3] == true) {
      Game.handler.addObject(new Wall(posX, posY + height, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2));
      Game.handler.addObject(new Wall(posX, posY + height + (height - doorWidth)/2 + doorWidth, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY + height, ID.Wall, Game.handler, wallThicc, height));
    }
  }
}  

    
    
    
