package spacetraveler;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.jsfml.system.*;

import javafx.scene.shape.Circle;

public class SpaceObjectModel {
	private Vector2f v;			// Bewegungsrichtung / Geschwindigkeit
	private Vector2f e;			// Energievektor
	private double m;			// Masse
	private boolean gravityOn;	// Wirkt Gravitation auf dieses Objekt? 
	
	
	private void updateVelocity(){		
		double v_abs = (2*Math.sqrt(e.x*e.x + e.y*e.y)/m);
		Vector2f e0 = Vector2f.div(e,(float)(Math.sqrt(e.x*e.x + e.y*e.y)));
		this.v = Vector2f.mul(e0, (float)v_abs);
	}
	
	public double absVelocity(){
		double v_abs = (2*Math.sqrt(e.x*e.x + e.y*e.y)/m);
		return v_abs;
	}
	
//	private Vector2f getEnergy(){
//		// Invers zu getVelocity
//		// return ...
//	}
	

	
	public double getRadius()
	{
		return 10+Math.sqrt(v.x*v.x+v.y*v.y);
	}
	
	public void addEnergy(Vector2f energy)
	{
		this.e = Vector2f.add(new Vector2f(this.e.x, this.e.y), energy);
		updateVelocity();
	}
	
	public void Kollision(Vector2f energy, Vector2f velocity)
	{
		this.e = energy;
		this.v = velocity;
	}
	
    public Vector2f getVelocity()
    {
		return v;
    }
    
    public Vector2f getEnergy()
    {
    	return e;
    }
		 	 
	public boolean isGravityOn()
	{ 
		return gravityOn;
	}
		 
	public SpaceObjectModel(double m, Vector2f energy, boolean gravityOn)
	{
	this.m = m;
	this.e = energy;
	this.gravityOn = gravityOn;
	
	updateVelocity();
	}
	

	
}
