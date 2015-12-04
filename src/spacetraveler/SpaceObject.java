package spacetraveler;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class SpaceObject {

	public Texture texture;
	public Sprite sprite;
	public SpaceObjectModel model;
	
	
	public SpaceObject(String texturePath, float m, Vector2f energy) throws IOException
	{
		texture = new Texture();
		texture.loadFromFile(Paths.get(texturePath));
		
		sprite = new Sprite(texture);
		
		model = new SpaceObjectModel(m, energy);
	}
	

	
}
