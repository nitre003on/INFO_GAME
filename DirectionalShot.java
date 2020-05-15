import java.awt.Color;

public class DirectionalShot extends Shot{
	
	private Vector2 dir = new Vector2(0,0);
	private GameObject owner;
	private float speed, despawnTimer = 100;
	
	public DirectionalShot(GameObject owner, Vector2 target, ID id, Handler handler) {
		super((int)owner.x, (int)owner.y, null, id, handler);
		dir = Vector2.subtract(target, Vector2.getPos(owner));
		speed = 10;
		this.owner = owner;
		dir.normalize();
	}
	
	public void tick() {
		x += dir.x * speed;
		y += dir.y * speed;
		handler.addObject(new BasicTrail((int)x+4, (int)y+4, ID.Trail, Color.blue, 8, 8, 0.08f, handler));
		if(despawnTimer < 0){handler.removeObject(this);}
		else{despawnTimer--;}
	}
}
