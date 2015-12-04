package spacetraveler;

import org.jsfml.system.*;

public class SpaceObjectModel {
	private Vector2f v;		// Bewegungsrichtung / Geschwindigkeit
	private Vector2f e;		// Energievektor
	private float m;		// Masse
	
	
	
	private void updateVelocity(){		
		float v_abs = (float)(2*Math.sqrt(e.x*e.x + e.y*e.y)/m);
		Vector2f e0 = Vector2f.div(e,(float)(Math.sqrt(e.x*e.x + e.y*e.y)));
		this.v = Vector2f.mul(e0, v_abs);
	}
	
//	private Vector2f getEnergy(){
//		// Invers zu getVelocity
//		// return ...
//	}
	
	
	
	
	public void addEnergy(Vector2f energy){
		this.e = Vector2f.add(this.e, energy);
		updateVelocity();
	}
	
	public SpaceObjectModel(float m, Vector2f energy){
		this.m = m;
		this.e = energy;
		
		updateVelocity();
	}
	

	
}
