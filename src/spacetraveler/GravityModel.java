package spacetraveler;

/**
 * @brief Klasse für rechnerische Eigenschaften der Gravitationspunkte
 */
public class GravityModel {
	public Vector center;		/**< Gravitationszentrum */
	public double m;			/**< Masse */
	
	/**
	 * @brief Konstruktor
	 * @param center Zentrum der Gravitation
	 * @param m Masse des Gravitationspunktes (= Proportional zur Anziehungskraft)
	 */
	public GravityModel(Vector center, double m){
		this.center = center;
		this.m = m;
	}
	
	/** 
	 * @brief Gibt die auf ein Graviton wirkende Energie aus
	 * 
	 * Berechnet also die Auswirkungen der Gravitation auf das Objekt s
	 * @param s SpaceObject für das die Energieauswirkungen berechnet werden sollen
	 * @return Energievektor, der dem SpaceObject hinzugefügt werden kann
	 */
	public Vector getEnergy(Graviton s){
		Vector gravVector = Vector.sub(center, s.getPosition());
		
		double dist = gravVector.abs();
		gravVector.toUnitVector();
	
		return Vector.mul(gravVector, Graviton.G * m*s.getMass()/dist);
	}
}
