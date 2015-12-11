package spacetraveler;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Gravity {
	public Texture texture;
	public Sprite sprite;
	public GravityModel model;
	
	public Gravity(Vector2f center, double m) throws IOException
	{
		texture = new Texture();
		texture.loadFromFile(Paths.get("rsc/gravity.png"));
		
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getSize().x/2, texture.getSize().y/2);
		sprite.setPosition(center);
		
		model = new GravityModel(center, m);
	}
	
	
	public Sprite getSprite(){
		return this.sprite;
	}
	
}
