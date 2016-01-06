package spacetraveler;

import java.io.IOException;
import java.util.Vector;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.Event.*;


/**
 * @brief Gameklasse. Enthält die main()-Methode
 * 
 */
public class Game {
	
	/**
	 * @brief Main-Methode des ganzen Spiels
	 * @param args Konsolenargumente, die dem Programm übergeben werden. (Werden nicht ausgewertet)
	 */
	public static void main(String args[]) throws InterruptedException, IOException{
		Vector<SpaceObject> spaceObjects = new Vector<>();
		Vector<Gravity> gravityFields = new Vector<>();
		
		RenderWindow hauptfenster = new RenderWindow(new VideoMode(1200, 800), "SpaceTraveler", Window.TITLEBAR | Window.CLOSE);
		hauptfenster.clear();
		
		hauptfenster.setPosition(new Vector2i(-10,0));

		//Get the window's default view
		ConstView defaultView = hauptfenster.getView();

		//Create a new view by copying the window's default view
		View view = new View(defaultView.getCenter(), defaultView.getSize());
		
		// Background Image
		Texture backgroundTexture = new Texture();
		backgroundTexture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/background.png"));
		Sprite backgroundSprite = new Sprite(backgroundTexture);
		
		hauptfenster.setView(view);
		
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/block.png", 5.0f, new Vector2f(50, 0), new Vector2f(100, 100), true));
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/asteroid.png", 5.0f, new Vector2f(50, 0), new Vector2f(200, 200), false));
		spaceObjects.get(1).addAngularMomentum(15);
		
		
		//gravityFields.addElement(new Gravity(new Vector2f(300,300), 5));
		//gravityFields.addElement(new Gravity(new Vector2f(500,300), 5));
		//gravityFields.addElement(new Gravity(new Vector2f(1200,400), 10));
		
		int userGravityId = -1;
		
		while(hauptfenster.isOpen()){
			// Events verarbeiten
			for(org.jsfml.window.event.Event ev : hauptfenster.pollEvents()){
        		if(ev.type == Type.CLOSED){
        			hauptfenster.close();
        		}
        		
        		if(ev.type == Type.MOUSE_BUTTON_PRESSED){
        			gravityFields.addElement(new Gravity((hauptfenster.mapPixelToCoords(new Vector2i((int)Mouse.getPosition().x, (int)Mouse.getPosition().y))), 5));
        			userGravityId = gravityFields.size()-1;
        		}
        		
        		if(ev.type == Type.MOUSE_BUTTON_RELEASED){
        			gravityFields.remove(userGravityId);
        			userGravityId = -1;
        		}
        		
			}

			hauptfenster.clear();
			
			//gravityFields.elementAt(0).model.m = Uhr.getElapsedTime().asSeconds() % 10;
			//gravityFields.elementAt(1).model.m = Uhr.getElapsedTime().asSeconds() % 20;
			
			
			
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
			
			if(userGravityId == -1){
				view.setCenter(spaceObjects.get(0).getSprite().getPosition());
			} else {
				view.setCenter(gravityFields.get(userGravityId).getSprite().getPosition());
			}
			
			backgroundSprite.setPosition(-600, -600);
			
			hauptfenster.setView(view);

			
			// Objekte rotieren
			for(SpaceObject s : spaceObjects){
				s.getSprite().rotate(s.getAngularMomentum());
				
			}
			
			
			// Rendering
			
			// Background
			hauptfenster.draw(backgroundSprite);
			
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
