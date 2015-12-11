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
		Vector<Gravitation> gravityFields = new Vector<>();
		
		RenderWindow hauptfenster = new RenderWindow(new VideoMode(1024, 800), "SpaceTraveler");
		hauptfenster.clear();
		
		
		spaceObjects.add(new SpaceObject("rsc/block.png", 5.0f, new Vector2f(100, 100), new Vector2f(100, 100)));
		
		gravityFields.addElement(new Gravitation(new Vector2f(400,400), 5));
		
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
				for(Gravitation g : gravityFields){
					gesamtEnergie = Vector2f.add(gesamtEnergie, g.getEnergy(s));
				}
				
				s.model.addEnergy(gesamtEnergie);
				s.move();
			}
				
			
			// Rendering
			// Alle SpaceObjects zeichnen!
			for(SpaceObject s : spaceObjects){
				
				hauptfenster.draw(s.getSprite());

			}
			
			hauptfenster.display();
			Thread.sleep(1000/25);
		}
	}
	
}
