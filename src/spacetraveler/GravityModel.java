package spacetraveler;

import org.jsfml.system.*;

/**
 * @brief Klasse für rechnerische Eigenschaften der Gravitationspunkte
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
	 * 
	 * Berechnet also die Auswirkungen der Gravitation auf das Objekt s
	 * @param s SpaceObject für das die Energieauswirkungen berechnet werden sollen
	 * @return Energievektor, der dem SpaceObject hinzugefügt werden kann
	 */
	public Vector2f getEnergy(SpaceObject s){
		float dx = center.x-s.getSprite().getPosition().x;
		float dy = center.y-s.getSprite().getPosition().y;
		
		float abstand = (float) Math.sqrt(dx*dx + dy*dy);
		
		Vector2f gravVector = new Vector2f(dx, dy);
		
		// Wenn Abstand->0  : Kraft->Inf.
		if(abstand*abstand > 1)
			return Vector2f.mul(gravVector, (float)(m*100/(abstand*abstand)));
		else
			return Vector2f.mul(gravVector, (float)(m*100/(1)));
	}
}
