package spacetraveler;

import java.io.IOException;
import java.util.Vector;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.Event.*;


public class Game {

	public static void main(String args[]) throws InterruptedException, IOException{
		Vector<SpaceObject> spaceObjects = new Vector<>();
		Vector<Gravity> gravityFields = new Vector<>();
		
		RenderWindow hauptfenster = new RenderWindow(new VideoMode(1924, 1200), "SpaceTraveler");
		hauptfenster.clear();
		
		
		spaceObjects.add(new SpaceObject("rsc/block.png", 5.0f, new Vector2f(0, 0), new Vector2f(100, 100), true));
		
		
		gravityFields.addElement(new Gravity(new Vector2f(400,400), 5));
		gravityFields.addElement(new Gravity(new Vector2f(1200,400), 10));
		gravityFields.addElement(new Gravity(new Vector2f(800,800), 10));
		
		while(hauptfenster.isOpen()){
			// Events verarbeiten
			for(org.jsfml.window.event.Event ev : hauptfenster.pollEvents()){
        		if(ev.type == Type.CLOSED){
        			hauptfenster.close();
        		}
			}

			hauptfenster.clear();
			
			
			
			// Berechnungen
			for(SpaceObject s : spaceObjects){
				Vector2f gesamtEnergie = new Vector2f(0, 0);
				
				if(s.model.isGravityOn()){
					for(Gravity g : gravityFields){
						gesamtEnergie = Vector2f.add(gesamtEnergie, g.model.getEnergy(s));
					}
				
					s.model.addEnergy(gesamtEnergie);
				}
				
				s.move();
			}
				
			
			// Rendering
			// Alle Gravitys zeichnen
			for(Gravity g : gravityFields){
				hauptfenster.draw(g.getSprite());
			}
			
			// Alle SpaceObjects zeichnen!
			for(SpaceObject s : spaceObjects){	
				hauptfenster.draw(s.getSprite());
			}
			

			
			hauptfenster.display();
			Thread.sleep(1000/25);
		}
	}
	
}
