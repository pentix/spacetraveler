package spacetraveler;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event.Type;

public class Game {

	public static void main(String args[]){
		RenderWindow hauptfenster = new RenderWindow(new VideoMode(1024, 800), "SpaceTraveler");
		hauptfenster.clear();
		
		while(hauptfenster.isOpen()){
			for(org.jsfml.window.event.Event ev : hauptfenster.pollEvents()){
        		if(ev.type == Type.CLOSED){
        			hauptfenster.close();
        		}
			}
			

			hauptfenster.display();
		}
	}
	
}
