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

import Source.Engine.Image.displayTypes;
import Source.World.Game;
import Source.World.GameObjects.Wall;
import Source.World.GameObjects.Door;

public class DungeonGeneration {
  static int wallThicc = 20;     //Allgemeine Breite der Waende
  static int doorWidth = 150;    //Allgemeine L�nge der Tueren
  
  static int id = 0; 
  
  static ArrayList<Integer> doors = new ArrayList<Integer>();   //Liste aller unbenutzten Tueren
  
  public static void drawDungeon() {
    //Umrandung des Spielfeldes
    Game.handler.addObject(new Wall(0, 0, ID.Wall, Game.handler, Game.WIDTH, 20));
    Game.handler.addObject(new Wall(0, Game.HEIGHT - 20, ID.Wall, Game.handler, Game.WIDTH, 20));
    Game.handler.addObject(new Wall(0, 0, ID.Wall, Game.handler, 20, Game.HEIGHT));
    Game.handler.addObject(new Wall(Game.WIDTH - 20, 0, ID.Wall, Game.handler, 20, Game.HEIGHT));
    //Erstellen des ersten Dungeons
    createDungeonLayout(1000, 1000, 1000, 2000, 15);
    
    if (Game.debug) {
      System.out.println(doors);
    }
  }
  
  public static int[][] makeDoorArray(int minDoors, int maxDoors, int doorPos/*1 = nord, 2 = ost, 3 = sued, 4 = west*/, int id){
    int[][] doorArray = new int[4][3];                      //Erstellen des Arrays
    double f = getRandomInt(minDoors, maxDoors);            //Anzahl der Tueren 
    int existingDoors = 0;
    for (int i = 0; i < 4; i++) {                           //Einf�gen der Tuer Positionen
      if (existingDoors < f) {                              //Wenn es noch mehr Tueren geben soll
        int c = getRandomInt(0, 1);                         
        
        if (f - existingDoors >= 4 - i) {                   //Falls es eine Tuer geben muss
          c = 1;
        }
        
        doorArray[i][0] = c;                                //Einf�gen der Tuer ins Array
        
        if (c == 1) {
          doorArray[i][1] = assignId();
          existingDoors++;
        }
      }
    }
    
    if (doorPos == 1 || doorPos == 2 || doorPos == 3 || doorPos == 4) { //Einf�gen der Tuer welche schon Existiert
      doorArray[doorPos - 1][0] = 1;
      doorArray[doorPos - 1][1] = id;
      doorArray[doorPos - 1][2] = 1;
    }                 
    
    if (Game.debug) {
      for (int i = 0; i < 4; i++) {
        for (int e = 0; e < 3; e++) {
          System.out.print(doorArray[i][e]);
        }
        System.out.println();
      }
      System.out.println();
    }
    return doorArray;
  }
  
