package spacetraveler;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * @brief Gravitationsklasse
 * 
 * Diese Klasse vereint unser Model (GravityModel) mit der SFML-Anzeige (Texture, Sprite, etc...)
 */
public class Gravity {
	public Texture texture;		/**< Textur der Klasse */
	public Sprite sprite;		/**< Sprite der Klasse */
	public GravityModel model;	/**< Rechnerisches Modell für die Gravitation */
	
	/**
	 * @brief Konstruktor
	 * @param center Zentrum der Gravitation
	 * @param m Masse des Punktes (= Proportional zur Anziehungskraft)
	 * @throws IOException Wenn Textur nicht geladen werden konnte
	 */
	public Gravity(Vector2f center, double m) throws IOException
	{
		texture = new Texture();
		texture.loadFromFile(Paths.get("rsc/gravity.png"));
		
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getSize().x/2, texture.getSize().y/2);
		sprite.setPosition(center);
		
		model = new GravityModel(center, m);
	}
	
	/**
	 * @brief getter für Sprite
	 * @return Sprite der Klasse
	 */
	public Sprite getSprite(){
		return this.sprite;
	}
	
}
