package spacetraveler;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

/**
 * @brief Enthält alle für ein Level notwendige Objekte (Gravitationspunkte, etc...)
 * @throws IOException Wenn Dateien nicht geladen werden können
 * */
public class Level {
	public Vector<Tile> tiles;					/**< Hintergrund Tiles */
	
	public Vector<SpaceObject> spaceObjects;	/**< SpaceObjects im Level */
	public Vector<Gravity> gravityFields;		/**< GravityFields im Level */
	
	public Clock levelTimer;					/**< Timer, der Zeit seit Beginn hochzählt */
	public float levelTimeAvailable;			/**< Zeit, die für das Level zur Verfügung steht */
	
	public Vector2f levelStart, levelZiel; 		/**< Level Start- und Zielpunkt */
	 
	
	
	
	public Level() throws IOException{
	
		// Class Members initialisieren
		tiles = new Vector<>();
		spaceObjects = new Vector<>();
		gravityFields = new Vector<>();
		
		levelTimer = new Clock();
		levelTimeAvailable = 30;
		
		levelStart = new Vector2f(100, 100);
		levelZiel = new Vector2f(600, 600);
		
		
		// Spieler erstellen    (Spieler = 1. spaceObject)
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/spieler.png", 5.0f, new Vector2f(50, 0), levelStart, true));
				
		// Beispieldaten spaceObjects
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/asteroid.png", 5.0f, new Vector2f(50, 0), new Vector2f(200, 200), true));
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/asteroid.png", 5.0f, new Vector2f(50, 0), new Vector2f(100, 200), true));
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/asteroid.png", 5.0f, new Vector2f(50, 0), new Vector2f(250, 200), true));
		spaceObjects.get(1).addAngularMomentum(15);

		
		// Beispieldaten gravityFields
		//gravityFields.addElement(new Gravity(new Vector2f(300,300), 5));
		//gravityFields.addElement(new Gravity(new Vector2f(500,300), 5));
		//gravityFields.addElement(new Gravity(new Vector2f(1200,400), 10));

	}
	
	
	/**
	 * @brief Lädt das Hintergrundbild eines Tiles und fügt die Objekte des Tiles dem aktuellen Level hinzu
	 * @param pos Position des Tiles (in absoluten Bildschirmkoordinaten)
	 * @param tileType Gibt den Typ/die Art des Tiles an
	 * @throws IOException Dateizugriffsfehler
	 */
	public void loadTile(Vector2f pos, int tileType) throws IOException{
		/* 
		 * Einlesen der TileType-Datei
		 * Erstellen der Objekte und deren Speicherung in den Vektoren
		 * 
		 * Struktur einer Tile-Datei (Ohne Leerzeilen):
		 * 
		 * int hintergrundId
		 * 
		 * int anzahlSpaceObjects
		 * 		string 		texturePfad
		 * 		float 		m
		 * 		floats 		EX, EY
		 * 		floats		posX, posY
		 * 		boolean		gravityOn
		 * 
		 * int anzahlGravityFields
		 * 		floats		posX, posX
		 * 		float		m
		 * 
		 */
		
		Scanner parser = new Scanner(Game.class.getResourceAsStream("/spacetraveler/rsc/tiles/tile" + tileType));
		
		// hintergrundId
		int hintergrundId = parser.nextInt(); 				parser.nextLine();
		
		// Tile laden!
		tiles.addElement(new Tile(pos, hintergrundId));
		
		// anzahlSpaceObjects
		int anzahlSpaceObjects = parser.nextInt();			parser.nextLine();
		
		// spaceObjects
		for(int n=0; n<anzahlSpaceObjects; n++){
			String texturePath = parser.nextLine();
			float m = parser.nextFloat();											parser.nextLine();
			Vector2f E = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
			Vector2f P = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
			boolean gravityOn = parser.nextBoolean();								parser.nextLine();
			
			spaceObjects.addElement(new SpaceObject(texturePath, m, E, P, gravityOn));
		}
		
		// anzahlGravityFields
		int anzahlGravityFields = parser.nextInt();			parser.nextLine();
		
		// gravityFields
		for(int n=0; n<anzahlGravityFields; n++){
			Vector2f P = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
			
			float m = parser.nextFloat();											parser.nextLine();					
			
			gravityFields.addElement(new Gravity(P, m));
		}
	}
	
}
