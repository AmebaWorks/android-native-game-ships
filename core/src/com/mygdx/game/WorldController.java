package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class WorldController implements InputProcessor {

	public OrthographicCamera camera;
	public Level level;

	float originalAccelX;
	float originalAccelY;

	public WorldController() {
		//Initialize variables
		camera = new OrthographicCamera();
		level = new Level(this);
		originalAccelX = Gdx.input.getAccelerometerX();
		originalAccelY = Gdx.input.getAccelerometerY();
		Gdx.input.setInputProcessor(this);
	}

	public void update(float delta) {

		level.update(delta);
		camera.update();
		inputs(delta);
		cameraMoving();
		if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer))
		{
			listenerAccelerometre();
		}
	}

	private void inputs(float delta)
	{
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			level.ship.shoot();
		}

		if(Gdx.input.isKeyJustPressed(Keys.B))
		{
			level.ship.shoot(new Vector2(-0.5f, 0.5f));
			level.ship.shoot(new Vector2(0f, 1f));
			level.ship.shoot(new Vector2(0.5f, 0.5f));
		}
	}

	void cameraMoving()
	{
		camera.position.x = level.ship.position.x + level.ship.dimension.x/2;
		camera.position.y = level.ship.position.y + level.ship.dimension.y/2;

		camera.position.x = MathUtils.clamp(camera.position.x, -Constants.WORLD_WIDTH/2 + (camera.viewportWidth/2 * camera.zoom),Constants.WORLD_WIDTH/2 - (camera.viewportWidth/2 * camera.zoom));
		camera.position.y = MathUtils.clamp(camera.position.y, -Constants.WORLD_HEIGTH/2 + (camera.viewportHeight/2 * camera.zoom),Constants.WORLD_HEIGTH/2 - (camera.viewportHeight/2 * camera.zoom));
		camera.update();
	}

	public boolean listenerAccelerometre()
	{
		float speed = Constants.SHIP_SPEED;

		float accelX = Gdx.input.getAccelerometerX() - originalAccelX;
		float accelY = Gdx.input.getAccelerometerY() - originalAccelY;

		int signX = 0;
		int signY = 0;

		if(accelY < -1) {
			signY = -1;
		}
		if(accelY > +1) {
			signY = 1;
		}
		if(accelX < -1) {
			signX = 1;
		}
		if(accelX > +1) {
			signX = -1;
		}
		level.ship.setSpeed(signX*speed, -signY*speed);
		return false;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		//processInput
		float speed = Constants.SHIP_SPEED;

		if(keycode == (Keys.A)) {
			level.ship.addSpeed(-speed, 0);
			return true;
		}
		if(keycode == (Keys.D)) {
			level.ship.addSpeed(speed, 0);
			return true;
		}
		if(keycode == (Keys.W)) {
			level.ship.addSpeed(0,speed);
			return true;
		}
		if(keycode == (Keys.S)) {
			level.ship.addSpeed(0,-speed);
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		float speed = Constants.SHIP_SPEED;
		if(keycode == (Keys.A)) {
			level.ship.addSpeed(speed, 0);
			return true;
		}
		if(keycode == (Keys.D)) {
			level.ship.addSpeed(-speed, 0);
			return true;
		}
		if(keycode == (Keys.W)) {
			level.ship.addSpeed(0, -speed);
			return true;
		}
		if(keycode == (Keys.S)) {
			level.ship.addSpeed(0, speed);
			return true;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		level.ship.shoot();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		level.ship.shoot(new Vector2(-0.5f, 0.5f));
		level.ship.shoot(new Vector2(0f, 1f));
		level.ship.shoot(new Vector2(0.5f, 0.5f));
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
