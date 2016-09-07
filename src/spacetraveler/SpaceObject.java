package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

/**
 * 
 * @brief Superklasse fuer alle Objekte, die sich auf dem Bildschirm bewegen koennen.
 * 
 * Die Klasse vereint unser Model (SpaceObjectModel) mit den SFML-Darstellungsklassen wie
 * Sprite und Texture. Im Code muss also nur ein SpaceObject erstellt werden, die immer dazugehoerigen
 * anderen Klassen werden automatisch erzeugt.
 *
 */
public class SpaceObject {

	public Texture texture;					/**< @brief Textur des Spaceobjects */
	public Sprite sprite;					/**< @brief Sprite des Spaceobjects */
	public SpaceObjectModel model;			/**< @brief Model (fuer Berechnungen) des Spaceobjects */
	public boolean collided = false;		/**< boolean um Doppelkollisionen mit der Wand zu minimieren */
	public boolean elastisch = false;		/**< boolean um Doppelkollisionen mit anderenn Objekten zu minimieren*/
	
	private float angularMomentum;			/**< @brief Rotationsgeschwindigkeit */
	
	public Vector2f[] Bereich;				/**< @brief Array mit 5 Koordinaten zu Tiles */
	
	/**
	 * @brief Konstruktor
	 * @param texturePath Pfad zur Textur, die fuer das SpaceObject verwendet werden soll
	 * @param m Masse des SpaceObjects
	 * @param energy Anfangsenergievektor des SpaceObjects
	 * @param coord position des SpaceObjects innerhalb des Koordinatenfeldes: Feld
	 * @param pos Position auf dem Spielfeld
	 * @param gravityOn true, wenn das Objekt von der Gravitation beeinflusst wird
	 * @throws IOException Wenn die angegebene Textur nicht gefunden werden konnte
	 */
	public SpaceObject(String texturePath, float m, Vector2f energy, Vector2f coord, Vector2f pos, boolean gravityOn) throws IOException
	{
		texture = new Texture();
		texture.loadFromStream(Game.class.getResourceAsStream(texturePath));
		
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getSize().x/2, texture.getSize().y/2);
		sprite.setPosition(pos);
		
		model = new SpaceObjectModel(m, energy, gravityOn, texture.getSize().x);
		
		angularMomentum = 0;
		Bereich = new Vector2f[9];
		bereichVerschieben(coord);

	}
	
	/**
	 * @brief neubesetzung von Bereich mit neuem Zentrum
	 * Ansatz: Um die Waende ueberpruefen zu koennen ueberpruefen wir die 4 Tiles,
	 * die um das aktuelle Tile sind auf index und Kollision.
	 * Sollte es Kollidieren und ist es eine wand also index = 1,
	 * prallt der spieler ab.
	 * Ansonnsten geht er weiter und sobald sein zentrum das Tile wechselt,
	 * werden die neuen 4 Tiles ueberprueft.
	 * @param center Position innerhalb des Koordinatenfeldes Feld
	 */
	public void bereichVerschieben(Vector2f center)
	{
		Bereich[0] = center;
		Bereich[1] = new Vector2f(center.x-1,center.y); //links
		Bereich[2] = new Vector2f(center.x,center.y-1); //oben
		Bereich[3] = new Vector2f(center.x+1,center.y); //rechts
		Bereich[4] = new Vector2f(center.x,center.y+1); //unten
		Bereich[5] = new Vector2f(center.x-1,center.y-1); //links oben
		Bereich[6] = new Vector2f(center.x+1,center.y-1); //rechts oben
		Bereich[7] = new Vector2f(center.x+1,center.y-1); //rechts unten
		Bereich[8] = new Vector2f(center.x-1,center.y+1); //links unten
		}
	
	/**
	 * @brief Bewegt das Objekt aufgrund seiner aktuellen Geschwindigkeit
	 */
	public void move(){
		 
		this.sprite.move(model.getVelocity());
	}
 
	

	/**
	 * @return Globale Koordinaten der Mitte des SpaceObjects
	 */
	public Vector2f getCenter()
	{
		return Vector2f.add(sprite.getPosition(), new Vector2f(texture.getSize().x/2,texture.getSize().y/2));
	}
	
	/**
	 * @brief Fuegt dem Objekt an Rotationsgeschwindigkeit hinzu, d.h., beschleunigt das Objekt in der Rotation
	 * @param am Zusaetzliche Rotationsgeschwindigkeit [Grad/s]
	 */
	public void addAngularMomentum(float am){
		this.angularMomentum += am;
	}
	
	/**
	 * @brief getter fuer angularMomentum
	 * @return Rotationsgeschwindigkeit [Grad/s]
	 */
	public float getAngularMomentum(){
		return this.angularMomentum;
	}
	
	/**
	 * @brief getter fuer verwendetes Sprite
	 * @return Verwendetes Spriteobjekt
	 */
	public Sprite getSprite(){
		return this.sprite;
	}
	
}
