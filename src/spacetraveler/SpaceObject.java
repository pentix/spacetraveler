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

	public Texture texture;			/**< Textur des Spaceobjects */
	public Sprite sprite;			/**< Sprite des Spaceobjects */
	public SpaceObjectModel model;	/**< Model (für Berechnungen) des Spaceobjects */
	public int collided = 2;
	
	private float angularMomentum;	/**< Drehmoment für Rotation */
	
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
 
	
	public CircleShape getCircle()
	{
		CircleShape Kreis = new CircleShape();
		Kreis.setRadius(model.getRadius());
		Kreis.setOrigin(sprite.getPosition().x,sprite.getPosition().y);
		return Kreis;
	}
	
	public Vector2f getCenter()
	{
		return Vector2f.add(sprite.getPosition(), new Vector2f(texture.getSize().x/2,texture.getSize().y/2));
	}
	
	public Line2D getLine()
	{
		Line2D.Float linie = new Line2D.Float(getCenter().x, getCenter().y, model.getVelocity().x, model.getVelocity().y);
		return linie;
	}
	
	/**
	 * @brief Fügt dem Objekt Rotationsenergie hinzu, d.h., beschleunigt das Objekt in der Rotation
	 * @param am 
	 */
	public void addAngularMomentum(float am){
		this.angularMomentum += am;
	}
	
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
