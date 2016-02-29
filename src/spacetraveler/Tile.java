package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;


/**
 * 
 * @brief Darstellung der Tiles
 * 
 * Diese Klasse dient dazu eine Sprite-Instanz, als auch die dazugehoerige Texture
 * zu buendeln, um sie nicht einzeln verwalten zu muessen. Diese Klasse beeinhaltet keine
 * Methoden, sondern lediglich oeffentlich zugaengliche Attribute. Der Konstruktor
 * dient dazu, die fuer die Darstellung noetigen Daten einzulesen und zu laden.
 *
 */
public class Tile {
	
	public Sprite sprite;		/**< @brief Sprite des Tiles */
	public Texture texture;		/**< @brief Texture des Tiles */
	public Vector2f position;	/**< @brief Position des Tiles */
	public boolean solid;
	public int index;
	public Vector2f center;
	public Vector2f coord;
	
	/**
	 * @brief Konstruktor des Tiles: Laedt Texturdaten und erstellt das Sprite
	 * @param p Position des Tiles (in absoluten Bildschirmkoordinaten)
	 * @param index Hintergrundbild des Tiles ( = Dateiname /spacetraveler/rsc/tiles/tile_bg***.png)
	 * @throws IOException Dateizugriffsfehler
	 */
	public Tile(Vector2f p, int index, boolean Dark) throws IOException 
	{
		this.index = index;
		
		texture = new Texture();
		if(Dark){ texture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/tiles/tile_bgW" + index + ".png")); }
		else{ texture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/tiles/tile_bg" + index + ".png")); }
		sprite = new Sprite(texture);
		this.position = new Vector2f(p.x*512, p.y*512);
		sprite.setPosition(position);
		
		center = new Vector2f(position.x+512/2, position.y +512/2);
		
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