  public static void createDungeonLayout(int posX, int posY, int minRoomSize, int maxRoomSize, int numberOfRooms){
    //createRoomRect(posX, posY, 500, 500, new int[][]{{1, 2}, {0, 0}, {1, 2}, {0, 0}});
    int minDoors = 0;
    int maxDoors = 0;
    int startRoomSize = 500;      //Groe�e des Startraumes
    int bossRoomSize = 2000;      //Groe�e des Bossraumes
    int roomDistance = maxRoomSize + 1000;      //Ermitteln des Abstands zwischen zwei R�umen sodass man sie nicht voneinander sehen kann
    
    createRoomRect(posX, posY, startRoomSize, startRoomSize, makeDoorArray(1, 4, 0, 0));   //Erstellen des Startraumes
    
    int roomPosX = posX + startRoomSize + 1000; //Position des n�chsten Raumes wird festgelegt
    int roomPosY = posY;                        
    
    //Erstellen der anderen Raeume
    for (int i = 0; i < numberOfRooms - 1; i++) {
      int u = doors.get(0);
      if (u < 2) {
        u += 2;
      }
      else {
        u -= 2;
      }
      
      if (doors.size()/2 == numberOfRooms - i) {
        minDoors = 0;
        maxDoors = 0;
      }
      else if (doors.size()/2 <= numberOfRooms - i && ((numberOfRooms - i) - doors.size()/2) <= 4) {
        minDoors = 1;
        maxDoors = (numberOfRooms - i) - doors.size()/2;
      }
      else {
        minDoors = 1;
        maxDoors = 4;
      }
      
      if (i > 0) {
        if (roomPosX + roomDistance*2 <= Game.WIDTH) {
          roomPosX += roomDistance;
        }
        else {
          roomPosX = posX;
          roomPosY += roomDistance;
        }
      }
      
      int r = getRandomInt(0, 1);
      switch (r) {
        case  0: 
          createRoomRect(roomPosX, roomPosY, 
          getRandomInt(minRoomSize, maxRoomSize), 
          getRandomInt(minRoomSize, maxRoomSize), 
          makeDoorArray(minDoors, maxDoors, u + 1, doors.get(1)));
          doors.remove(0);
          doors.remove(0);
          break;
        case  1: 
          createRoomCross(roomPosX, roomPosY, 
          getRandomInt(minRoomSize, maxRoomSize)/3, 
          getRandomInt(minRoomSize, maxRoomSize)/3, 
          makeDoorArray(minDoors, maxDoors, u + 1, doors.get(1)));
          doors.remove(0);
          doors.remove(0);
          break;
        default: 
          System.out.println("Something went wrong...");
      }
    }
    
    int r = doors.get(0);
    if (r < 2) {
      r += 2;
    }
    else {
      r -= 2;
    }
    
    if (roomPosX + bossRoomSize*2 + 2000 <= Game.WIDTH) {
      roomPosX += bossRoomSize + 1000;
    }
    else {
      roomPosY += bossRoomSize + 1000;
    }
    
    createRoomRect(roomPosX, roomPosY, 2000, 2000, makeDoorArray(0, 0, r + 1, doors.get(1)));
    doors.remove(0);
    doors.remove(0);
  }
  
  public static void createRoomRect (int posX, int posY, int length, int height, int[][] doorsFacing){
    Game.handler.addObject(new Image("Content\\Environment\\floor.png", new Vector2(posX, posY), new Vector2(height, length), 2, displayTypes.tiled, ID.Image, Game.handler));
    if (doorsFacing[0][0] == 1) {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
      
      int TPPosX = posX + length/2;
      int TPPosY = posY + wallThicc + 10;
      Game.handler.addObject(new Door(posX + (length/2 - doorWidth/2), posY, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[0][1], 0));
      
      if (doorsFacing[0][2] != 1) {
        doors.add(0);
        doors.add(doorsFacing[0][1]);
      }
      
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
      if (doorsFacing[2][2] != 1) {
        doors.add(2);
        doors.add(doorsFacing[2][1]);
      }
      
      Game.handler.addObject(new Wall(posX + length/2 + doorWidth/2, posY - wallThicc + height, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY - wallThicc + height, ID.Wall, Game.handler, length, wallThicc));
    }
    
    if (doorsFacing[1][0] == 1) {
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY, ID.Wall, Game.handler, wallThicc, height/2 - doorWidth/2));
      
