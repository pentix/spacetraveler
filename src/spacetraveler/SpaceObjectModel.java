package spacetraveler;

import org.jsfml.system.*;

public class SpaceObjectModel {
	private Vector2f v;			// Bewegungsrichtung / Geschwindigkeit
	private Vector2f e;			// Energievektor
	private double m;			// Masse
	private boolean gravityOn;	// Wirkt Gravitation auf dieses Objekt? 
	
	
	private void updateVelocity(){		
		double v_abs = 2*Math.sqrt(e.x*e.x + e.y*e.y)/m;
		Vector2f e0 = Vector2f.div(e,(float)(Math.sqrt(e.x*e.x + e.y*e.y)));
		this.v = Vector2f.mul(e0, (float)v_abs);
	}
	
//	private Vector2f getEnergy(){
//		// Invers zu getVelocity
//		// return ...
//	}
	
	
	
	
	public void addEnergy(Vector2f energy){
		this.e = Vector2f.add(this.e, energy);
		updateVelocity();
	}
	
	public SpaceObjectModel(double m, Vector2f energy){
		this.m = m;
		this.e = energy;
		
		updateVelocity();
	}
	

	
}
