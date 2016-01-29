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
	public int levelWidth, levelHeight;			/**< Breite und Höhe des Levels */
	 
	
	
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
	 * flaots | zielX, zielY
	 * ints | width, height
	 * 		ints | id1,1 id2,1 id3,1 id4,1
	 * 		ints | id2,1 id2,2 id3,2 id4,2
	 * 		... | ...
	 * 
	 */
	public Level(String levelId) throws IOException{
	
		// Class Members initialisieren
		tiles = new Vector<>();
		spaceObjects = new Vector<>();
		gravityFields = new Vector<>();
	
		// Leveldatei öffnen
		Scanner parser = new Scanner(Game.class.getResourceAsStream("/spacetraveler/rsc/levels/" + levelId));
		
		// Levelinformationen laden
		levelTimeAvailable = parser.nextInt();									parser.nextLine();
		levelStart = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
		levelZiel = new Vector2f(parser.nextFloat(), parser.nextFloat());		parser.nextLine();
		
		levelWidth = parser.nextInt();
		levelHeight = parser.nextInt();
		
		// Spieler erstellen    (Spieler = 1. spaceObject)
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/spieler.png", 5.0f, new Vector2f(50, 0), levelStart, true));
		
		
		// Tiles laden und erstellen
		for(int h=0; h<levelHeight; h++){
			for(int w=0; w<levelWidth; w++){
				loadTile(new Vector2f(256*w, 256*h), parser.nextInt());
			}
			
			parser.nextLine();
		}
				
		
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
