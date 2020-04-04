package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.LittleEndianInputStream;

/**
 * Created by Cristina on 04/05/2018.
 */

public class RegularEnemy extends GameObject {

    Animation<TextureRegion> regularEnemyAnimation;

    int direction;
    float elapsedTime = 0;
    Ship target;
    int shootCounter = 0;

    Level level;

    public RegularEnemy(Vector2 position, int direction, Level level)
    {
        regularEnemyAnimation = new Animation<TextureRegion>(0.1f, Assets.getInstance().regulatEnemyAnimTiles);
        this.direction = direction;
        this.position = new Vector2(position);
        dimension = new Vector2(5f,5f);
        this.level = level;
        this.target = this.level.ship;

        setLives(1);
    }

    public void movement()
    {
        this.position.x += Constants.REGULARENEMY_SPEED * direction;
        this.position.y += Constants.REGULARENEMY_SPEED * direction;
    }

    public void lookAt() {

        rotation = (float) (Math.atan2(target.position.y - this.position.y, target.position.x - this.position.x)* (180 / Math.PI)-90);
    }

    public void shoot()
    {
        level.shoots.add(new Bullet(this.position,target.position, "regularBullet", level));
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(regularEnemyAnimation.getKeyFrame(elapsedTime,true),position.x,position.y, dimension.x /2, dimension.y /2, dimension.x, dimension.y, 1, 1, rotation);
    }

    @Override
    public void update(float elapsedTime) {
        this.elapsedTime += elapsedTime;

        shootCounter++;
        if(shootCounter >60)
        {
            shoot();
            shootCounter = 0;
        }

        movement();
        lookAt();
    }

    public boolean checkBounds()
    {
        if(this.position.x < -Constants.WORLD_WIDTH/2 || this.position.x > Constants.WORLD_WIDTH/2 ||
                this.position.y < -Constants.WORLD_HEIGTH/2 || this.position.y > Constants.WORLD_HEIGTH/2)
        {
            //si estoy fuera
            return true;

        }
        else
        {
            //si estoy dentro
            return false;
        }
    }
}
