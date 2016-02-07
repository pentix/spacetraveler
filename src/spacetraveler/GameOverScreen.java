package spacetraveler;

import java.io.IOException;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

public class GameOverScreen implements Screen {
	private RenderWindow hauptfenster;
	private Sprite gameOverSprite;
	
	public GameOverScreen(RenderWindow w) {
		hauptfenster=w;

		// Load GameOver Image
		Texture gameOverTexture = new Texture();
		try {
			gameOverTexture.loadFromStream(Game.class.getResourceAsStream("/spacetraveler/rsc/gameOver.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		gameOverSprite = new Sprite(gameOverTexture);
		gameOverSprite.setOrigin(gameOverTexture.getSize().x / 2, gameOverTexture.getSize().y / 2);


	}

	@Override
	public void update(Level l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		gameOverSprite.setPosition(hauptfenster.mapPixelToCoords(
				new Vector2i(hauptfenster.getSize().x / 2, hauptfenster.getSize().y / 2)));
		hauptfenster.draw(gameOverSprite);
	}

}
