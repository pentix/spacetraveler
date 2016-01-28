package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Tile {
	
	public Sprite sprite;
	public Texture texture;
	public int size;
	public Vector2f position;
	
	public Tile(Vector2f p, int index) throws IOException
	{
		texture = new Texture();
		
		texture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/tile.jpg"));
		sprite = new Sprite(texture);
		sprite.setScale(0.5f,0.5f);
		
		this.position = Vector2f.mul(p, texture.getSize().x/2);
		sprite.setPosition(position);
		
	}

}
