package spacetraveler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event.*;
import org.jsfml.window.event.MouseButtonEvent;



/**
 * @brief Gameklasse. Enthält die main()-Methode
 * 
 */
public class Game {
	
	private static boolean gravLeft = false; 		/**< @brief Wird linke Maustaste gedrückt?*/
	private static boolean gravRight = false; 		/**< @brief Wird rechte Maustaste gedrückt?*/
	
	/**
	 * @brief Hilfsfunktion zum Berechnen der Länge des Vectors
	 * @param v ein Vektor
	 * @return Länge des Vektors
	 */
	public static float absVec(Vector2f v)
	{
		return (float)Math.abs(Math.sqrt(v.x*v.x+v.y*v.y));
	}
	
	/** 
	 * @brief Hilfsfunktion zum berechnen des Skalarproduktes
	 * @param a erster Vektor
	 * @param b zweiter Vektor
	 * @return Skalarprodukt der Beiden Vektoren
	 */
	public static float skalar(Vector2f a, Vector2f b)
	{
		return a.x*b.x+a.y*b.y;
	}
	
	/**
	 * @brief Funktion zum �berpr�fen ob ein Punkt in einem Floatrect ist
	 * @param f das Floatrect
	 * @param P der Punkt
	 * @return true wenn der Punkt enthalten ist, ansonsten false
	 */
	public static boolean contains(FloatRect f, Vector2f P)
	{
		Vector2f LO = new Vector2f(f.left, f.top);
		Vector2f RU = Vector2f.add(LO, new Vector2f(f.width, f.height));
		
		if(LO.x <= P.x && LO.y <= P.y && P.x <= RU.x && P.y <= RU.y)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * @brief Funktion die die �berschneidung zweier Floatrects �berp�ft
	 * @param a FloatRect a muss erheblich kleiner als b sein
	 * @param b Das gr�ssere Floatrect
	 * @return true wenn sie sich �berschneiden, ansonnsten false
	 */
	public static boolean intersection(FloatRect a, FloatRect b)
	{
		// ansatz: b ist erheblich gr�sser als a
		// dadurch muss bei einer �berschneidung immer min. ein Eckpunkt im anderen Rechteck liegen
		// �berpr�fen ob Ecken von a in b:
		Vector2f a_LO = new Vector2f(a.left, a.top);
		Vector2f a_LU = Vector2f.add(a_LO, new Vector2f(0, a.height));
		Vector2f a_RO = Vector2f.add(a_LO, new Vector2f(a.width, 0));
		Vector2f a_RU = Vector2f.add(a_LO, new Vector2f(a.width, a.height));

		if(contains(b,a_LO)||contains(b,a_LU)||contains(b,a_RO)||contains(b, a_RU))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean containsFloat(FloatRect a, FloatRect b)
	{
		// ansatz: b ist erheblich gr�sser als a
		// dadurch muss bei einer �berschneidung immer min. ein Eckpunkt im anderen Rechteck liegen
		// �berpr�fen ob Ecken von a in b:
		Vector2f a_LO = new Vector2f(a.left, a.top);
		Vector2f a_LU = Vector2f.add(a_LO, new Vector2f(0, a.height));
		Vector2f a_RO = Vector2f.add(a_LO, new Vector2f(a.width, 0));
		Vector2f a_RU = Vector2f.add(a_LO, new Vector2f(a.width, a.height));

		if(contains(b,a_LO) && contains(b,a_LU) && contains(b,a_RO) && contains(b, a_RU))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	/**
	 * @brief Kollisionsüberprüfung und elastischer Stoss
	 * @param spaceObjects liste der Spaceobjects, um alle �berpr�fen zu k�nnen
	 */
	public static void schneiden(Vector<SpaceObject> spaceObjects)
	{

		if(spaceObjects.size() != 0){
			for(int i = 0; i+1 <= spaceObjects.size(); i++)
			{
				SpaceObject A = spaceObjects.elementAt(i);
				A.move();
				
	
				for(int j = i+1; j < spaceObjects.size(); j++) // nur wenn ein zweites Objekt vorhanden ist
				{
					
					SpaceObject B = spaceObjects.elementAt(j);
					Vector2f P1 = A.getCenter();	//Zentrum 1
					Vector2f P2 = B.getCenter();	//Zentrum 2
					
					B.move(); 
					
					// Ist der Abstand der beiden Zentren kleiner als die Summe der Radien? 
					if(absVec(Vector2f.sub(P1,P2)) <= Math.abs(A.model.getRadius())+Math.abs(B.model.getRadius()) && !A.elastisch && !B.elastisch)
					{
						/*A.getSprite().move(Vector2f.sub(A.model.getVelocity(),Vector2f.mul(A.model.getVelocity(), 2)));
						B.getSprite().move(Vector2f.sub(B.model.getVelocity(),Vector2f.mul(B.model.getVelocity(), 2)));
						float Abstand = (A.model.getRadius()+B.model.getRadius())-absVec(Vector2f.sub(P1,P2));
						float cosPhi = skalar(A.model.getVelocity(),B.model.getVelocity())/(absVec(A.model.getVelocity())+absVec(B.model.getVelocity()));
						float strecke1a = Abstand * cosPhi;
						*/
						Vector2f x0 = Vector2f.div(Vector2f.sub(P1, P2),absVec(Vector2f.sub(P1, P2))); //Nullvektor rechtwinklig zur Schnittachse
						Vector2f y0 = new Vector2f(-x0.y,x0.x); // Nullvektor parallel zu Schnittachse
						
						//Berechnung der Energievektorkomponenten
						Vector2f Ax = Vector2f.mul(x0,skalar(A.model.getEnergy(),x0)); //Vektor A auf x0 projiziert
						Vector2f Ay = Vector2f.mul(y0,skalar(A.model.getEnergy(),y0)); //Vector A auf y0 projiziert
						
						Vector2f Bx = Vector2f.mul(x0,skalar(B.model.getEnergy(),x0)); //Vektor B auf x0 porjiziert
						Vector2f By = Vector2f.mul(y0,skalar(B.model.getEnergy(),y0)); //Vektor B auf y0 projiziert
						
						//Addieren der Energievektorkomponeneten
						A.model.setEnergy(Vector2f.add(Bx,Ay)); 
						B.model.setEnergy(Vector2f.add(Ax,By));
						
						//Berechnung der Geschwindigkeitsvektorkomponeneten
						Vector2f Axv = Vector2f.mul(x0,skalar(A.model.getVelocity(),x0));
						Vector2f Ayv = Vector2f.mul(y0,skalar(A.model.getVelocity(),y0));
						
						Vector2f Bxv = Vector2f.mul(x0,skalar(B.model.getVelocity(),x0));
						Vector2f Byv = Vector2f.mul(y0,skalar(B.model.getVelocity(),y0));
						
						//Addieren der Geschwindigkeitsvektoren
						A.model.setVelocity(Vector2f.add(Bxv,Ayv));
						B.model.setVelocity(Vector2f.add(Axv,Byv));
						
						A.move();
						B.move();
						
						A.elastisch = true;
						
						//A.collided = 2;
						//B.collided = 2;
					}
					else
					{
						//Falls keine Kollision stattgefunden hat, B zur�ckpositionieren
						B.sprite.move(Vector2f.mul(B.model.getVelocity(),-1)); 
						A.elastisch = false;
					}
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
		
		// Load font
		Font font = new Font();
		font.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/DejaVuSans.ttf"));
		
		
		// Lade die im Menu verfuegbaren Levels
		Scanner levelsFile = new Scanner(Game.class.getResourceAsStream("/spacetraveler/rsc/levels/levels")); 
		Vector<String> availableLevels = new Vector<>(); 
		Vector<Text> levelOptions = new Vector<>();
		
		Text selectLevelText = new Text("Oder wähle ein Level aus:", font);
		selectLevelText.setPosition(400, 250);
		
		
		while((levelsFile.hasNextLine())){
			availableLevels.addElement(levelsFile.nextLine());
			levelOptions.addElement(new Text(availableLevels.lastElement(), font));
			levelOptions.lastElement().setPosition(400, 300+50*levelOptions.size());
			levelOptions.lastElement().setColor(Color.YELLOW);
			
			System.out.println("Gefunden: '" + availableLevels.lastElement() + "'");
			
			
		}
		
		levelsFile.close();
		
		
		// Menu aktiv?
		boolean menuAktiv = true;
		Texture menuTexture = new Texture();
		menuTexture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/menu.png"));
		Sprite menuSprite = new Sprite(menuTexture);
		menuSprite.setOrigin(new Vector2f(menuTexture.getSize().x/2, menuTexture.getSize().y/2));
		menuSprite.setPosition(hauptfenster.mapPixelToCoords(new Vector2i(hauptfenster.getSize().x/2, hauptfenster.getSize().y/2)));
		
		// Menu Buttons definieren
		IntRect spielStartenButton = new IntRect(72, 244, 223, 44);
		IntRect spielBeendenButton = new IntRect(72, 328, 247, 44); 
		
		// Load GameOver Image
		Texture gameOverTexture = new Texture();
		gameOverTexture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/gameOver.png"));
		Sprite gameOverSprite = new Sprite(gameOverTexture);
		gameOverSprite.setOrigin(gameOverTexture.getSize().x/2, gameOverTexture.getSize().y/2);
		
		// Anzeigetext für Zeit erstellen
		Text timeText = new Text("", font);
		timeText.setPosition(25, 25);
		
		hauptfenster.setView(view);
	
	
		
		boolean gameOver = false;		/**< @brief true, wenn der Spieler das Spiel verloren hat */
		int userGravityId = -1;

		
		// Level erstellen (Laden, um l zu initialisieren!)
		Level l = new Level("level3");
		
		
		while(hauptfenster.isOpen()){
			// Events verarbeiten
			for(org.jsfml.window.event.Event ev : hauptfenster.pollEvents()){
				
				// Event beim Anklicken von [x], bzw. [Alt]+[F4], etc...
        		if(ev.type == Type.CLOSED){
        			hauptfenster.close();
        			
        			continue;
        		}
        		
        		// Escape um zum Menü zu gelangen
        		if(ev.type == Type.KEY_PRESSED && ev.asKeyEvent().key == Key.ESCAPE){
        			menuAktiv = true;
        			gameOver = true;
        			
        			continue;
        		}
        		
        		// Menü Klick abfangen
        		if(menuAktiv && ev.type == Type.MOUSE_BUTTON_RELEASED){
        			Vector2i mousePos = ev.asMouseButtonEvent().position;
        			if(spielStartenButton.contains(mousePos)){
        				// Level1 laden und Menu deaktivieren
        				l = new Level("level1");
        				menuAktiv = false;
        				
        				gravRight = false;
        				gravLeft = false;
        				
        				gameOver = false;
        				
        			} else if(spielBeendenButton.contains(mousePos)){
        				hauptfenster.close();
        			} else {
        				for(int i=0; i<levelOptions.size(); i++){
        					if(levelOptions.get(i).getGlobalBounds().contains(hauptfenster.mapPixelToCoords(mousePos))){
        						System.out.println("Lade Level '" + availableLevels.get(i) + "'");
        						
        						l = new Level(availableLevels.get(i));
                				menuAktiv = false;
                				
                				gravRight = false;
                				gravLeft = false;
                				
                				gameOver = false;
        						
        						continue;
        					}
        				}
        			}
        			
        			continue;
        		}
        		

        		// Wenn schon ein Gravitationspunkt gesetzt wurde, diesen entfernen!
        		if(ev.type == Type.MOUSE_BUTTON_PRESSED && (gravLeft || gravRight) && l.gravityFields.size() > 0){
        			l.gravityFields.remove(userGravityId);
        			userGravityId = -1;
        			
        			gravLeft = false;
        			gravRight = false;
        			gameOver = false;
        			
        			break;
        		}
        		
        		
        		if(ev.type == Type.MOUSE_BUTTON_PRESSED && ev.asMouseButtonEvent().button == Mouse.Button.LEFT){
	        		if(gravLeft == false){
	        			gravLeft = true;
	        			l.gravityFields.addElement(new Gravity((hauptfenster.mapPixelToCoords(Vector2i.sub(new Vector2i((int)Mouse.getPosition().x, (int)Mouse.getPosition().y), hauptfenster.getPosition()))), 5));
	        			userGravityId = l.gravityFields.size()-1;
	        			
	        			continue;
	        		}
        		}
        		
        		if(ev.type == Type.MOUSE_BUTTON_PRESSED && ev.asMouseButtonEvent().button == Mouse.Button.RIGHT){
	        		if(gravRight == false){
	        			gravRight = true;
	        			l.gravityFields.addElement(new Gravity((hauptfenster.mapPixelToCoords(Vector2i.sub(new Vector2i((int)Mouse.getPosition().x, (int)Mouse.getPosition().y), hauptfenster.getPosition()))), -5));
	        			userGravityId = l.gravityFields.size()-1;
	        			
	        			continue;
	        		}
        		}
        		
        		
			}

			
			hauptfenster.clear();
			
			if(!gameOver && !menuAktiv){
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
				
				// Objekte rotieren
				for(SpaceObject s : l.spaceObjects){
					s.getSprite().rotate(s.getAngularMomentum());
					
				}
				
				if(l.spaceObjects.size()!= 0){				// es muss mindestens ein objekt haben
					for(SpaceObject s : l.spaceObjects)		
					{
						s.sprite.move(s.model.getVelocity());;

						
							for(int f = 1; f < s.Bereich.length; f++)
							{
								Tile tile = l.Feld[(int)s.Bereich[f].x][(int)s.Bereich[f].y];
								FloatRect FR = tile.sprite.getGlobalBounds();
								if(tile.index == 1)
								{
									if(intersection(s.sprite.getGlobalBounds(), FR)) //kollisions�berpr�fung
									{										
										// umdrehen der einen komponente der Geschwindigkeits- und Energievektoren
										float a = s.model.getVelocity().x;
										float b = s.model.getVelocity().y;
										
										float c = s.model.getEnergy().x;
										float d = s.model.getEnergy().y;
										
										if(f == 1 || f == 3)
										{
											s.model.setVelocity(new Vector2f(-a,b)); 
											s.model.setEnergy(new Vector2f(-c,d));
											s.collided = true;
										}
										if(f == 2 || f == 4)
										{
											s.model.setEnergy(new Vector2f(c,-d));
											s.model.setVelocity(new Vector2f(a,-b));
											s.collided = true;
										}
									}
									else
									{
										
										s.collided = false;
	
									}
								}
								else
								{
									s.collided = false;
								}
	
							}
							s.sprite.move(-s.model.getVelocity().x, -s.model.getVelocity().y);

						}
					
				}
				
				/**
				 * �berpr�fen der Kollision unter den Objekten
				 * Bewegen aller Objekte
				 */
				schneiden(l.spaceObjects);
				
				for(SpaceObject s : l.spaceObjects){
					for(int f = 0; f < s.Bereich.length; f++)
					{
						FloatRect FR = l.Feld[(int)s.Bereich[f].x][(int)s.Bereich[f].y].sprite.getGlobalBounds();
						if(contains(FR, s.getCenter()) && l.Feld[(int)s.Bereich[f].x][(int)s.Bereich[f].y].index != 1)
						{
							s.bereichVerschieben(s.Bereich[f]);
							break;
						}
					}
				}
			
				
				// Überprüfen, ob die Zeit abgelaufen ist.
				if(l.levelTimer.getElapsedTime().asSeconds() > l.levelTimeAvailable){
					gameOver = true;
				}
				
				view.setCenter(l.spaceObjects.get(0).getSprite().getPosition());
				
				// Hintergrund / View gut positionieren!
				view.setCenter(l.spaceObjects.get(0).getSprite().getPosition());
				hauptfenster.setView(view);
	

				
				// Rendering
				
				// Alle Background Tiles zeichnen
				for(int x = 0; x < l.Feld.length; x++)
				{
					for(int y = 0; y < l.Feld[0].length; y++)
					{
						hauptfenster.draw(l.Feld[x][y].sprite);

					}
				}
				
				/*for(Tile t : l.tiles){
					hauptfenster.draw(t.sprite);
				}*/
				
				// Alle Gravitys zeichnen
				for(Gravity g : l.gravityFields){
					hauptfenster.draw(g.getSprite());
				}
			
				// Alle SpaceObjects zeichnen!
				for(SpaceObject s : l.spaceObjects){	
					hauptfenster.draw(s.getSprite());
				}
				
				if(!l.sprites.isEmpty())
				{
					for(Sprite sp: l.sprites)
					{
						hauptfenster.draw(sp);
					}
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
				if(gameOver){
					gameOverSprite.setPosition(hauptfenster.mapPixelToCoords(new Vector2i(hauptfenster.getSize().x/2, hauptfenster.getSize().y/2)));
					hauptfenster.draw(gameOverSprite);
				}
				
				// Wenn Menü aktiviert wurde, Menü anzeigen
				if(menuAktiv){
					hauptfenster.clear(new Color(155, 150, 150));
					menuSprite.setPosition(hauptfenster.mapPixelToCoords(new Vector2i(hauptfenster.getSize().x/2, hauptfenster.getSize().y/2)));
					selectLevelText.setPosition(hauptfenster.mapPixelToCoords(new Vector2i(400, 250)));
					
					hauptfenster.draw(menuSprite);
					hauptfenster.draw(selectLevelText);
					
					// Verschiedene Level anzeigen
					int i = 0;
					for(Text t : levelOptions){
						// Repositionieren
						t.setPosition(hauptfenster.mapPixelToCoords(new Vector2i(400, 300+50*i)));
						
						hauptfenster.draw(t);
						i++;
					}
				}
			}

			
			hauptfenster.display();
			Thread.sleep(1000/25);
		}
	}
	
}
