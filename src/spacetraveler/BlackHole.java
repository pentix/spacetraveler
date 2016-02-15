package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * 
 * @brief Klasse für Schwarze Loecher Klasse Gravity vererbt
 *
 */
public class BlackHole extends Gravity {
	
	public Vector2f center = this.model.center;

	/**
	 * @brief Konstruktor für Schwarze Loecher
	 * @param center Zentrum des Schwarzen Loches
	 * @param m Masse des Schwarzen Loches
	 * @throws IOException wenn die Textur nicht geladen werden kann.
	 */
	public BlackHole(Vector2f center, double m) throws IOException {
		super(center, m);
		texture = new Texture();
    	texture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/blackhole.png"));
	    sprite = new Sprite(texture);
		sprite.setOrigin(texture.getSize().x/2, texture.getSize().y/2);
		sprite.setPosition(center);
		
		//laden des GravityModels
		model = new GravityModel(center, m);
	}

}
