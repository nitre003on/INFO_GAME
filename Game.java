
import java.awt.Canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.renderable.RenderableImage;
import java.util.Random;

public class Game extends Canvas implements Runnable
{
	
	private static final long serialVersionUID = 358011174883387846L;

	public static final int WIDTH = 2*640, HEIGHT = WIDTH / 12*9;
	
	private Thread thread;
	private boolean running = false;
	
	private Random random;
	private Handler handler;
	private HUD hud;
	private Spawn spawn;
	
	public Game() 
	{
		handler =  new Handler();
		this.addKeyListener(new KeyInput(handler));
		new Windows(WIDTH, HEIGHT, "SNAKE", this);
		hud = new HUD();
		spawn = new Spawn(handler, hud);
		random = new Random();
		handler.addObject(new Player((WIDTH/2)-16, (HEIGHT/2)-16, ID.Player, handler, Direction.None));
		//handler.addObject(new Player((WIDTH/2)+16, (HEIGHT/2)+16, ID.Player, handler, Direction.None));
		//handler.addObject(new Wall(100, 200, ID.Wall, handler, 20, 400));
		//handler.addObject(new BasicEnemy(Game.ranInt(17, WIDTH-17), Game.ranInt(17, HEIGHT-17), ID.BasicEnemy, handler));					//Hier werden alle Objekte das erste mal gespawnt
		//handler.addObject(new FastEnemy(Game.ranInt(17, WIDTH-17), Game.ranInt(17, HEIGHT-17), ID.FastEnemy, handler));
		//handler.addObject(new SmartEnemy(Game.ranInt(17, WIDTH-17), Game.ranInt(17, HEIGHT-17), ID.SmartEnemy, handler));
		
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
			}																						// GAME LOOP
			if(running)
			{
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				//System.out.println("FPS: "+ frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() 
	{
		handler.tick();
		hud.tick();																							//Hier werden alle Tickmethoden(bzw. im handler dann) aufgerufen
		spawn.tick();
	}
	
	private void render() 
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);																				//selbe wie bei den tick-Methoden, au�erdem wird das eigentliche Fenster gezeigt
		
		hud.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var>=max) {
			return var = max;
		}else if(var<=min) {																		//durch diese Methode wird var nicht au�erhalb der Grenzen von min un max gelassen
			return var = min;
		}else {
			return var;
		}
	}
	
	public static int ranInt(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();																	//zuf�llige zahl zwischen min und max
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static void main(String args[]) 
	{
		new Game();
	}
}