      int TPPosX = posX + length - wallThicc - 10;
      int TPPosY = posY + height/2;
      Game.handler.addObject(new Door(posX + length - wallThicc, posY + height/2 - doorWidth/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[1][1], 1));
      if (doorsFacing[1][2] != 1) {
        doors.add(1);
        doors.add(doorsFacing[1][1]);
      }
      
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY + height/2 + doorWidth/2, ID.Wall, Game.handler, wallThicc, height/2 - doorWidth/2));
    }
    else {
      Game.handler.addObject(new Wall(posX + length - wallThicc, posY, ID.Wall, Game.handler, wallThicc, height));
    }
    if (doorsFacing[3][0] == 1) {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, wallThicc, height/2 - doorWidth/2)); 
      
      int TPPosX = posX + wallThicc + 10;
      int TPPosY = posY + height/2;
      Game.handler.addObject(new Door(posX, posY + height/2 - doorWidth/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[3][1], 3));
      if (doorsFacing[3][2] != 1) {
        doors.add(3);
        doors.add(doorsFacing[3][1]);
      }
      
      Game.handler.addObject(new Wall(posX, posY + height/2 + doorWidth/2, ID.Wall, Game.handler, wallThicc, height/2 - doorWidth/2));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, wallThicc, height));
    }
  }
  
  public static void createRoomCross(int posX, int posY, int length, int height, int[][] doorsFacing){
    Game.handler.addObject(new Wall(posX + length - wallThicc, posY, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length*2, posY, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length - wallThicc, posY + height*2, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length*2, posY + height*2, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX, posY + height - wallThicc, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX + length*2 + wallThicc, posY + height - wallThicc, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX, posY + height*2, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX + length*2 + wallThicc, posY + height*2, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    //Noerdliche Tuer
    if (doorsFacing[0][0] == 1) {
      Game.handler.addObject(new Wall(posX + length, posY, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
      
      int TPPosX = posX + length + (length - doorWidth)/2 + doorWidth/2;
      int TPPosY = posY + wallThicc + 10;
      Game.handler.addObject(new Door(posX + length + (length - doorWidth)/2, posY, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[0][1], 0));
      
      if (doorsFacing[0][2] != 1) {
        doors.add(0);
        doors.add(doorsFacing[0][1]);
      }
      
      Game.handler.addObject(new Wall(posX + length + (length - doorWidth)/2 + doorWidth, posY, ID.Wall, Game.handler, (length - doorWidth)/2 + wallThicc, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX + length, posY, ID.Wall, Game.handler, length, wallThicc));
    }
    //Suedliche Tuer
    if (doorsFacing[2][0] == 1) {
      Game.handler.addObject(new Wall(posX + length, posY + height*3 - wallThicc, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
      
      int TPPosX = posX + length + (length - doorWidth)/2 + doorWidth/2;
      int TPPosY = posY + height*3 - wallThicc - 10;
      Game.handler.addObject(new Door(posX + length + (length - doorWidth)/2, posY + height*3 - wallThicc, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[2][1], 2));
      if (doorsFacing[2][2] != 1) {
        doors.add(2);
        doors.add(doorsFacing[2][1]);
      }
      
      Game.handler.addObject(new Wall(posX + length + (length - doorWidth)/2 + doorWidth, posY + height*3 - wallThicc, ID.Wall, Game.handler, (length - doorWidth)/2 + wallThicc, wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX + length, posY + height*3 - wallThicc, ID.Wall, Game.handler, length, wallThicc));
    }
    //Oestliche Tuer
    if (doorsFacing[1][0] == 1) {
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height - wallThicc, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2 + wallThicc));
      
      int TPPosX = posX + length*3 - wallThicc - 10;
      int TPPosY = posY + height + (height - doorWidth)/2 + doorWidth/2;
      Game.handler.addObject(new Door(posX + length*3 - wallThicc, posY + height + (height - doorWidth)/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[1][1], 1));
      if (doorsFacing[1][2] != 1) {
        doors.add(1);
        doors.add(doorsFacing[1][1]);
      }
      
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height + (height - doorWidth)/2 + doorWidth, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2 + wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX + length*3 - wallThicc, posY + height - wallThicc, ID.Wall, Game.handler, wallThicc, height + wallThicc*2));
    }
    //Westliche Tuer
    if (doorsFacing[3][0] == 1) {
      Game.handler.addObject(new Wall(posX, posY + height - wallThicc, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2 + wallThicc));
      
      int TPPosX = posX + wallThicc + 10;
      int TPPosY = posY + height + (height - doorWidth)/2 + doorWidth/2;
      Game.handler.addObject(new Door(posX, posY + height + (height - doorWidth)/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[3][1], 3));
      if (doorsFacing[3][2] != 1) {
        doors.add(3);
        doors.add(doorsFacing[3][1]);
      }
      
      Game.handler.addObject(new Wall(posX, posY + height + (height - doorWidth)/2 + doorWidth, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2 + wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY + height - wallThicc, ID.Wall, Game.handler, wallThicc, height + wallThicc*2));
    }
  }
  
  public static void createRoomL(int posX, int posY, int length, int height, boolean[] doorsFacing){
    
  }
  
  public static int assignId(){
    id++;
    return id;
  }
  
  public static int getRandomInt(int min, int max){
    int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
    return randomNum;
  }
}
