package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;


/**
 * 
 * @brief Darstellung der Tiles
 * 
 * Diese Klasse dient dazu eine Sprite-Instanz, als auch die dazugehörige Texture
 * zu bündeln, um sie nicht einzeln verwalten zu müssen. Diese Klasse beeinhaltet keine
 * Methoden, sondern lediglich öffentlich zugängliche Attribute. Der Konstruktor
 * dient dazu, die für die Darstellung nötigen Daten einzulesen und zu laden.
 *
 */
public class Tile {
	
	public Sprite sprite;		/**< @brief Sprite des Tiles */
	public Texture texture;		/**< @brief Texture des Tiles */
	public Vector2f position;	/**< @brief Position des Tiles */
	public boolean solid;
	public int index;
	
	/**
	 * @brief Konstruktor des Tiles: Lädt Texturdaten und erstellt das Sprite
	 * @param p Position des Tiles (in absoluten Bildschirmkoordinaten)
	 * @param index Hintergrundbild des Tiles ( = Dateiname /spacetraveler/rsc/tiles/tile_bg***.png)
	 * @throws IOException Dateizugriffsfehler
	 */
	public Tile(Vector2f p, int index) throws IOException 
	{
		this.index = index;
		
		texture = new Texture();
		texture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/tiles/tile_bg" + index + ".png"));
		sprite = new Sprite(texture);
		sprite.setPosition(p);
		
		if(index == 1)
		{
			solid = true;
		}
		else
		{
			solid = false;
		}
		
	}

}
