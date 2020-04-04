package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	Vector2 position;
	Vector2 dimension;
	
	Vector2 origin;
	float rotation;
	
	Vector2 speed;
	private Rectangle bounds;

	protected String name;
	int lives = 0;

	public void setLives(int lives){this.lives = lives;}
	public int getLives(){return lives;}
	public void damage(){lives--;}
	public void damage(int takeOutlives){this.lives -= takeOutlives;}


	GameObject(){
		bounds = new Rectangle();
	}

	public Rectangle getBounds(){
		bounds.set(position.x,position.y,dimension.x,dimension.y);
		return bounds;
	}
	
	public abstract void render(SpriteBatch batch);
	
	public abstract void update(float elpasedTime);
	
}
	
