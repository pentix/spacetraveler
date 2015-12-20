package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.*;
import org.jsfml.system.*;


public class Explosion {

	public Animation a;
	
	public Explosion(Vector2f pos, float magnitude) throws IOException{
		a = new Animation("explosion", 13, 25, pos, true);
	}
	
}
