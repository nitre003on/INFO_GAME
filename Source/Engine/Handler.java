package Source.Engine;

import java.awt.Graphics;
import java.util.LinkedList;

import Source.World.GameObject;

public class Handler 
{
  public LinkedList<GameObject> objects = new LinkedList<GameObject>();
  public LinkedList<GameObject> enemies = new LinkedList<GameObject>();
  public void tick() {
    for(int i = 0; i<objects.size(); i++) { if(objects.get(i).onScreen() || objects.get(i).getID() == ID.Trail)objects.get(i).tick(); }                            //hier werden alle Tickmethoden getan.
    for(int i = 0; i<enemies.size(); i++) { if(enemies.get(i).inRoom())enemies.get(i).tick(); }
  }
  public void render(Graphics g) {
    for (int i = 0; i < objects.size(); i++) { if(objects.get(i).onScreen())objects.get(i).render(g); }                             // Hier alle rendermethoden 
  }
  public void addObject(GameObject object) {
    this.objects.add(object);
  }
  public void addObjectAsBG(GameObject object) {
    this.objects.add(0, object);
  }
  public void removeObject(GameObject object) {
    this.objects.remove(object);
  }
  
  public void addEnemy(GameObject object) {
    this.enemies.add(object);
  }
  
  public void removeEnemy(GameObject object) {
    this.enemies.remove(object);
  }
}

//Der handler ist daf�r da mit den Objekten um zu gehen. Also Hinzuf�gen, Entfernen. Auch Alle Objekte in einer Liste zu haben.
