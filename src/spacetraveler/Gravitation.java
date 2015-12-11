package spacetraveler;

import org.jsfml.system.*;

public class Gravitation {
	public Vector2f center;		// Gravitationszentrum
	public double m;			// Masse
	
	
	public Gravitation(Vector2f center, double m){
		this.center = center;
		this.m = m;
	}
	
	public Vector2f getEnergy(SpaceObject s){
		float dx = center.x-s.getSprite().getPosition().x;
		float dy = center.y-s.getSprite().getPosition().y;
		
		float abstand = (float) Math.sqrt(dx*dx + dy*dy);
		
		Vector2f gravVector = new Vector2f(dx, dy);
		
		return Vector2f.mul(gravVector, (float)(1/abstand * m));
	}
}

