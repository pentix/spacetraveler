package spacetraveler;

import org.jsfml.system.*;

/**
 * @brief Klasse fuer rechnerische Eigenschaften der Gravitationspunkte
 */
public class GravityModel {
	public Vector2f center;		/**< Gravitationszentrum */
	public double m;			/**< Masse */
	
	/**
	 * @brief Konstruktor
	 * @param center Zentrum der Gravitation
	 * @param m Masse des Gravitationspunktes (= Proportional zur Anziehungskraft)
	 */
	public GravityModel(Vector2f center, double m){
		this.center = center;
		this.m = m;
	}
	
	/** 
	 * @brief Gibt die auf ein SpaceObject wirkende Energie aus
	 * Berechnet also die Auswirkungen der Gravitation auf das Objekt s
	 * 
	 * @param s SpaceObject fuer das die Energieauswirkungen berechnet werden sollen
	 * @return Energievektor, der dem SpaceObject hinzugefuegt werden kann
	 */
	public Vector2f getEnergy(SpaceObject s){
		float dx = center.x-s.getSprite().getPosition().x;
		float dy = center.y-s.getSprite().getPosition().y;
		
		float abstand = (float) Math.sqrt(dx*dx + dy*dy);
		
		Vector2f gravVector = new Vector2f(dx, dy);
		
		return Vector2f.mul(gravVector, (float)(1/abstand * m));
	}
}
