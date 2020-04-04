package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Cristina on 17/04/2018.
 */

public class Ship extends GameObject {

    Animation<TextureRegion> shipIdleAnimation;
    Animation<TextureRegion> shipLeftAnimation;
    Animation<TextureRegion> shipRightAnimation;
    Level level;
    private float elapsedTime = 0;

    public Ship(Level level) {

        position = new Vector2(0,0);
        dimension = new Vector2(5f,5f);

        speed = new Vector2(0,0);

        shipIdleAnimation = Assets.getInstance().shipIdle;
        shipLeftAnimation = Assets.getInstance().shipMovementLeft;
        shipRightAnimation = Assets.getInstance().shipMovementRight;

        this.level = level;
    }

    @Override
    public void render(SpriteBatch batch) {

        TextureRegion tex;

        if(speed.x > 0) {
            tex = shipRightAnimation.getKeyFrame(elapsedTime, true);
        }
        else if(speed.x < 0)
        {
            tex = shipLeftAnimation.getKeyFrame(elapsedTime, true);
        }
        else
        {
            tex = shipIdleAnimation.getKeyFrame(elapsedTime, true);
        }

        batch.draw(tex, position.x, position.y, dimension.x, dimension.y);
    }

    @Override
    public void update(float elapsedTime) {

        //update animations
        this.elapsedTime += elapsedTime;

        Vector2 copy = new Vector2(speed);
        //update position
        position.add(copy.scl(elapsedTime));
        position.x = MathUtils.clamp(position.x, -Constants.WORLD_WIDTH/2, Constants.WORLD_WIDTH/2 - dimension.x);
        position.y = MathUtils.clamp(position.y, -Constants.WORLD_HEIGTH/2, Constants.WORLD_HEIGTH/2 -dimension.y);
    }

    public void addSpeed(float x, float y){
        speed.add(x,y);
        System.out.print("estoy entrando");
    }

    public void setSpeed(float x, float y){
        speed.set(x*2, y*2);
    }

    public void shoot() {
        level.shoots.add(new Bullet(this.position, "fromShip", level));
    }
    public void shoot(Vector2 direction) {
        level.shoots.add(new Bullet(this.position, direction,"fromShip", level));
    }
}
