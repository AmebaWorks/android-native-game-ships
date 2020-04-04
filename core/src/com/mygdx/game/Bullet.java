package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.lang.String;

/**
 * Created by Cristina on 01/05/2018.
 */

public class Bullet extends GameObject {

    Animation<TextureRegion> bulletAnimation;
    float elapsedTime;
    Vector2 direction;
    float speed;
    Ship shipTarget;
    int liveBullet = 0;

    //Bullet from ship
    public Bullet(Vector2 position, String name, Level level)
    {
        this.position = new Vector2(position.x+0.8f,position.y+1.8f);
        this.name = name;
        shipTarget = level.ship;

        if(name != "fromShip")//from specialEnemie Missil
        {
            this.dimension = new Vector2(3f,3f);
            this.bulletAnimation = new Animation<TextureRegion>(0.1f, Assets.getInstance().missileAnim);
            this.direction = new Vector2();
            this.direction.x = direction.x - shipTarget.position.x;
            this.direction.y = direction.y - shipTarget.position.y;

            speed = Constants.SPECIALENEMY_BULLET_SPEED;
        }
        else//from ship
        {
            this.dimension = new Vector2(2f,2f);
            this.bulletAnimation = new Animation<TextureRegion>(0.1f, Assets.getInstance().bulletAnim);
            this.direction = new Vector2(0,1);
            speed = Constants.SHIP_BULLET_SPEED;
        }
    }

    //Bullet from regularEnemies
    public Bullet(Vector2 position, Vector2 direction, String name, Level level)
    {
        this.position = new Vector2(position.x+0.8f,position.y+1.8f);
        this.dimension = new Vector2(2f,2f);
        this.bulletAnimation = new Animation<TextureRegion>(0.1f, Assets.getInstance().bulletAnim);

        this.direction = new Vector2();

        if(name == "specialBulletfromShip")
        {
            this.direction.x = position.x + direction.x;
            this.direction.y = position.y + direction.y;
        }
        else
        {
            this.direction.x = direction.x - position.x;
            this.direction.y = direction.y - position.y;
        }

        speed = Constants.REGULARENEMY_BULLET_SPEED;

        shipTarget = level.ship;
        this.name = name;
    }


    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(bulletAnimation.getKeyFrame(elapsedTime,true), position.x, position.y, 2,2,dimension.x, dimension.y,1,1,rotation);

    }

    public void lookAt()
    {
        rotation = (float) (Math.atan2(shipTarget.position.y - this.position.y, shipTarget.position.x - this.position.x) * (180 / Math.PI)-90);
    }

    @Override
    public void update(float elapsedTime)
    {
        this.elapsedTime += elapsedTime;
        liveBullet++;

        if(name == "specialBullet" && liveBullet > 30)
        {
            specialMovement();
            liveBullet = 0;
        }
        else
        {
            movement();
        }
        lookAt();
    }

    boolean outOfBounds()
    {
        if(this.position.x < -Constants.WORLD_WIDTH/2 || this.position.x > Constants.WORLD_WIDTH/2 ||
                this.position.y < -Constants.WORLD_HEIGTH/2 || this.position.y > Constants.WORLD_HEIGTH/2)
        {
            //if i'm out
            return true;
        }
        else
        {
            //if i inside
            return false;
        }
    }

    private void movement()
    {
        this.position.x += speed * direction.x;
        this.position.y += speed * direction.y;
    }

    private void specialMovement()
    {
        this.direction.x = (shipTarget.position.x - position.x);
        this.direction.y = (shipTarget.position.y - position.y);
        this.position.x += speed * direction.x;
        this.position.y += speed * direction.y;
    }
}
