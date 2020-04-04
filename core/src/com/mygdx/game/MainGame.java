package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MainGame implements ApplicationListener {

	WorldController controller;
	WorldRenderer renderer;

	@Override
	public void create () {
		controller = new WorldController();
		renderer = new WorldRenderer(controller);
		controller.camera = renderer.camera;

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}

	@Override
	public void render () {
		
		controller.update(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
	}
	
	@Override
	public void dispose () {
		renderer.dispose();
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}
	@Override
	public void resize(int width, int height) {
		
		renderer.resize(width, height);
	}
}
