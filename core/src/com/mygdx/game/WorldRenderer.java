package com.mygdx.game;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {

	OrthographicCamera camera;
	SpriteBatch batch;
	WorldController wc;
    FPSLogger fps;

	public WorldRenderer(WorldController controller) {
		
		this.wc = controller;
        batch = new SpriteBatch();
        fps = new FPSLogger();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGTH);
		init();
	}
	
	private void init() {

		camera.setToOrtho(false,Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGTH);
		camera.position.set(wc.level.ship.position.x, wc.level.ship.position.y,0);
		camera.update();
	}
	
	public void render() {

        fps.log();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//backgorund
		batch.draw(Assets.getInstance().background, -Constants.WORLD_WIDTH/2 , -Constants.WORLD_HEIGTH/2, Constants.WORLD_WIDTH , Constants.WORLD_HEIGTH);

		//ship
		wc.level.ship.render(batch);

		//regularEnemies
		for (int i = 0; i < wc.level.regularEnemies.size; i++)
		{
			wc.level.regularEnemies.get(i).render(batch);
		}

		//specialEnemies
		for (int i = 0; i < wc.level.specialEnemies.size; i++)
		{
			wc.level.specialEnemies.get(i).render(batch);
		}

		//shoots
		for(int i = 0; i < wc.level.shoots.size(); i++)
		{
			wc.level.shoots.get(i).render(batch);
		}

		batch.end();
	}
	
	public void resize(int width, int height) {
		
		camera.viewportWidth = (camera.viewportHeight/height) * width;
		camera.update();
	}
	
	public void dispose() {

		batch.dispose();
	}
	
}
