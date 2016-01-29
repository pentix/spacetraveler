package spacetraveler;

import java.awt.geom.Ellipse2D;

import org.jsfml.graphics.CircleShape;
import org.jsfml.system.*;

/**
 * @brief Rechnerisches Modell für SpaceObjects
 */
public class SpaceObjectModel {
	private Vector2f v;			/**< @brief Bewegungsrichtung / Geschwindigkeit */
	private Vector2f e;			/**< @brief Energievektor */
	private double m;			/**< @brief Masse */
	private boolean gravityOn;	/**< @brief Wirkt Gravitation auf dieses Objekt? */
	private int radius;	
	
	/**
	 * @brief Berechnet den Geschwindigkeitsvektor anhand des Energievektors neu.
	 */
	private void updateVelocity()
	{		
		double v_abs = (2*Math.sqrt(e.x*e.x + e.y*e.y)/m);
		Vector2f e0 = Vector2f.div(e,(float)(Math.sqrt(e.x*e.x + e.y*e.y)));
		this.v = Vector2f.mul(e0, (float)v_abs);
	}
	
	
	/**
	 * @brief Fügt dem Objekt gerichtete Energie hinzu
	 * @param energy Die gerichtete Energie
	 */
	public void addEnergy(Vector2f energy){
		this.e = Vector2f.add(new Vector2f(this.e.x*0.99f, this.e.y*0.99f), energy);
		updateVelocity();
	}
	
	/**
	 * @brief Setzt die Energie des Objektes fest
	 * @param energy Neue Energie des Objektes
	 */
	public void setEnergy(Vector2f energy)
	{
		this.e = energy;
	}

	/**
	 * Setzt die neue Geschwindigkeit des Objektes fest
	 * @param velocity Neue Geschwindigkeit des Objektes
	 */
	public void setVelocity(Vector2f velocity)
	{
		this.v = velocity;
	}

	/**
	 * @brief getter des Radius
	 * @return radius des Objekts
	 */
	public float getRadius()
	{
		return radius;
	}
    
	/**
	 * getter für Energy
	 * @return Energie des Objektes
	 */
    public Vector2f getEnergy()
    {
    	return e;
    }
	
    /**
     * @brief getter für v
     * @return Geschwindigkeitsvektor v des Objekts
     */
	public Vector2f getVelocity()
	{
		return v;
	}
	 
			
	/**
	 * @brief getter für gravityOn
	 * @return Gravitation für dieses Objekt eingeschaltet?
	 */
	public boolean isGravityOn()
	{
		return gravityOn;
	}
	 
		
	/**
	 * @brief Konstruktor
	 * @param m Masse des Objekts
	 * @param energy Anfangsenergievektor des Objekts
	 * @param gravityOn Gibt an ob das Objekt von Gravitationskräften beeinflusst wird
	 */
	public SpaceObjectModel(double m, Vector2f energy, boolean gravityOn, int Radius){
		this.m = m;
		this.e = energy;
		this.gravityOn = gravityOn;
		this.radius = Radius/2;
		updateVelocity();
	}

	
}
