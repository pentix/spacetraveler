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
	
	/** @todo Documentation / needed?*/
	public static boolean hallo = false;
	
	public static float absVec(Vector2f v)
	{
		return (float)Math.abs(Math.sqrt(v.x*v.x+v.y*v.y));
	}
	
	public static float skalar(Vector2f a, Vector2f b)
	{
		return a.x*b.x+a.y*b.y;
	}
	
	public static Vector2f Schnittpunkt(SpaceObject A, SpaceObject B)
	{	
		float t = 0;	//Gleitpunkt
		t = (A.getCenter().x - B.getCenter().x)/(B.model.getVelocity().x-A.model.getVelocity().x);	//LGS
		return Vector2f.add(A.getCenter(), Vector2f.mul(A.model.getVelocity(), t));	//Einsetzen in die Formel (Inzidenzkriterium)
	}
	
	public static void schneiden(Vector<SpaceObject> spaceObjects)
	{
		System.out.println(spaceObjects.size());

		if(spaceObjects.size() != 0){
			for(int i = 0; i+1 <= spaceObjects.size(); i++)
			{
				SpaceObject A = spaceObjects.elementAt(i);
				A.move();
	
				for(int j = i+1; j < spaceObjects.size(); j++)
				{
					
					SpaceObject B = spaceObjects.elementAt(j);
					Vector2f P1 = A.getCenter();	//Zentrum 1
					Vector2f P2 = B.getCenter();	//Zentrum 2
					
					B.move();
					
					System.out.println(1);
					if(absVec(Vector2f.sub(P1,P2)) <= Math.abs(A.model.getRadius())+Math.abs(B.model.getRadius()))
					{
						/*A.getSprite().move(Vector2f.sub(A.model.getVelocity(),Vector2f.mul(A.model.getVelocity(), 2)));
						B.getSprite().move(Vector2f.sub(B.model.getVelocity(),Vector2f.mul(B.model.getVelocity(), 2)));
						float Abstand = (A.model.getRadius()+B.model.getRadius())-absVec(Vector2f.sub(P1,P2));
						float cosPhi = skalar(A.model.getVelocity(),B.model.getVelocity())/(absVec(A.model.getVelocity())+absVec(B.model.getVelocity()));
						float strecke1a = Abstand * cosPhi;
						*/
						Vector2f x0 = Vector2f.div(Vector2f.sub(P1, P2),absVec(Vector2f.sub(P1, P2)));
						Vector2f y0 = new Vector2f(-x0.y,x0.x);
						
						Vector2f Ax = Vector2f.mul(x0,skalar(A.model.getEnergy(),x0));
						Vector2f Ay = Vector2f.mul(y0,skalar(A.model.getEnergy(),y0));
						
						Vector2f Bx = Vector2f.mul(x0,skalar(B.model.getEnergy(),x0));
						Vector2f By = Vector2f.mul(y0,skalar(B.model.getEnergy(),y0));
						
						A.model.setEnergy(Vector2f.add(Bx,Ay));
						B.model.setEnergy(Vector2f.add(Ax,By));
						
						Vector2f Axv = Vector2f.mul(x0,skalar(A.model.getVelocity(),x0));
						Vector2f Ayv = Vector2f.mul(y0,skalar(A.model.getVelocity(),y0));
						
						Vector2f Bxv = Vector2f.mul(x0,skalar(B.model.getVelocity(),x0));
						Vector2f Byv = Vector2f.mul(y0,skalar(B.model.getVelocity(),y0));
						
						A.model.setVelocity(Vector2f.add(Bxv,Ayv));
						B.model.setVelocity(Vector2f.add(Axv,Byv));
						
						System.out.println(2);
						A.move();
						B.move();
						
						A.collided = 2;
						B.collided = 2;
						hallo = true;
					}
					else
					{
						B.sprite.move(Vector2f.mul(B.model.getVelocity(),-1));
					}
					
					
					
					/*boolean texCol = (Math.abs(A.getCenter().x - B.getCenter().x) 
							<= A.getSprite().getTexture().getSize().x/2 
								+ B.getSprite().getTexture().getSize().x/2)
						  && (Math.abs(A.getCenter().y - B.getCenter().y)
						  	<= A.getSprite().getTexture().getSize().y/2
						  		+ B.getSprite().getTexture().getSize().y/2);
					
					
					if(A.getCircle(). && A.getLine().intersectsLine(B.getLine()) || texCol)
					{
						//System.out.println(1);
						Vector2f P1 = A.getCenter();	//Zentrum 1
						Vector2f P2 = B.getCenter();	//Zentrum 2
						Vector2f P = Schnittpunkt(A, B);
						Vector2f F1 = Vector2f.add(P1, A.model.getVelocity());
						Vector2f F2 = Vector2f.add(P2, B.model.getVelocity());
						 
						
						
						FloatRect R1 = new FloatRect(P1, A.model.getVelocity());
						FloatRect R2 = new FloatRect(P2, B.model.getVelocity());
						
						Vector2f v = Vector2f.sub(P, P1);
						Vector2f verh1 = Vector2f.add(v,Vector2f.mul(Vector2f.div(v,absVec(v)),(float)(A.getSprite().getLocalBounds().width*(Math.sqrt(2)/2))));
						Vector2f verh2 = Vector2f.sub(v,Vector2f.mul(Vector2f.div(v,absVec(v)),(float)(A.getSprite().getLocalBounds().width*(Math.sqrt(2)/2))));
						Vector2f verh3 = Vector2f.sub(P, P2);
												

						if(((absVec(verh2)/absVec(A.model.getVelocity()))
								<= absVec(verh3)/absVec(B.model.getVelocity())
								&& absVec(verh3)/absVec(B.model.getVelocity())
								<= absVec(verh1)/absVec(A.model.getVelocity()))){
						if((R1.contains(P) && R2.contains(P)) || absVec(Vector2f.sub(P1, P2)) <= (A.getSprite().getLocalBounds().width/2+B.getSprite().getLocalBounds().width+2) || texCol)
						{
							hallo = true;
							Vector2f v1a = Vector2f.sub(P, P1);
							Vector2f v1b = Vector2f.sub(F2, P);
							Vector2f v2a = Vector2f.sub(P, P2);
							Vector2f v2b = Vector2f.sub(F1, P);
							
							Vector2f v1 = Vector2f.add(v1a, v1b);
							Vector2f v2 = Vector2f.add(v2a, v2b);
							
							A.getSprite().move(v1);
							B.getSprite().move(v2);
							
							Vector2f e_1 = new Vector2f(A.model.getEnergy().x*0.5f, A.model.getEnergy().y*0.5f);
							Vector2f v_1 = new Vector2f(A.model.getVelocity().x * 0.5f, A.model.getVelocity().y * 0.5f);
							
							Vector2f e_2 = new Vector2f(B.model.getEnergy().x*0.5f, B.model.getEnergy().y*0.5f);
							Vector2f v_2 = new Vector2f(B.model.getVelocity().x * 0.5f, B.model.getVelocity().y * 0.5f);
							
							A.model.Kollision(e_2,v_2);
							B.model.Kollision(e_1, v_1);
							//System.out.println("geht");

							
							
		
							break;
						}
						}
					}*/					
				}
				
			}
			}
		}
	
	
	
	
	
	
	
	
	
	
	/**
	 * @brief Main-Methode des ganzen Spiels
	 * @param args Konsolenargumente, die dem Programm übergeben werden. (Werden nicht ausgewertet)
	 */
	public static void main(String args[]) throws InterruptedException, IOException{
		RenderWindow hauptfenster = new RenderWindow(new VideoMode(1200, 800), "SpaceTraveler", Window.TITLEBAR | Window.CLOSE);
		hauptfenster.clear();
		hauptfenster.setPosition(new Vector2i(-10,0));

		//Get the window's default view
		ConstView defaultView = hauptfenster.getView();

		//Create a new view by copying the window's default view
		View view = new View(defaultView.getCenter(), defaultView.getSize());
		
		// Load GameOver Image
		Texture gameOverTexture = new Texture();
		gameOverTexture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/gameOver.png"));
		Sprite gameOverSprite = new Sprite(gameOverTexture);
		
		// Load font
		Font font = new Font();
		font.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/DejaVuSans.ttf"));
		
		// Anzeigetext für Zeit erstellen
		Text timeText = new Text("", font);
		timeText.setPosition(25, 25);
		
		hauptfenster.setView(view);
	
	
		
		boolean gameOver = false;		/**< true, wenn der Spieler das Spiel verloren hat */
		int userGravityId = -1;

		
		
		// Level erstellen
		Level l = new Level("level1");
		
		
		while(hauptfenster.isOpen()){
			// Events verarbeiten
			for(org.jsfml.window.event.Event ev : hauptfenster.pollEvents()){
        		if(ev.type == Type.CLOSED){
        			hauptfenster.close();
        		}
        		
        		if(ev.type == Type.MOUSE_BUTTON_PRESSED){
        			l.gravityFields.addElement(new Gravity((hauptfenster.mapPixelToCoords(new Vector2i((int)Mouse.getPosition().x, (int)Mouse.getPosition().y))), 5));
        			userGravityId = l.gravityFields.size()-1;
        		}
        		
        		if(ev.type == Type.MOUSE_BUTTON_RELEASED){
        			l.gravityFields.remove(userGravityId);
        			userGravityId = -1;
        		}
        		
			}

			hauptfenster.clear();
					
			
			if(!gameOver){
				// Berechnungen
				for(SpaceObject s : l.spaceObjects){
					Vector2f gesamtEnergie = new Vector2f(0, 0);
					
					if(s.model.isGravityOn()){
						for(Gravity g : l.gravityFields){
							gesamtEnergie = Vector2f.add(gesamtEnergie, g.model.getEnergy(s));
						}
					

					s.model.addEnergy(gesamtEnergie);
				}
			
			}
			
				schneiden(l.spaceObjects);
			
				// Überprüfen, ob die Zeit abgelaufen ist.
				if(l.levelTimer.getElapsedTime().asSeconds() > l.levelTimeAvailable){
					gameOver = true;
				}
				
				view.setCenter(l.spaceObjects.get(0).getSprite().getPosition());
				
				// Hintergrund / View gut positionieren!

				view.setCenter(l.spaceObjects.get(0).getSprite().getPosition());

				hauptfenster.setView(view);
	
				
				// Objekte rotieren
				for(SpaceObject s : l.spaceObjects){
					s.getSprite().rotate(s.getAngularMomentum());
					
				}

				
				
				// Rendering
				
				// Alle Background Tiles zeichnen
				for(Tile t : l.tiles){
					hauptfenster.draw(t.sprite);
				}
				
				// Alle Gravitys zeichnen
				for(Gravity g : l.gravityFields){
					hauptfenster.draw(g.getSprite());
				}
			
				// Alle SpaceObjects zeichnen!
				for(SpaceObject s : l.spaceObjects){	
					hauptfenster.draw(s.getSprite());
				}
				
				
				// Zeit anzeigen!
				long timePassed = l.levelTimer.getElapsedTime().asMilliseconds();
				int minutesPassed = (int)Math.floor(timePassed/1000/60);
				int secondsPassed = (int)Math.floor(timePassed/1000 - minutesPassed*60);
				int millisecondsPassed = (int)((timePassed % 1000)/10);
				
				String timeTextString = (minutesPassed<10?"0"+minutesPassed:minutesPassed) + ":" + (secondsPassed<10?"0"+secondsPassed:secondsPassed) + ":" + (millisecondsPassed<10?"0"+millisecondsPassed:millisecondsPassed);
				timeText.setString(timeTextString);
				timeText.setPosition(hauptfenster.mapPixelToCoords(new Vector2i(25, 25)));
	
				hauptfenster.draw(timeText);
				
			} else {
				// Wenn Spieler gameOver ist, Spiel anhalten, GameOver anzeigen!
				gameOverSprite.setOrigin(gameOverTexture.getSize().x/2, gameOverTexture.getSize().y/2);
				gameOverSprite.setPosition(hauptfenster.mapPixelToCoords(new Vector2i(hauptfenster.getSize().x/2, hauptfenster.getSize().y/2)));

				hauptfenster.draw(gameOverSprite);
			
			}
				
			hauptfenster.display();
			Thread.sleep(1000/25);
		}
	}
	
}
