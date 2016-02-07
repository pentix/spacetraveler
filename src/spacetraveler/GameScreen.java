package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2i;

public class GameScreen implements Screen {
	private RenderWindow hauptfenster;
	private Text timeText;
	private Level level;

	public GameScreen(RenderWindow w) {
		hauptfenster = w;
		// Load font
		Font font = new Font();

		try {
			font.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/DejaVuSans.ttf"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Anzeigetext für Zeit erstellen
		timeText = new Text("", font);
		timeText.setPosition(25, 25);

	}

	@Override
	public void update(Level l) {
		level = l;

		// Berechnungen

		for (int i = 0; i + 1 <= l.spaceObjects.size(); i++) {
			SpaceObject s = l.spaceObjects.get(i);

			// Gravity
			Vector gesamtEnergie = new Vector(0, 0);
			if (s.model.isGravityOn()) {
				for (Gravity g : l.gravityFields) {
					gesamtEnergie.add(g.model.getEnergy(s));
				}
				s.model.addEnergy(gesamtEnergie);
			}

			// Rotation
			s.getSprite().rotate(s.getAngularMomentum());

			// Wand Kollision
			s.sprite.move(s.model.getVelocity().toVector2f());

			for (int f = 1; f < s.Bereich.length; f++) {
				Tile tile = l.Feld[(int) s.Bereich[f].x][(int) s.Bereich[f].y];
				FloatRect FR = tile.sprite.getGlobalBounds();
				if (tile.index == 1) {
					if (FR.intersection(s.sprite.getGlobalBounds()) != null) // kollisions�berpr�fung
					{
						// umdrehen der einen komponente der
						// Geschwindigkeits- und Energievektoren
						double a = s.model.getVelocity().x;
						double b = s.model.getVelocity().y;

						double c = s.model.getEnergy().x;
						double d = s.model.getEnergy().y;

						if (f == 1 || f == 3) {
							s.model.setVelocity(new Vector(-a, b));
							s.model.setEnergy(new Vector(-c, d));
						}
						if (f == 2 || f == 4) {
							s.model.setEnergy(new Vector(c, -d));
							s.model.setVelocity(new Vector(a, -b));
						}
					}
				}
			}
			s.sprite.move(Vector.mul(s.model.getVelocity(), -1).toVector2f());

			// Schneiden
			SpaceObject A = l.spaceObjects.get(i);
			A.move();

			for (int j = i + 1; j < l.spaceObjects.size(); j++) // nur
																// wenn
																// ein
																// zweites
																// Objekt
																// vorhanden
																// ist
			{

				SpaceObject B = l.spaceObjects.get(j);
				Vector P1 = A.getCenter(); // Zentrum 1
				Vector P2 = B.getCenter(); // Zentrum 2

				B.move();

				// Ist der Abstand der beiden Zentren kleiner als die
				// Summe der Radien?
				if (Vector.sub(P1, P2).abs() <= Math.abs(A.model.getRadius()) + Math.abs(B.model.getRadius())
						&& !A.elastisch && !B.elastisch) {

					/*
					 * A.getSprite()
					 * .move(Vector2f.sub(A.model.getVelocity(),
					 * Vector2f.mul(A.model.getVelocity(), 2)));
					 * B.getSprite()
					 * .move(Vector2f.sub(B.model.getVelocity(),
					 * Vector2f.mul(B.model.getVelocity(), 2))); float
					 * Abstand = (A.model.getRadius() +
					 * B.model.getRadius()) - absVec(Vector2f.sub(P1,
					 * P2)); float cosPhi =
					 * skalar(A.model.getVelocity(),
					 * B.model.getVelocity()) /
					 * (absVec(A.model.getVelocity()) +
					 * absVec(B.model.getVelocity())); float strecke1a =
					 * Abstand * cosPhi;
					 */

					Vector x0 = Vector.unitVector(Vector.sub(P1, P2)); // Nullvektor
																		// rechtwinklig
																		// zur
																		// Schnittachse
					Vector y0 = new Vector(-x0.y, x0.x); // Nullvektor
															// parallel
															// zu
															// Schnittachse

					// Berechnung der Energievektorkomponenten
					Vector Ax = Vector.mul(x0, Vector.scalar((A.model.getEnergy()), x0)); // Vektor
																							// A
																							// auf
																							// x0
																							// projiziert
					Vector Ay = Vector.mul(y0, Vector.scalar((A.model.getEnergy()), y0)); // Vector
																							// A
																							// auf
																							// y0
																							// projiziert

					Vector Bx = Vector.mul(x0, Vector.scalar((B.model.getEnergy()), x0)); // Vektor
																							// B
																							// auf
																							// x0
																							// porjiziert
					Vector By = Vector.mul(y0, Vector.scalar((B.model.getEnergy()), y0)); // Vektor
																							// B
																							// auf
																							// y0
																							// projiziert

					// Addieren der Energievektorkomponeneten
					A.model.setEnergy(Vector.add(Bx, Ay));
					B.model.setEnergy(Vector.add(Ax, By));

					// Berechnung der Geschwindigkeitsvektorkomponeneten
					Vector Axv = Vector.mul(x0, Vector.scalar(A.model.getVelocity(), x0));
					Vector Ayv = Vector.mul(y0, Vector.scalar(A.model.getVelocity(), y0));

					Vector Bxv = Vector.mul(x0, Vector.scalar(B.model.getVelocity(), x0));
					Vector Byv = Vector.mul(y0, Vector.scalar(B.model.getVelocity(), y0));

					// Addieren der Geschwindigkeitsvektoren
					A.model.setVelocity(Vector.add(Bxv, Ayv));
					B.model.setVelocity(Vector.add(Axv, Byv));

					A.move();
					B.move();

					A.elastisch = true;

					// A.collided = 2;
					// B.collided = 2;
				} else {
					// Falls keine Kollision stattgefunden hat, B
					// zur�ckpositionieren
					B.sprite.move(Vector.mul(B.model.getVelocity(), -1).toVector2f());
					A.elastisch = false;
				}
			}

			for (int f = 0; f < s.Bereich.length; f++) {
				FloatRect FR = l.Feld[(int) s.Bereich[f].x][(int) s.Bereich[f].y].sprite.getGlobalBounds();
				if (FR.contains(s.getCenter().toVector2f())
						&& l.Feld[(int) s.Bereich[f].x][(int) s.Bereich[f].y].index != 1) {
					s.bereichVerschieben(s.Bereich[f]);
					break;
				}
			}

		}
	}

	@Override
	public void render() {
		// Rendering

		// Alle Background Tiles zeichnen
		for (int x = 0; x < level.Feld.length; x++) {
			for (int y = 0; y < level.Feld[0].length; y++) {
				hauptfenster.draw(level.Feld[x][y].sprite);
			}
		}

		/*
		 * for(Tile t : l.tiles){ hauptfenster.draw(t.sprite); }
		 */

		// Alle Gravitys zeichnen
		for (Gravity g : level.gravityFields) {
			hauptfenster.draw(g.getSprite());
		}

		// Alle SpaceObjects zeichnen!
		for (SpaceObject s : level.spaceObjects) {
			hauptfenster.draw(s.getSprite());
		}

		if (!level.sprites.isEmpty()) {
			for (Sprite sp : level.sprites) {
				hauptfenster.draw(sp);
			}
		}

		// Zeit anzeigen!
		long timePassed = level.levelTimer.getElapsedTime().asMilliseconds();
		int minutesPassed = (int) Math.floor(timePassed / 1000 / 60);
		int secondsPassed = (int) Math.floor(timePassed / 1000 - minutesPassed * 60);
		int millisecondsPassed = (int) ((timePassed % 1000) / 10);

		String timeTextString = (minutesPassed < 10 ? "0" + minutesPassed : minutesPassed) + ":"
				+ (secondsPassed < 10 ? "0" + secondsPassed : secondsPassed) + ":"
				+ (millisecondsPassed < 10 ? "0" + millisecondsPassed : millisecondsPassed);
		timeText.setString(timeTextString);
		timeText.setPosition(hauptfenster.mapPixelToCoords(new Vector2i(25, 25)));

		hauptfenster.draw(timeText);
	}

}
