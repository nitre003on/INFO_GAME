package Source.Engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Source.Engine.Graphics.Image;
import Source.Engine.Graphics.squareImage;
import Source.Engine.Graphics.Image.displayTypes;
import Source.World.Game;
import Source.World.GameObject;
import Source.World.GameObjects.Wall;
import Source.World.GameObjects.Door;
import Source.World.GameObjects.Enemies.BasicEnemy;
import Source.World.GameObjects.Enemies.FastEnemy;
import Source.World.GameObjects.Enemies.SmartEnemy;

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
    createDungeonLayout(1000, 1000, 1000, 2000, 7);
    
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
    
    createRoomRect(posX, posY, startRoomSize, startRoomSize, makeDoorArray(1, 4, 0, 0), new int[] {}, 0);   //Erstellen des Startraumes
    
    int roomPosX = posX + startRoomSize + 1000; //Position des n�chsten Raumes wird festgelegt
    int roomPosY = posY;                        
    
    //Erstellen der anderen Raeume
    for (int i = 0; i < numberOfRooms - 1; i++) {
      //Erzeugen der Werte des Raumes
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
      int numOfEnemies = getRandomInt(1, 4);
      int[] enemyArray = new int[numOfEnemies];
      for (int f = 0; f < numOfEnemies; f++) {
        int ranEnemyType = getRandomInt(0, 2);
        enemyArray[f] = ranEnemyType;
      }
      int ranObstacle = getRandomInt(1, 2);
      
      switch (r) {
        case  0: 
          createRoomRect(roomPosX, roomPosY, 
          getRandomInt(minRoomSize, maxRoomSize), 
          getRandomInt(minRoomSize, maxRoomSize), 
          makeDoorArray(minDoors, maxDoors, u + 1, doors.get(1)),
          enemyArray,
          ranObstacle);
          doors.remove(0);
          doors.remove(0);
          break;
        case  1: 
          createRoomCross(roomPosX, roomPosY, 
          getRandomInt(minRoomSize, maxRoomSize)/3, 
          getRandomInt(minRoomSize, maxRoomSize)/3, 
          makeDoorArray(minDoors, maxDoors, u + 1, doors.get(1)),
          enemyArray,
          ranObstacle);
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
    
    createRoomRect(roomPosX, roomPosY, 2000, 2000, makeDoorArray(0, 0, r + 1, doors.get(1)), new int[] {0, 0, 0}, 0);
    doors.remove(0);
    doors.remove(0);
  }
  
  public static void createRoomRect (int posX, int posY, int length, int height, int[][] doorsFacing, int[] spawnEnemies, int obstacle){
    int[] roomBounds = {posX, posY, length, height};
    Game.handler.addObjectAsBG(new squareImage(new Vector2(posX + length, posY), new Vector2(height, 158),Color.BLACK, ID.Image, Game.handler));
    Game.handler.addObjectAsBG(new squareImage(new Vector2(posX, posY + height), new Vector2(158, length + 158),Color.BLACK, ID.Image, Game.handler));

    Game.handler.addObjectAsBG(new Image("Content\\Environment\\floor.png", new Vector2(posX, posY), new Vector2(height, length), 2, displayTypes.tiled, ID.Image, Game.handler));
    //Erzeugen des Raumes
    if (doorsFacing[0][0] == 1) {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, length/2 - doorWidth/2, wallThicc));
      
      int TPPosX = posX + length/2;
      int TPPosY = posY + wallThicc + 10;
      Game.handler.addObject(new Door(posX + (length/2 - doorWidth/2), posY, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[0][1], 0, roomBounds, false));
      
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
      Game.handler.addObject(new Door(posX + (length/2 - doorWidth/2), posY - wallThicc + height, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[2][1], 2, roomBounds, false));
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
      Game.handler.addObject(new Door(posX + length - wallThicc, posY + height/2 - doorWidth/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[1][1], 1, roomBounds, false));
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
      Game.handler.addObject(new Door(posX, posY + height/2 - doorWidth/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[3][1], 3, roomBounds, false));
      if (doorsFacing[3][2] != 1) {
        doors.add(3);
        doors.add(doorsFacing[3][1]);
      }
      
      Game.handler.addObject(new Wall(posX, posY + height/2 + doorWidth/2, ID.Wall, Game.handler, wallThicc, height/2 - doorWidth/2));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY, ID.Wall, Game.handler, wallThicc, height));
    }
    
    //Einf�gen des Obstacles
    if (obstacle != 0) {
      addObstacle(obstacle, length/4, height/4, posX + length/2, posY + height/2);
    }
    
    //Einf�gen der Gegner
    for (int i = 0; i < spawnEnemies.length; i++) {
      switch (spawnEnemies[i]) {
        case 0 :
          int ran0X = getRandomInt(wallThicc, length - wallThicc - BasicEnemy.BasicEnemySize);
          int ran0Y = getRandomInt(wallThicc, height - wallThicc - BasicEnemy.BasicEnemySize);
           
          GameObject tempEnemy0 = new BasicEnemy(posX + ran0X, posY + ran0Y, ID.BasicEnemy, Game.handler); 
          Game.handler.addEnemy(tempEnemy0);
          
          for (int t = 0; t < Game.handler.objects.size(); t++) {         
            GameObject tempObject = Game.handler.objects.get(t); 
            if(tempObject instanceof Wall) {
              while (tempEnemy0.getBounds().intersects(tempObject.getBounds())) { 
                tempEnemy0.x++;
              }
            }
          }
          break;
        case 1 : 
          int ran1X = getRandomInt(wallThicc, length - wallThicc - FastEnemy.FastEnemySize);
          int ran1Y = getRandomInt(wallThicc, height - wallThicc - FastEnemy.FastEnemySize);
          
          GameObject tempEnemy1 = new FastEnemy(posX + ran1X, posY + ran1Y, ID.FastEnemy, Game.handler);
          Game.handler.addEnemy(tempEnemy1);
          for (int t = 0; t < Game.handler.objects.size(); t++) {         
            GameObject tempObject = Game.handler.objects.get(t); 
            if(tempObject instanceof Wall) {
              while (tempEnemy1.getBounds().intersects(tempObject.getBounds())) { 
                tempEnemy1.x++;
              }
            }
          }
          break;
        case 2 :
          int ran2X = getRandomInt(wallThicc, length - wallThicc - SmartEnemy.SmartEnemySize);
          int ran2Y = getRandomInt(wallThicc, height - wallThicc - SmartEnemy.SmartEnemySize);
          
          GameObject tempEnemy2 = new SmartEnemy(posX + ran2X, posY + ran2Y, ID.SmartEnemy, Game.handler);
          Game.handler.addEnemy(tempEnemy2);
          for (int t = 0; t < Game.handler.objects.size(); t++) {         
            GameObject tempObject = Game.handler.objects.get(t); 
            if(tempObject instanceof Wall) {
              while (tempEnemy2.getBounds().intersects(tempObject.getBounds())) { 
                tempEnemy2.x++;
              }
            }
          }
          break;
        default: 
          System.out.println("Enemy does not exist... probably");
      }
    }
  }
  
  public static void createRoomCross(int posX, int posY, int length, int height, int[][] doorsFacing, int[] spawnEnemies, int obstacle){
    Game.handler.addObjectAsBG(new squareImage(new Vector2(posX, posY), new Vector2(height, length),Color.BLACK, ID.Image, Game.handler));
    Game.handler.addObjectAsBG(new squareImage(new Vector2(posX  + length*2, posY), new Vector2(height, length),Color.BLACK, ID.Image, Game.handler));
    Game.handler.addObjectAsBG(new squareImage(new Vector2(posX, posY + height*2), new Vector2(height, length),Color.BLACK, ID.Image, Game.handler));
    Game.handler.addObjectAsBG(new squareImage(new Vector2(posX + length*2, posY + height*2), new Vector2(height + 168, length + 168),Color.BLACK, ID.Image, Game.handler));
    Game.handler.addObjectAsBG(new squareImage(new Vector2(posX + length*3, posY), new Vector2(length * 3, 168),Color.BLACK, ID.Image, Game.handler));
    Game.handler.addObjectAsBG(new squareImage(new Vector2(posX, posY + height*3), new Vector2(168, height * 3),Color.BLACK, ID.Image, Game.handler));
    Game.handler.addObjectAsBG(new Image("Content\\Environment\\floor.png", new Vector2(posX, posY), new Vector2(height * 3, length * 3), 2, Source.Engine.Graphics.Image.displayTypes.tiled, ID.Image, Game.handler));
    
    Game.handler.addObject(new Wall(posX + length - wallThicc, posY, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length*2, posY, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length - wallThicc, posY + height*2, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX + length*2, posY + height*2, ID.Wall, Game.handler, wallThicc, height));
    Game.handler.addObject(new Wall(posX, posY + height - wallThicc, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX + length*2 + wallThicc, posY + height - wallThicc, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX, posY + height*2, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    Game.handler.addObject(new Wall(posX + length*2 + wallThicc, posY + height*2, ID.Wall, Game.handler, length - wallThicc, wallThicc));
    
    int[] roomBounds = {posX, posY, length*3, height*3};
    
    //Noerdliche Tuer
    if (doorsFacing[0][0] == 1) {
      Game.handler.addObject(new Wall(posX + length, posY, ID.Wall, Game.handler, (length - doorWidth)/2, wallThicc));
      
      int TPPosX = posX + length + (length - doorWidth)/2 + doorWidth/2;
      int TPPosY = posY + wallThicc + 10;
      Game.handler.addObject(new Door(posX + length + (length - doorWidth)/2, posY, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[0][1], 0, roomBounds, false));
      
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
      Game.handler.addObject(new Door(posX + length + (length - doorWidth)/2, posY + height*3 - wallThicc, ID.Door, Game.handler, doorWidth, wallThicc, TPPosX, TPPosY, doorsFacing[2][1], 2, roomBounds, false));
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
      Game.handler.addObject(new Door(posX + length*3 - wallThicc, posY + height + (height - doorWidth)/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[1][1], 1, roomBounds, false));
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
      Game.handler.addObject(new Door(posX, posY + height + (height - doorWidth)/2, ID.Door, Game.handler, wallThicc, doorWidth, TPPosX, TPPosY, doorsFacing[3][1], 3, roomBounds, false));
      if (doorsFacing[3][2] != 1) {
        doors.add(3);
        doors.add(doorsFacing[3][1]);
      }
      
      Game.handler.addObject(new Wall(posX, posY + height + (height - doorWidth)/2 + doorWidth, ID.Wall, Game.handler, wallThicc, (height - doorWidth)/2 + wallThicc));
    }
    else {
      Game.handler.addObject(new Wall(posX, posY + height - wallThicc, ID.Wall, Game.handler, wallThicc, height + wallThicc*2));
    }
    
    //Einf�gen des Obstacles
    if (obstacle != 0) {
      addObstacle(2, length/4*3, height/4*3, posX + length/2 + length, posY + height/2 + height);
    }
    
    
    //Einf�gen der Gegner
    for (int i = 0; i < spawnEnemies.length; i++) {
      switch (spawnEnemies[i]) {
        case 0 :
          int ran0X = getRandomInt(wallThicc, length*3 - wallThicc - BasicEnemy.BasicEnemySize);
          int ran0Y;
          if (ran0X < length || ran0X > length*2 - BasicEnemy.BasicEnemySize) {
            ran0Y = getRandomInt(height, height*2 - BasicEnemy.BasicEnemySize);
          }
          else {
            ran0Y = getRandomInt(wallThicc, height*3 - wallThicc - BasicEnemy.BasicEnemySize);
          }
          GameObject tempEnemy0 = new BasicEnemy(posX + ran0X, posY + ran0Y, ID.BasicEnemy, Game.handler); 
          Game.handler.addEnemy(tempEnemy0);
          
          for (int t = 0; t < Game.handler.objects.size(); t++) {         
            GameObject tempObject = Game.handler.objects.get(t); 
            if(tempObject instanceof Wall) {
              while (tempEnemy0.getBounds().intersects(tempObject.getBounds())) { 
                tempEnemy0.x++;
              }
            }
          }
          break;
        case 1 : 
          int ran1X = getRandomInt(wallThicc, length*3 - wallThicc - BasicEnemy.BasicEnemySize);
          int ran1Y;
          if (ran1X < length || ran1X > length*2 - BasicEnemy.BasicEnemySize) {
            ran1Y = getRandomInt(height, height*2 - BasicEnemy.BasicEnemySize);
          }
          else {
            ran1Y = getRandomInt(wallThicc, height*3 - wallThicc - BasicEnemy.BasicEnemySize);
          }
          GameObject tempEnemy1 = new FastEnemy(posX + ran1X, posY + ran1Y, ID.FastEnemy, Game.handler);
          Game.handler.addEnemy(tempEnemy1);
          for (int t = 0; t < Game.handler.objects.size(); t++) {         
            GameObject tempObject = Game.handler.objects.get(t); 
            if(tempObject instanceof Wall) {
              while (tempEnemy1.getBounds().intersects(tempObject.getBounds())) { 
                tempEnemy1.x++;
              }
            }
          }
          break;
        case 2 :
          int ran2X = getRandomInt(wallThicc, length*3 - wallThicc - BasicEnemy.BasicEnemySize);
          int ran2Y;
          if (ran2X < length || ran2X > length*2 - BasicEnemy.BasicEnemySize) {
            ran2Y = getRandomInt(height, height*2 - BasicEnemy.BasicEnemySize);
          }
          else {
            ran2Y = getRandomInt(wallThicc, height*3 - wallThicc - BasicEnemy.BasicEnemySize);
          }
          GameObject tempEnemy2 = new SmartEnemy(posX + ran2X, posY + ran2Y, ID.SmartEnemy, Game.handler);
          Game.handler.addEnemy(tempEnemy2);
          for (int t = 0; t < Game.handler.objects.size(); t++) {         
            GameObject tempObject = Game.handler.objects.get(t); 
            if(tempObject instanceof Wall) {
              while (tempEnemy2.getBounds().intersects(tempObject.getBounds())) { 
                tempEnemy2.x++;
              }
            }
          }
          break;
        default: 
          System.out.println("Enemy does not exist... probably");
      }
    }
  }
      
  public static void createRoomL(int posX, int posY, int length, int height, boolean[] doorsFacing){
    
  }
  
  
  public static void addObstacle(int obstacleType, int length, int height, int posX, int posY){
    switch (obstacleType) {
      case 1 : 
        Game.handler.addObject(new Wall(posX - length/2, posY - height/2, ID.Wall, Game.handler, length, height));
        break;
      case 2 : 
        Game.handler.addObject(new Wall(posX - wallThicc/2, posY - height/2, ID.Wall, Game.handler, wallThicc, height));
        Game.handler.addObject(new Wall(posX - length/2, posY - wallThicc/2, ID.Wall, Game.handler, length, wallThicc));
        break;
      default: 
        System.out.println("Kein richtiges Obstacle! Warum mache ich das �berhaupt noch...?");
    }
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
