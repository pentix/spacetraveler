package spacetraveler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

/**
 * @brief Enthält alle für ein Level notwendige Objekte (Gravitationspunkte, etc...)
 *
 * 
 * 
 * Allgemeine Struktur einer Leveldatei:
 * 
 * int anzahlSpaceObjects
 * 		string 		texturePfad
 * 		float 		m
 * 		float 		EX, EY
 * 		float		posX, posY
 * 		boolean		gravityOn
 * 		float		drehGeschwindigkeit
 * 
 * int anzahlGravityFields
 * 		float		posX, posX
 * 		float		m
 * 
 * int timeAvailable
 * float startPosX, startPosY
 * float zielPosX, zielPosY 
 * 
 * */
public class Level {
	
	public Vector<SpaceObject> spaceObjects;	/**< SpaceObjects im Level */
	public Vector<Gravity> gravityFields;		/**< GravityFields im Level */
	
	public Clock levelTimer;					/**< Timer, der Zeit seit Beginn hochzählt */
	public float levelTimeAvailable;			/**< Zeit, die für das Level zur Verfügung steht */
	
	public Vector2f levelStart, levelZiel; 		/**< Level Start- und Zielpunkt */
	 
	
	
	/** @brief Erstellt ein Level von einer Datei
	 *  @throws IOException Wenn Dateien nicht geladen werden können
	 */
	public Level(String filename) throws IOException{
	
		// Leveldatei einlesen
		InputStream levelDatei = Game.class.getResourceAsStream(filename);
		Scanner parser = new Scanner(levelDatei);
	
		
		// SpaceObjects initialisieren
		int anzahlSpaceObjects = parser.nextInt();
		spaceObjects = new Vector<>(anzahlSpaceObjects+1);
		

		// spaceObjects einlesen
		// Spieler erstellen    (Spieler == 1. Spaceobject)
		spaceObjects.add(new SpaceObject("/spacetraveler/rsc/spieler.png", 5.0f, new Vector2f(50, 0), levelStart, true));

		
		for(int n=0; n<anzahlSpaceObjects; n++){
			spaceObjects.add(new SpaceObject(parser.nextLine(), parser.nextFloat(), new Vector2f(parser.nextFloat(), parser.nextFloat()), new Vector2f(parser.nextFloat(), parser.nextFloat()), parser.nextBoolean()));
			spaceObjects.get(1+n).addAngularMomentum(parser.nextFloat());
		}
		
		// gravityFields einlesen
		int anzahlGravityFields = parser.nextInt();
		gravityFields = new Vector<>(anzahlGravityFields);

		
		for(int n=0; n<anzahlGravityFields; n++){
			gravityFields.addElement(new Gravity(new Vector2f(parser.nextFloat(), parser.nextFloat()), parser.nextFloat()));
		}
			
		
		// Levelinformationen einlesen
		levelTimeAvailable = parser.nextInt();
		
		levelStart = new Vector2f(parser.nextFloat(), parser.nextFloat());
		levelZiel = new Vector2f(parser.nextFloat(), parser.nextFloat());
		
		
		// Zeitmessung starten!
		levelTimer = new Clock();

	}
	
	
}
