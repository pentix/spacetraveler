package spacetraveler;

import java.io.IOException;
import java.util.Vector;

import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

/**
 * @brief Enthält alle für ein Level notwendige Objekte (Gravitationspunkte, etc...)
 * @throws IOException Wenn Dateien nicht geladen werden können
 * */
public class Level {
	
	public Vector<SpaceObject> spaceObjects;	/**< SpaceObjects im Level */
	public Vector<Gravity> gravityFields;		/**< GravityFields im Level */
	
	public Clock levelTimer;					/**< Timer, der Zeit seit Beginn hochzählt */
	public float levelTimeAvailable;			/**< Zeit, die für das Level zur Verfügung steht */
	
	public Vector2f levelStart, levelZiel; 		/**< Level Start- und Zielpunkt */
	 
	
	
	
	public Level() throws IOException{
	
		// Class Member initialisieren
		spaceObjects = new Vector<>();
		gravityFields = new Vector<>();
		
		levelTimer = new Clock();
		levelTimeAvailable = 30;
		
		levelStart = new Vector2f(100, 100);
		levelZiel = new Vector2f(600, 600);
		
		
		// Spieler erstellen    (Spieler == 1. Spaceobject)
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/spieler.png", 5.0f, new Vector2f(50, 0), levelStart, true));
				
		// Beispieldaten spaceObjects
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/asteroid.png", 5.0f, new Vector2f(50, 0), new Vector2f(200, 200), true));
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/asteroid.png", 5.0f, new Vector2f(50, 0), new Vector2f(100, 200), true));
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/asteroid.png", 5.0f, new Vector2f(50, 0), new Vector2f(250, 200), true));
		spaceObjects.get(1).addAngularMomentum(15);

		
		// Beispieldaten gravityFields
		//gravityFields.addElement(new Gravity(new Vector2f(300,300), 5));
		//gravityFields.addElement(new Gravity(new Vector2f(500,300), 5));
		//gravityFields.addElement(new Gravity(new Vector2f(1200,400), 10));

	}
	
	
}
