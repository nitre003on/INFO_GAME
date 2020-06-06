package Source.Engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import Source.World.GameObject;

public class squareImage extends GameObject{
    private int width, height;

    public squareImage(Vector2 pos, Vector2 size, ID id, Handler handler){   //frag Piet bei nachfragen
        super(pos.x, pos.y,(int)size.y,(int)size.x, id, handler);
        this.height = (int)size.x;
        this.width = (int)size.y;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,16,16);                              //Grenzen werden hierdurch entnommen
    }
    public void tick(){}

    public void render(Graphics g){ g.setColor(Color.BLACK); g.fillRect((int)x, (int)y, width, height); }
}

