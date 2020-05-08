package actualGame;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import java.util.Scanner;

public class Player extends GameObject {
	
	Direction direction;
	
	public Player(int x, int y, ID id, Handler handler, Direction direction) {
		super(x, y, id, handler);
		this.direction = direction;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,32,32);																						//Methode um die Umrisse zu kriegen
	}

	public void tick() {
		x+=velX;																													//Bewegungsrichtumg
		y+=velY;																													
		x=Game.clamp(x, 0, Game.WIDTH-37);																							//das innerhalb des Fensters bleiben
		y=Game.clamp(y, 0, Game.HEIGHT-64);
		handler.addObject(new BasicTrail((int)x, (int)y, ID.Trail, Color.white, 32, 32, 0.08f, handler));							//"Schwanz" ran hängen
		collision();																												//Kollisionsmethode
	}
	
	public void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			if (tempObject.getID()==ID.BasicEnemy || tempObject.getID()==ID.FastEnemy||tempObject.getID()==ID.SmartEnemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					//collision code
					HUD.HEALTH -=2;																										//Das passiert, wenn man mit einer Art Gegner "kollidiert"(sich überschneidet)
				}
			}
			if (tempObject.getID()==ID.Wall) {
				if(getBounds().intersects(tempObject.getBounds())) {
					
					velX=0;
					velY=0;																												////Das passiert, wenn man mit einer Wand "kollidiert"(sich überschneidet)
				}
			}
		}
	}
	
	public void shoot() {
		handler.addObject(new Shot((int) x,(int) y, direction, ID.Shot, handler));																//Schuss methode(ein Schuss Object wird erstellt
	}	
	
	public void interact() { 
			x = x*(-1);
			y = y*(-1);
	}

	
	public void render(Graphics g) {
		
		/*Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		g2d.draw(getBounds());*/
		if(id == ID.Player)g.setColor(Color.white);
		g.fillRect((int)x, (int)y, 32, 32);																										// Form wird ge"zeichnet"
		
	}
	
	
}
