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
		Vector<Animation> explosions = new Vector<>();
		
		RenderWindow hauptfenster = new RenderWindow(new VideoMode(1200, 800), "SpaceTraveler", Window.TITLEBAR | Window.CLOSE);
		hauptfenster.clear();
		
		hauptfenster.setPosition(new Vector2i(-10,0));

		//Get the window's default view
		ConstView defaultView = hauptfenster.getView();

		//Create a new view by copying the window's default view
		View view = new View(defaultView.getCenter(), defaultView.getSize());
		
		hauptfenster.setView(view);
		
		spaceObjects.add(new SpaceObject("rsc/block.png", 5.0f, new Vector2f(0, 0), new Vector2f(100, 100), true));
		
		
		gravityFields.addElement(new Gravity(new Vector2f(300,300), 1));
		//gravityFields.addElement(new Gravity(new Vector2f(500,300), 5));
		//gravityFields.addElement(new Gravity(new Vector2f(1200,400), 10));
		
		
		explosions.addElement(new Animation("explosion", 13, 25, new Vector2f(200, 200), false));
		
		
		while(hauptfenster.isOpen()){
			// Events verarbeiten
			for(org.jsfml.window.event.Event ev : hauptfenster.pollEvents()){
        		if(ev.type == Type.CLOSED){
        			hauptfenster.close();
        		}
        		
        		if(ev.type == Type.MOUSE_BUTTON_PRESSED){
        			
        			gravityFields.addElement(new Gravity((hauptfenster.mapPixelToCoords(new Vector2i((int)Mouse.getPosition().x, (int)Mouse.getPosition().y))), 5));
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
				
				view.setCenter(s.getSprite().getPosition().x,s.getSprite().getPosition().y);
				hauptfenster.setView(view);
			}
			
			
			// Animationen
			// Explosionen
			for(Animation e : explosions){
				// Animationen, die zuletzt noch fertig gerendert wurden
				// können entfernt werden!
				if(e.isFinished()){
					explosions.remove(e);
					continue;
				}
				
				
				// Animationsschritt, wenn Zeit schon vorbei
				e.animationStep();
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
			
			// Alle Explosionen zeichnen
			for(Animation e : explosions){
				Sprite s = e.getSprite();
				
				if(s != null)
					hauptfenster.draw(s);
			}

			
			hauptfenster.display();
			Thread.sleep(1000/25);
		}
	}
	
}
