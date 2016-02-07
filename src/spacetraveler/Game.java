package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.event.Event.Type;

/**
 * @brief Gameklasse. Enthält die main()-Methode
 * 
 */
public class Game {

	private static boolean gravLeft = false;
	/** < @brief Wird linke Maustaste gedrückt? */
	private static boolean gravRight = false; /**
												 * < @brief Wird rechte
												 * Maustaste gedrückt?
												 */

	/**
	 * @brief Main-Methode des ganzen Spiels
	 * @param args
	 *            Konsolenargumente, die dem Programm übergeben werden. (Werden
	 *            nicht ausgewertet)
	 */
	public static void main(String args[]) throws InterruptedException, IOException {
		RenderWindow hauptfenster = new RenderWindow(new VideoMode(1200, 800), "SpaceTraveler",
				Window.TITLEBAR | Window.CLOSE);
		hauptfenster.clear();
		hauptfenster.setPosition(new Vector2i(-10, 0));

		// Get the window's default view
		ConstView defaultView = hauptfenster.getView();

		// Create a new view by copying the window's default view
		View view = new View(defaultView.getCenter(), defaultView.getSize());

		GameScreen gameScreen = new GameScreen(hauptfenster);
		MenuScreen menuScreen = new MenuScreen(hauptfenster);
		GameOverScreen gameOverScreen = new GameOverScreen(hauptfenster);

		Screen currentScreen = menuScreen;

		hauptfenster.setView(view);

		int userGravityId = -1;

		// Level erstellen (Laden, um l zu initialisieren!)
		Level l = new Level("level1");

		while (hauptfenster.isOpen()) {
			// Events verarbeiten
			for (org.jsfml.window.event.Event ev : hauptfenster.pollEvents()) {

				// Event beim Anklicken von [x], bzw. [Alt]+[F4], etc...
				if (ev.type == Type.CLOSED) {
					hauptfenster.close();
					continue;
				}

				// Escape um zum Menü zu gelangen
				if (ev.type == Type.KEY_PRESSED && ev.asKeyEvent().key == Key.ESCAPE) {
					currentScreen = menuScreen;
					continue;
				}

				// Menü Klick abfangen
				if (currentScreen == menuScreen && ev.type == Type.MOUSE_BUTTON_RELEASED) {
					Vector2i mousePos = ev.asMouseButtonEvent().position;
					if (menuScreen.spielStartenButton.contains(mousePos)) {
						// Level1 laden und Menu deaktivieren
						l = new Level("level2");
						currentScreen=gameScreen;

						gravRight = false;
						gravLeft = false;

					} else if (menuScreen.spielBeendenButton.contains(mousePos)) {
						hauptfenster.close();
					}

					continue;
				}

				// Wenn schon ein Gravitationspunkt gesetzt wurde, diesen
				// entfernen!
				if (ev.type == Type.MOUSE_BUTTON_PRESSED && (gravLeft || gravRight) && l.gravityFields.size() > 0) {
					l.gravityFields.remove(userGravityId);
					userGravityId = -1;

					gravLeft = false;
					gravRight = false;

					break;
				}

				if (ev.type == Type.MOUSE_BUTTON_PRESSED && ev.asMouseButtonEvent().button == Mouse.Button.LEFT) {
					if (gravLeft == false) {
						gravLeft = true;
						l.gravityFields.add(new Gravity((hauptfenster.mapPixelToCoords(
								Vector2i.sub(new Vector2i((int) Mouse.getPosition().x, (int) Mouse.getPosition().y),
										hauptfenster.getPosition()))),
								500));
						userGravityId = l.gravityFields.size() - 1;

						continue;
					}
				}

				if (ev.type == Type.MOUSE_BUTTON_PRESSED && ev.asMouseButtonEvent().button == Mouse.Button.RIGHT) {
					if (gravRight == false) {
						gravRight = true;
						l.gravityFields.add(new Gravity((hauptfenster.mapPixelToCoords(
								Vector2i.sub(new Vector2i((int) Mouse.getPosition().x, (int) Mouse.getPosition().y),
										hauptfenster.getPosition()))),
								-500));
						userGravityId = l.gravityFields.size() - 1;

						continue;
					}
				}

			}

			hauptfenster.clear();

			currentScreen.update(l);

			// Überprüfen, ob die Zeit abgelaufen ist.
			if (currentScreen==gameScreen && l.levelTimer.getElapsedTime().asSeconds() > l.levelTimeAvailable) {
				currentScreen=gameOverScreen;
			}

			view.setCenter(l.spaceObjects.get(0).getSprite().getPosition());

			// Hintergrund / View gut positionieren!
			view.setCenter(l.spaceObjects.get(0).getSprite().getPosition());
			hauptfenster.setView(view);


			currentScreen.render();
			hauptfenster.display();
			Thread.sleep(1000 / 25);
		}
	}

}
