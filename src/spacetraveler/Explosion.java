package spacetraveler;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.*;
import org.jsfml.system.*;


public class Explosion {
	private ArrayList<Texture> textures;
	private Sprite sprite;
	private String name;
	private int steps;
	private float dt;
	
	private Clock clock;
	private int step;
	
	public Explosion(String name, int steps, float dt, Vector2f pos) throws IOException{
		this.name = name;
		this.steps = steps;
		this.dt = dt;
		this.clock = new Clock();
		this.textures = new ArrayList<Texture>(steps);
		this.step = 0;
		
		// Load textures
		for(int i=0; i<steps; i++){
			textures.add(i, new Texture());
			textures.get(i).loadFromFile(Paths.get("rsc/" + name + i + ".png"));
		}
		
		sprite = new Sprite(textures.get(0));
		sprite.setOrigin(textures.get(step).getSize().x/2, textures.get(step).getSize().y/2);
		sprite.setPosition(pos);
		
	}
	
	public void animationStep(){
		if(clock.getElapsedTime().asMilliseconds() > dt){
			step = (step + 1) % steps;
			sprite.setTexture(textures.get(step));
			sprite.setOrigin(textures.get(step).getSize().x/2, textures.get(step).getSize().y/2);
			clock.restart();
		}
	}
	
	
	public Sprite getSprite(){
		return sprite;
	}
}
