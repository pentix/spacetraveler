package spacetraveler;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

import javafx.scene.shape.Circle;

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
 
	public Circle getCircle()
	{
		Circle Kreis = new Circle();
		Kreis.setRadius(model.getRadius());
		Kreis.setCenterX(sprite.getPosition().x);
		Kreis.setCenterY(sprite.getPosition().y);
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
	
	public Sprite getSprite(){
		return this.sprite;
	}
	
}
