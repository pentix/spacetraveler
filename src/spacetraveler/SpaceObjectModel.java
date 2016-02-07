package spacetraveler;

/**
 * @brief Rechnerisches Modell für SpaceObjects
 */
public class SpaceObjectModel {
	private Vector v;
	/** < @brief Bewegungsrichtung / Geschwindigkeit */
	private Vector e;
	/** < @brief Energievektor */
	private double m;
	/** < @brief Masse */
	private boolean gravityOn;
	/** < @brief Wirkt Gravitation auf dieses Objekt? */
	private int radius;

	/**
	 * @brief Berechnet den Geschwindigkeitsvektor anhand des Energievektors
	 *        neu.
	 */
	private void updateVelocity() {
		this.v = Vector.mul(e, 2.0 / m);
		// double v_abs = (2*Math.sqrt(e.x*e.x + e.y*e.y)/m);
		// Vector2f e0 = Vector.div(e,(float)(Math.sqrt(e.x*e.x + e.y*e.y)));
		// this.v = Vector2f.mul(e0, (float)v_abs);
	}

	/**
	 * @brief Fügt dem Objekt gerichtete Energie hinzu
	 * @param energy
	 *            Die gerichtete Energie
	 */
	public void addEnergy(Vector energy) {
		this.e = Vector.add(Vector.mul(e, 0.99f), energy);
		updateVelocity();
	}

	/**
	 * @brief Setzt die Energie des Objektes fest
	 * @param energy
	 *            Neue Energie des Objektes
	 */
	public void setEnergy(Vector energy) {
		this.e = energy;
//		updateVelocity();
	}

	/**
	 * Setzt die neue Geschwindigkeit des Objektes fest
	 * 
	 * @param velocity
	 *            Neue Geschwindigkeit des Objektes
	 */
	public void setVelocity(Vector velocity) {
		this.v = velocity;
	}

	/**
	 * @brief getter des Radius
	 * @return radius des Objekts
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * getter für Energy
	 * 
	 * @return Energie des Objektes
	 */
	public Vector getEnergy() {
		return e;
	}

	/**
	 * @brief getter für v
	 * @return Geschwindigkeitsvektor v des Objekts
	 */
	public Vector getVelocity() {
		return v;
	}

	public double getMass() {
		return m;
	}

	/**
	 * @brief getter für gravityOn
	 * @return Gravitation für dieses Objekt eingeschaltet?
	 */
	public boolean isGravityOn() {
		return gravityOn;
	}

	/**
	 * @brief Konstruktor
	 * @param m
	 *            Masse des Objekts
	 * @param energy
	 *            Anfangsenergievektor des Objekts
	 * @param gravityOn
	 *            Gibt an ob das Objekt von Gravitationskräften beeinflusst wird
	 */
	public SpaceObjectModel(double m, Vector energy, boolean gravityOn, int Radius) {
		this.m = m;
		this.e = energy;
		this.gravityOn = gravityOn;
		this.radius = Radius / 2;
		updateVelocity();
	}

}
