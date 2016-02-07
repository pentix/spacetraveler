package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class MenuScreen implements Screen {

	IntRect spielStartenButton, spielBeendenButton;

	private RenderWindow hauptfenster;
	private Sprite menuSprite;

	public MenuScreen(RenderWindow hauptfenster) {
		this.hauptfenster = hauptfenster;
		// Menu aktiv?
		Texture menuTexture = new Texture();

		try {
			menuTexture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/menu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		menuSprite = new Sprite(menuTexture);
		menuSprite.setOrigin(new Vector2f(menuTexture.getSize().x / 2, menuTexture.getSize().y / 2));
		menuSprite.setPosition(hauptfenster
				.mapPixelToCoords(new Vector2i(hauptfenster.getSize().x / 2, hauptfenster.getSize().y / 2)));

		// Menu Buttons definieren
		spielStartenButton = new IntRect(72, 244, 223, 44);
		spielBeendenButton = new IntRect(72, 328, 247, 44);
	}

	@Override
	public void update(Level l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		hauptfenster.clear(new Color(155, 150, 150));
		menuSprite.setPosition(hauptfenster
				.mapPixelToCoords(new Vector2i(hauptfenster.getSize().x / 2, hauptfenster.getSize().y / 2)));
		hauptfenster.draw(menuSprite);
	}

}
