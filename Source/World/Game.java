package Source.World;

import java.awt.Canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import Source.Engine.Direction;
import Source.Engine.DungeonGeneration;
import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Windows;
import Source.Engine.Input.KeyInput;
import Source.Engine.Input.MouseInput;
import Source.Engine.UI.HUD;
import Source.World.GameObjects.Player;
import Source.World.GameObjects.Door;
import Source.World.GameObjects.Items.Gun;
import Source.World.GameObjects.Items.HealingPotionM;

public class Game extends Canvas implements Runnable
{
  
  private static final long serialVersionUID = 358011174883387846L;
  
  public static final int WIDTH = 20000, HEIGHT = 20000;              //Höhe und Breite des Gesamten Spielfeldes
  public static Player player;
  public static Camera cam;
  public static Handler handler;
  public static HUD hud;
  public static states curState;
  public static enum states{play,paused,menu};
  public static boolean leftMousePressed;

  public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
  public static int ScreenWidth = gd.getDisplayMode().getWidth();
  public static int ScreenHeight = gd.getDisplayMode().getHeight();
  
  private static int targetFPS = 60;                         //FPS cap
  private static int targetTime = 1000000000 / targetFPS;    //Für die FPS cap
  
  private Thread thread;
  private boolean running = false;
  
  public static boolean debug = false;        //Wenn debug true ist werden alle debug funktionen ausgeführt
  
  public void play(){
    debug = false;
    curState = states.play;
    player = new Player(1150, 1150, ID.Player, handler, Direction.None);
    handler.addObject(player);
    handler.addObject(new Gun(1300, 1200, ID.Item, handler));
    handler.addObject(new HealingPotionM(1300, 1250, ID.Item, handler));
    DungeonGeneration.drawDungeon();                                                    //Zeichnen des Dungeons
    
    //Update für alle Tueren
    for (int e = 0; e < Game.handler.objects.size(); e++) {
      GameObject tempObject2 = Game.handler.objects.get(e);
      if (tempObject2 instanceof Door) {
        Door tempDoor = (Door)tempObject2;
        tempDoor.checkIfOpen();
      }
    }
  }


  public Game() 
  {
    hud = new HUD();
    curState = states.menu;
    handler =  new Handler();
    cam = new Camera(0, 0);                         //Kamera wird initialisiert
    this.addKeyListener(new KeyInput(handler));
    this.addMouseListener(new MouseInput(handler));
    new Windows(WIDTH, HEIGHT, "Dungeon Crawler", this);
    hud.drawMenu(this);
  }
  
  public synchronized void start() 
  {
    thread = new Thread(this);
    thread.start();
    running = true;
  }
  
  public synchronized void stop() 
  {
    try 
    {
      thread.join();
      running = false;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void run() 
  {
    this.requestFocus();
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0;
    double ns = 1000000000 / amountOfTicks;
    double delta = 0;
    long timer = System.currentTimeMillis();
    int frames = 0;
    while(running)
    {
      long now = System.nanoTime();
      delta += (now - lastTime)/ ns;
      lastTime = now;
      while(delta >= 1)
      {
        tick();
        delta--;
      }                                           // GAME LOOP
      if(running)
      {
        render();
      }
      frames++;
      
      if(System.currentTimeMillis() - timer > 1000)
      {
        timer += 1000;
        if (debug == true) {
          System.out.println("FPS: "+ frames);
        }
        frames = 0;
      }
    }
    stop();
  }
  
  private void tick() 
  {
    hud.tick();
    if(curState == states.play){
      handler.tick();                                             //Hier werden alle Tickmethoden(bzw. im handler dann) aufgerufen
      //spawn.tick();
      for (int i = 0;i < handler.objects.size();i++) {        
        if(handler.objects.get(i).getID() == ID.Player){
          cam.tick(handler.objects.get(i));
        }
      }
    }
  }
  
  private void render() 
  {
    long startTime = System.nanoTime();  //Für die FPS cap
    
    BufferStrategy bs = this.getBufferStrategy();
    if(bs == null) {
      this.createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();
    
    Graphics2D g2d = (Graphics2D) g;
    
    g.setColor(Color.black);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    
    g2d.translate(cam.getX(), cam.getY());  //Start von cam
    
    handler.render(g);                                                           //selbe wie bei den tick-Methoden, au�erdem wird das eigentliche Fenster gezeigt
    
    g2d.translate(-cam.getX(), -cam.getY()); //Ende von cam                                     
    
    hud.render(g);
    
    g.dispose();
    bs.show();
    
    long totalTime = System.nanoTime() - startTime;     //Misst die Zeit für Alles was seit dem letzen Frame passiert ist
    
    if (totalTime < targetTime){  //FPS cap, pausiert das Spiel bis die gewünschte Länge des Frames erreicht ist
      try {   
        Thread.sleep((targetTime - totalTime) / 1000000);
      } 
      catch (InterruptedException e){
        e.printStackTrace();
      }
    }
  }
  
  public static float clamp(float var, float min, float max) {
    if(var>=max) {
      return var = max;
    }else if(var<=min) {                                    //durch diese Methode wird var nicht au�erhalb der Grenzen von min un max gelassen
      return var = min;
    }else {
      return var;
    }
  }
  
  public static int ranInt(int min, int max) {
    
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    
    Random r = new Random();                                  //zuf�llige zahl zwischen min und max
    return r.nextInt((max - min) + 1) + min;
  }
  
  public static void main(String args[]) 
  {
    new Game();
  }
}
