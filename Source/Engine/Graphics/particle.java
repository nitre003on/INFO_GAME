package Source.Engine.Graphics;

import java.awt.Graphics;
import java.awt.Rectangle;

import Source.Engine.Handler;
import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.Engine.Graphics.Image.displayTypes;
import Source.World.Game;
import Source.World.GameObject;
import java.awt.Color;

public class particle extends GameObject {

    private Vector2 velocity;
    private int lifespan, maxLifespan;
    private Color startColor, endColor;
    private Image img;
    private particleHandler ph;
    private squareImage si;

    public particle(float x, float y, int w, int h, ID id, Handler handler) {
        super(x, y, w, h, id, handler);

    }

    public particle(float x, float y, int w, int h, int lifespan, Vector2 velocity, Color startColor,Color endColor,ID id, Handler handler, particleHandler ph) {
        super(x, y, w, h, id, handler);
        this.velocity = velocity;
        this.lifespan = lifespan;
        this.maxLifespan = lifespan;
        this.startColor = startColor;
        this.endColor = endColor;
        this.ph = ph;
        img = new Image("Content/effects/hexParticle.png",new Vector2(x,y),new Vector2(16,16),0.2f,displayTypes.normal,ID.Image,Game.handler);
        float size = (float)(Math.random() * 29 + 40);
        si = new squareImage(new Vector2(x, y), new Vector2(size, size),startColor, id, handler);
        img.color = startColor;
	}

    float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }

    public void tick() {
        lifespan -= 1;
        if(lifespan < 1) ph.removeParticle(this);
        Color tempColor = new Color((int)lerp(startColor.getRed(),endColor.getRed(),(float)lifespan / maxLifespan), (int)lerp(startColor.getGreen(),endColor.getGreen(),(float)lifespan / maxLifespan), (int)lerp(startColor.getBlue(),endColor.getBlue(),(float)lifespan / maxLifespan), 255);
        img.color = tempColor;
        si.color = tempColor;
        si.x += velocity.x;
        si.y += velocity.y;
    }

    public void render(Graphics g) {
        //img.render(g);
        si.render(g);
    }

    public Rectangle getBounds() {
        return null;
    }
    
}