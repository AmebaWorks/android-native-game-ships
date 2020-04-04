package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	Texture background;

	Texture ship;
	TextureRegion[][] shipTiles;
	Animation<TextureRegion> shipIdle;
	Animation<TextureRegion> shipMovementLeft;
	Animation<TextureRegion> shipMovementRight;

	TextureRegion[] bulletAnim;
	TextureRegion[][] bulletTiles;

	Texture regularEnemy;
	TextureRegion[][] regularEnemyTile;
	public TextureRegion[] regulatEnemyAnimTiles;
	Animation<TextureRegion> regularEnemyAnimation;

	Texture specialEnemy;
	TextureRegion[][] specialEnemyTile;
	Animation<TextureRegion> specialEnemyAnimation;

	TextureRegion[] missileAnim;
	TextureRegion[][] missileTiles;

	static Assets instance;

	private Assets() {

		loadBackground();
		loadShip();
		loadBullet();
		loadRegularEnemy();
		loadSpecialEnemy();
		loadMissile();
	}
	
	public static Assets getInstance() {

		if(instance == null)
			instance = new Assets();
		
		return instance;
	}

	private void loadBackground()
	{
		background = new Texture(Gdx.files.internal("background.jpg"));
	}

	private void loadShip()
	{
		ship = new Texture(Gdx.files.internal("ship.png"));
		shipTiles = TextureRegion.split(ship,ship.getWidth()/3,ship.getHeight()/2);

		TextureRegion[] arrayTextureRegionIdle  = new TextureRegion[2];
		arrayTextureRegionIdle[0] = shipTiles[0][1];
		arrayTextureRegionIdle[1] = shipTiles[1][1];
		shipIdle = new Animation<TextureRegion>( 0.1f, arrayTextureRegionIdle);

		TextureRegion[] arrayTextureRegionLeft  = new TextureRegion[2];
		arrayTextureRegionLeft[0] = shipTiles[0][0];
		arrayTextureRegionLeft[1] = shipTiles[1][0];
		shipMovementLeft = new Animation<TextureRegion>( 0.1f, arrayTextureRegionLeft);

		TextureRegion[] arrayTextureRegionRight  = new TextureRegion[2];
		arrayTextureRegionRight[0] = shipTiles[0][2];
		arrayTextureRegionRight[1] = shipTiles[1][2];
		shipMovementRight = new Animation<TextureRegion>( 0.1f, arrayTextureRegionRight);
	}

	private void loadRegularEnemy()
	{
		regularEnemy = new Texture(Gdx.files.internal("enemy.png"));
		regularEnemyTile = TextureRegion.split(regularEnemy, regularEnemy.getWidth()/4, regularEnemy.getHeight()/2);

		regulatEnemyAnimTiles = new TextureRegion[8];
		regulatEnemyAnimTiles[0] = regularEnemyTile[0][0];
		regulatEnemyAnimTiles[1] = regularEnemyTile[0][1];
		regulatEnemyAnimTiles[2] = regularEnemyTile[0][2];
		regulatEnemyAnimTiles[3] = regularEnemyTile[0][3];
		regulatEnemyAnimTiles[4] = regularEnemyTile[1][0];
		regulatEnemyAnimTiles[5] = regularEnemyTile[1][1];
		regulatEnemyAnimTiles[6] = regularEnemyTile[1][2];
		regulatEnemyAnimTiles[7] = regularEnemyTile[1][3];
		regularEnemyAnimation = new Animation<TextureRegion>( 0.1f, regulatEnemyAnimTiles);
	}

	private void loadSpecialEnemy()
	{
		specialEnemy = new Texture(Gdx.files.internal("specialenemy.png"));
		specialEnemyTile = TextureRegion.split(specialEnemy, regularEnemy.getWidth()/4, specialEnemy.getHeight()/2);
		TextureRegion[] arrayTextureRegion = new TextureRegion[8];
		arrayTextureRegion[0] = specialEnemyTile[0][0];
		arrayTextureRegion[1] = specialEnemyTile[0][1];
		arrayTextureRegion[2] = specialEnemyTile[0][2];
		arrayTextureRegion[3] = specialEnemyTile[0][3];
		arrayTextureRegion[4] = specialEnemyTile[1][0];
		arrayTextureRegion[5] = specialEnemyTile[1][1];
		arrayTextureRegion[6] = specialEnemyTile[1][2];
		arrayTextureRegion[7] = specialEnemyTile[1][3];
		specialEnemyAnimation = new Animation<TextureRegion>( 0.1f, arrayTextureRegion);
	}

	private void loadBullet()
	{
		bulletTiles = TextureRegion.split(new Texture(Gdx.files.internal("shoot.png")),10,22);
		bulletAnim = new TextureRegion[] {bulletTiles[0][0], bulletTiles[0][1]};
	}

	private void loadMissile()
	{

		missileTiles = TextureRegion.split(new Texture(Gdx.files.internal("missile.png")),16,34);
		missileAnim = new TextureRegion[] {missileTiles[0][0], missileTiles[0][1]};
	}
}
