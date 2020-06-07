package Source.Engine.Graphics;

import java.util.LinkedList;

import Source.Engine.ID;
import Source.Engine.Vector2;
import Source.World.Game;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Graphics;

public class particleHandler {
    
    private LinkedList<particle> particles = new LinkedList<particle>();
    private Rectangle emissionRect;
    private Vector2 startVelocity;
    private int lifespan, emissionRate,size, timer = 0;
    private Color startColor, endColor;
    private boolean active;
    private Random rand = new Random();

    public particleHandler(Rectangle emissionRect, int emissionRate, Vector2 startVelocity, int lifespan,Color startColor,Color endColor){
        this.emissionRect = emissionRect;
        this.startVelocity = startVelocity;
        this.lifespan = lifespan;
        this.emissionRate = emissionRate;
        this.startColor = startColor;
        this.endColor = endColor;

        size = 5;
    }

    public void play(){ active=true; }

    public void pause(){ active=false; }

    public void tick(){
        for(int i = 0; i<particles.size(); i++) { if(particles.get(i).onScreen())particles.get(i).tick(); }
        if(active){
            timer++;
            if(timer > emissionRate){
                timer = 0;
                addParticle( new particle((float)(rand.nextInt((int)emissionRect.getWidth()) + emissionRect.getX()), (float)(rand.nextInt((int)emissionRect.getHeight()) + emissionRect.getY()), size, size,lifespan,startVelocity,startColor,endColor, ID.Image, Game.handler, this));
            }
        }
    }

    public void render(Graphics g){
        for (int i = 0; i < particles.size(); i++) { if(particles.get(i).onScreen())particles.get(i).render(g); }
    }

    public void addParticle(particle particle) {
        this.particles.add(particle);
    }

    public void removeParticle(particle particle) {
        this.particles.remove(particle);
    }
}
         