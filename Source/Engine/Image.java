package Source.Engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

import Source.World.Game;
import Source.World.GameObject;
import java.awt.Color;

public class Image extends GameObject{
    private BufferedImage img;
    private int borderW, borderH,zoomLvl;
    public static enum displayTypes{normal,zoomed,stretched,tiled};
    private displayTypes displayType;

    public Image(String imgURL, Vector2 pos, Vector2 size, int zoomLvl, displayTypes displayType, ID id, Handler handler){   //frag Piet bei nachfragen
        super(pos.x, pos.y,(int)size.x,(int)size.y, id, handler);
        this.displayType = displayType;
        this.zoomLvl = zoomLvl;
        this.borderH = (int)size.x;
        this.borderW = (int)size.y;
        try { img = loadImage(imgURL); } 
        catch (IOException e) { e.printStackTrace(); }
    }
  
    private BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new FileInputStream(path));
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,16,16);                              //Grenzen werden hierdurch entnommen
    }
    public void tick(){}

    public void render(Graphics g){
        switch(displayType){
            case normal :
                g.drawImage(img, (int)x, (int)y,img.getWidth() * zoomLvl,img.getHeight() * zoomLvl, null);
                break;
            case zoomed :
                g.drawImage(img, (int)x, (int)y, null);
                break;
            case stretched :
                g.drawImage(img, (int)x, (int)y, borderW, borderH, null);
            case tiled :
                for(int i = 0; i < Math.floor(borderH / (img.getHeight() * zoomLvl)+1);i++){
                    for(int j = 0; j < Math.floor(borderW / (img.getWidth() * zoomLvl)+1);j++){
                        if((int)x + j * img.getWidth() * zoomLvl - Game.player.x + img.getWidth() * zoomLvl > 0 - Game.ScreenWidth / 2 && (int)x + j * img.getWidth() * zoomLvl - Game.player.x < 0 + Game.ScreenWidth / 2 && (int)y  + i * img.getHeight() * zoomLvl - Game.player.y + img.getHeight() * zoomLvl > 0 - Game.ScreenHeight / 2 && (int)y  + i * img.getHeight() * zoomLvl - Game.player.y < 0 + Game.ScreenHeight / 2)
                            g.drawImage(img, (int)x + j * img.getWidth() * zoomLvl,(int)y  + i * img.getHeight() * zoomLvl,img.getWidth() * zoomLvl,img.getHeight() * zoomLvl , null);
                    }
                }
                if(Game.debug){
                    g.setColor(Color.RED);
                    g.drawRect((int)x, (int)y, borderW, borderH);
                }
        }
    }

    private BufferedImage getSubimage(BufferedImage img, int x, int y, int width, int height){   //Sprite aus dem Spritesheet lesen
        return img.getSubimage(x,y,width / zoomLvl,height / zoomLvl);
    }
}

