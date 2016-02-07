package spacetraveler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

/**
 * @brief Enthält alle für ein Level notwendige Objekte (Gravitationspunkte, etc...)
 * @throws IOException Wenn Dateien nicht geladen werden können
 *
 * */
public class Level {
	public Tile[][] Feld;						/**< @brief Koordinatenfeld der indices */	
	
	public ArrayList<SpaceObject> spaceObjects;	/**< @brief SpaceObjects im Level */
	public ArrayList<Gravity> gravityFields;		/**< @brief GravityFields im Level */
	public ArrayList<Sprite> sprites;
	
	public Clock levelTimer;					/**< @brief Timer, der Zeit seit Beginn hochzählt */
	public float levelTimeAvailable;			/**< @brief Zeit, die für das Level zur Verfügung steht */
	
	public Vector2f levelStart; 				/**< @brief Level Startpunkt */
	public Vector2f levelZiel;					/**< @brief Level Zielpunkt */
	
	public int levelWidth;						/**< @brief Breite des Levels */
	public int levelHeight;						/**< @brief Höhe des Levels */
	
	
	/**
	 * @brief Liest ein Level aus der Datei ein und erstellt die dazugehörigen Objekte
	 * @param levelId Name des Levels ( = Name der Datei in /spacetraveler/rsc/levels/)
	 * @throws IOException Wenn Leveldatei nicht geöffnet werden kann
	 * 
	 * Struktur der Leveldatei:
	 * 
	 * Datentyp|Inhalt
	 * --------|------
	 * int | levelTimeAvailable
	 * floats | startX, startY
	 * floats | zielX, zielY
	 * ints | width, height
	 * 		ints | id1,1 id2,1 id3,1 id4,1
	 * 		ints | id2,1 id2,2 id3,2 id4,2
	 * 		... | ...
	 * 
	 */
	public Level(String levelId) throws IOException{
	
		// Class Members initialisieren
		//tiles = new Vector<>();
		spaceObjects = new ArrayList<>();
		gravityFields = new ArrayList<>();
		sprites = new ArrayList<>();
	
		// Leveldatei öffnen
		Scanner parser = new Scanner(Game.class.getResourceAsStream("/spacetraveler/rsc/levels/" + levelId));
		
		// Levelinformationen laden
		levelTimeAvailable = parser.nextInt();									parser.nextLine();
		levelStart = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
		levelZiel = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
		
		levelWidth = parser.nextInt();
		levelHeight = parser.nextInt();
				
		Feld = new Tile[levelHeight][levelWidth]; // erstellen des Koordinatenfeldes
		
		
		// Tiles laden und erstellen
		for(int h=0; h<levelHeight; h++){
			for(int w=0; w<levelWidth; w++){
				loadTile(new Vector2f(w, h), parser.nextInt());
				
			}
			
			parser.nextLine();
		}
		
		parser.close();
		
		// Zeitmessung starten!
		levelTimer = new Clock();
	}
	
	
	/**
	 * @brief Lädt das Hintergrundbild eines Tiles und fügt die Objekte des Tiles dem aktuellen Level hinzu
	 * @param pos Position des Tiles (in absoluten Bildschirmkoordinaten)
	 * @param tileType Gibt den Typ/die Art des Tiles an
	 * @throws IOException Dateizugriffsfehler
	 * 
	 * 
	 * 		 
	 * Einlesen der TileType-Datei
	 * Erstellen der Objekte und deren Speicherung in den Vektoren
	 * 
	 * Struktur einer Tile-Datei (Ohne Leerzeilen):
	 * 
	 * Datentyp | Inhalt
	 * ---------|--------
	 * int | hintergrundId
	 * int |anzahlSpaceObjects
	 * 	string 	| texturePfad
	 * 	float 	| m
	 * 	floats 	| EX, EY
	 * 	floats	| posX, posY
	 * 	boolean	| gravityOn
	 * int | anzahlGravityFields
	 * 	floats	| posX, posX
	 * 	float	| m
	 *
	 *
	 */
	public void loadTile(Vector2f pos, int tileType) throws IOException{
		
		Scanner parser = new Scanner(Game.class.getResourceAsStream("/spacetraveler/rsc/tiles/tile" + tileType));
		
		// hintergrundId
		int hintergrundId = parser.nextInt(); 				parser.nextLine();
		
		// Tile laden!
		Feld[(int)(pos.x)][(int)(pos.y)] = new Tile(pos, hintergrundId);
		
		Vector2f coord = pos;
		pos = Vector2f.mul(pos, 512);
		
		// anzahlSpaceObjects
		int anzahlSpaceObjects = parser.nextInt();			parser.nextLine();
		
		// spaceObjects
		for(int n=0; n<anzahlSpaceObjects; n++){
			String texturePath = parser.nextLine();
			float m = parser.nextFloat();											parser.nextLine();
			Vector E = new Vector(parser.nextDouble(), parser.nextDouble());		    parser.nextLine();
			Vector2f P = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
			boolean gravityOn = parser.nextBoolean();								parser.nextLine();
			
			spaceObjects.add(new SpaceObject(texturePath, m, E, coord, Vector2f.add(pos, P), gravityOn));
		}
		
		// anzahlGravityFields
		int anzahlGravityFields = parser.nextInt();			parser.nextLine();
		
		// gravityFields
		for(int n=0; n<anzahlGravityFields; n++){
			Vector2f P = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
			
			float m = parser.nextFloat();											parser.nextLine();					
			
			gravityFields.add(new Gravity(Vector2f.add(pos, P), m));
		}
		
		parser.close();
		
		if(tileType == 4) //startfeld
		{
			Vector2f mitte = new Vector2f(pos.x+512/2, pos.y+512/2); // herausfinden der Mitte

			//laden un positiionieren der Starttextur
			Texture startTex = new Texture(); 
			startTex.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/goal.png"));
			Sprite start = new Sprite(startTex);
			start.setPosition(mitte);
			sprites.add(start);
			
			//erstellen des Spielers
			spaceObjects.add(0,new SpaceObject("/spacetraveler/rsc/spieler.png", 10.0f, new Vector(50, 0), coord, mitte, true));

			
		}
	}
	
}
