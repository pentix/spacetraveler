package spacetraveler;

import java.io.IOException;

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
	public GravityModel model;	/**< Rechnerisches Modell fuer die Gravitation */
	
	/**
	 * @brief Konstruktor
	 * @param center Zentrum der Gravitation
	 * @param m Masse des Punktes (= Proportional zur Anziehungskraft)
	 * @throws IOException Wenn Textur nicht geladen werden konnte
	 */
	public Gravity(Vector2f center, double m) throws IOException
	{
		texture = new Texture();
		if(m > 0)
		{
			texture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/gravity.png"));
		}
		else
		{
			texture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/antigravity.png"));
		}
		
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getSize().x/2, texture.getSize().y/2);
		sprite.setPosition(center);
		
		model = new GravityModel(center, m);
	}
	
	/**
	 * @brief getter fuer Sprite
	 * @return Sprite der Klasse
	 */
	public Sprite getSprite(){
		return this.sprite;
	}
	
}
