package spacetraveler;

import java.awt.geom.Line2D;
import java.io.IOException;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

/**
 * 
 * @brief Superklasse für alle Objekte, die sich auf dem Bildschirm bewegen können.
 * 
 * Die Klasse vereint unser Model (SpaceObjectModel) mit den SFML-Darstellungsklassen wie
 * Sprite und Texture. Im Code muss also nur ein SpaceObject erstellt werden, die immer dazugehörigen
 * anderen Klassen werden automatisch erzeugt.
 *
 */
public class SpaceObject {

	public Texture texture;			/**< @brief Textur des Spaceobjects */
	public Sprite sprite;			/**< @brief Sprite des Spaceobjects */
	public SpaceObjectModel model;	/**< @brief Model (für Berechnungen) des Spaceobjects */
	public int collided = 2;		/**< @todo collided dokumentieren */
	
	private float angularMomentum;	/**< @brief Rotationsgeschwindigkeit */
	
	/**
	 * @brief Konstruktor
	 * @param texturePath Pfad zur Textur, die für das SpaceObject verwendet werden soll
	 * @param m Masse des SpaceObjects
	 * @param energy Anfangsenergievektor des SpaceObjects
	 * @param pos Position auf dem Spielfeld
	 * @param gravityOn true, wenn das Objekt von der Gravitation beeinflusst wird
	 * @throws IOException Wenn die angegebene Textur nicht gefunden werden konnte
	 */
	public SpaceObject(String texturePath, float m, Vector2f energy, Vector2f pos, boolean gravityOn) throws IOException
	{
		texture = new Texture();
		texture.loadFromStream(Game.class.getResourceAsStream(texturePath));
		
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getSize().x/2, texture.getSize().y/2);
		sprite.setPosition(pos);
		
		model = new SpaceObjectModel(m, energy, gravityOn, texture.getSize().x);
		
		angularMomentum = 0;
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
	 * @brief Fügt dem Objekt an Rotationsgeschwindigkeit hinzu, d.h., beschleunigt das Objekt in der Rotation
	 * @param am Zusätzliche Rotationsgeschwindigkeit [Grad/s]
	 */
	public void addAngularMomentum(float am){
		this.angularMomentum += am;
	}
	
	/**
	 * @brief getter für angularMomentum
	 * @return Rotationsgeschwindigkeit [Grad/s]
	 */
	public float getAngularMomentum(){
		return this.angularMomentum;
	}
	
	/**
	 * @brief getter für verwendetes Sprite
	 * @return Verwendetes Spriteobjekt
	 */
	public Sprite getSprite(){
		return this.sprite;
	}
	
}
