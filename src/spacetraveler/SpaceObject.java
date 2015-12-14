package spacetraveler;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

public class SpaceObject {

	public Texture texture;
	public Sprite sprite;
	public SpaceObjectModel model;
	
	
	public SpaceObject(String texturePath, float m, Vector2f energy, Vector2f pos, boolean gravityOn) throws IOException
	{
		texture = new Texture();
		texture.loadFromFile(Paths.get(texturePath));
		
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getSize().x/2, texture.getSize().y/2);
		sprite.setPosition(pos);
		
		model = new SpaceObjectModel(m, energy, gravityOn);
	}
	
	public void move(){
		 
		this.sprite.move(model.getVelocity());
	}
 

	
	
	
	public Sprite getSprite(){
		return this.sprite;
	}
	
}
