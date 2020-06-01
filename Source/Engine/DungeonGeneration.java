package Source.Engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.Graphics2D;
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Source.World.Game;
import Source.World.GameObjects.Wall;
import Source.World.GameObjects.Door;

public class DungeonGeneration {
  //private static Handler handler;
  
  static int wallThicc = 20;
  static int doorWidth = 100;
  
  static int id = 0; 
  
  static ArrayList<Integer> doors = new ArrayList<Integer>();
  
  public static void drawDungeon() {
    //handler = new Handler();
    //Umrandung des Spielfeldes und spawnen einer debug wall
    //Game.handler.addObject(new Wall(100, 100, ID.Wall, Game.handler, 20, 200));
    Game.handler.addObject(new Wall(0, 0, ID.Wall, Game.handler, Game.WIDTH, 20));
    Game.handler.addObject(new Wall(0, Game.HEIGHT - 20, ID.Wall, Game.handler, Game.WIDTH, 20));
    Game.handler.addObject(new Wall(0, 0, ID.Wall, Game.handler, 20, Game.HEIGHT));
    Game.handler.addObject(new Wall(Game.WIDTH - 20, 0, ID.Wall, Game.handler, 20, Game.HEIGHT));
    //Erstellen des ersten Dungeons
    createDungeonLayout(300, 300, 500, 500, 5);
  }
  
  public static int assignId(){
    id++;
    return id;
  }
  
  public static int getRandomInt(int min, int max){
    int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
    return randomNum;
  }
  
  public static int[][] makeDoorArray(int minDoors, int maxDoors, int doorPos, int id){
    int[][] doorArray = new int[4][2];
    double f = getRandomInt(minDoors, maxDoors);
    int existingDoors = 0;
    for (int i = 0; i < f; i++) {
      int c = getRandomInt(0, 1);
      
      if (f - existingDoors < f - i) {
        c = 1;
      }
      
      if (c == 1) {
        existingDoors++;
      }
      
      doorArray[i][0] = c;
      doorArray[i][1] = assignId();
    }
    
    if (doorPos == 1 || doorPos == 2 || doorPos == 3 || doorPos == 4) {
      doorArray[doorPos - 1][0] = 1;
      doorArray[doorPos - 1][1] = id;
    }
    
    return doorArray;
  }
  
  public static void createDungeonLayout(int posX, int posY, int minRoomSize, int maxRoomSize, int numberOfRooms){
    //createRoomRect(posX, posY, 500, 500, new int[][]{{1, 2}, {0, 0}, {1, 2}, {0, 0}});
    
    createRoomRect(posX + 5000, posY, 500, 500, makeDoorArray(1, 4, 0, 0));
    
    for (int i = 0; i < numberOfRooms - 1; i++) {
      int u = doors.get(0);
      if (u < 2) {
        u += 2;
      }
      else {
        u -= 2;
      }
      
      createRoomRect(posX + 5000 + (i + 1)*(600), posY, 
      getRandomInt(minRoomSize, maxRoomSize), 
      getRandomInt(minRoomSize, maxRoomSize), 
      makeDoorArray(1, 4, u + 1, doors.get(1)));
      doors.remove(0);
      doors.remove(0);
    } 
    
    
    
    
    
    
    
    
    
    
    
    /*
    createRoomRect(posX + 500, posY, 500, 500, new boolean[]{true, true, true ,true});
    createRoomRect(posX + 500, posY + 600, 500, 500, new boolean[]{false, false, false ,false});
    createRoomCross(posX - 150, posY + 500, 200, 200, new boolean[]{false, false, false ,false});
    createRoomCross(posX - 150, posY + 1300, 200, 200, new boolean[]{true, true, true ,true});
    createRoomCross(posX + 2000, posY + 2000, 500, 500, new boolean[]{true, true, true ,true});
    */
  }
  
  public static void createRoomRect (int posX, int posY, int length, int height, int[][] doorsFacing){
    if (doorsFacing[0][0] == 1) {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
      
      int TPPosX = posX + length/2;
      int TPPosY = posY + wallThicc + 10;
      Game.handler.addObject(new Door(posX + (length/2 - doorWidth/2), posY, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[0][1], 0));
      doors.add(0);
      doors.add(doorsFacing[0][1]);
      
      Game.handler.addObject(new Wall(posX + length/2 + doorWidth/2, posY, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, length, wallThicc));
    }
    if (doorsFacing[2][0] == 1) {
      Game.handler.addObject(new Wall(posX, posY - wallThicc + height, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
      
      int TPPosX = posX + length/2;
      int TPPosY = posY - wallThicc + height - 10;
      Game.handler.addObject(new Door(posX + (length/2 - doorWidth/2), posY - wallThicc + height, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[2][1], 2));
      doors.add(2);
      doors.add(doorsFacing[2][1]);
      
      Game.handler.addObject(new Wall(posX + length/2 + doorWidth/2, posY - wallThicc + height, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY - wallThicc + height, ID.Wall, Game.handler, length, wallThicc));
    }
    
    if (doorsFacing[1][0] == 1) {
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY + wallThicc, ID.Wall, Game.handler, wallThicc, height/2 - wallThicc - doorWidth/2));
      
      int TPPosX = posX + length - wallThicc - 10;
      int TPPosY = posY + height/2;
      Game.handler.addObject(new Door(posX + length - wallThicc, posY + height/2 - doorWidth/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[1][1], 1));
      doors.add(1);
      doors.add(doorsFacing[1][1]);
      
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY + height/2 + doorWidth/2, ID.Wall, Game.handler, wallThicc, height/2 - wallThicc - doorWidth/2));
    }
    else {
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY + wallThicc, ID.Wall, Game.handler, wallThicc, height - wallThicc*2));
    }
    if (doorsFacing[3][0] == 1) {
      Game.handler.addObject(new Wall(posX, posY + wallThicc, ID.Wall, Game.handler, wallThicc, height/2 - wallThicc - doorWidth/2)); 
      
      int TPPosX = posX + wallThicc + 10;
      int TPPosY = posY + height/2;
      Game.handler.addObject(new Door(posX, posY + height/2 - doorWidth/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[3][1], 3));
      doors.add(3);
      doors.add(doorsFacing[3][1]);
      
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
    //Noerdliche Tuer
    if (doorsFacing[0] == true) {
      Game.handler.addObject(new Wall(posX + length, posY, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
      Game.handler.addObject(new Wall(posX + length + (length - doorWidth)/2 + doorWidth, posY, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX + length, posY, ID.Wall, Game.handler, length, wallThicc));
    }
    //Suedliche Tuer
    if (doorsFacing[2] == true) {
      Game.handler.addObject(new Wall(posX + length, posY + height*3 - wallThicc, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
      Game.handler.addObject(new Wall(posX + length + (length - doorWidth)/2 + doorWidth, posY + height*3 - wallThicc, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX + length, posY + height*3 - wallThicc, ID.Wall, Game.handler, length, wallThicc));
    }
    //Oestliche Tuer
    if (doorsFacing[1] == true) {
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2));
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height + (height - doorWidth)/2 + doorWidth, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2));
    }
    else {
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height, ID.Wall, Game.handler, wallThicc, height));
    }
    //Westliche Tuer
    if (doorsFacing[3] == true) {
      Game.handler.addObject(new Wall(posX, posY + height, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2));
      Game.handler.addObject(new Wall(posX, posY + height + (height - doorWidth)/2 + doorWidth, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY + height, ID.Wall, Game.handler, wallThicc, height));
    }
  }
  
  public static void createRoomL(int posX, int posY, int length, int height, boolean[] doorsFacing){
    
  }
}  

    
    
    
