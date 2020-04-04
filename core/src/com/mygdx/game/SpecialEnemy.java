package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Cristina on 04/05/2018.
 */

public class SpecialEnemy extends GameObject {
    Animation<TextureRegion> specialEnemyAnimation;

    float elapsedTime = 0;
    Ship targetShip;

    int shootCounter = 0;
    Array<Vector2> targetPos;
    int currentPoint = 0;

    Level level ;

    public SpecialEnemy(Vector2 position, Level level)
    {
        specialEnemyAnimation = Assets.getInstance().specialEnemyAnimation;

        this.position = new Vector2(position);

        dimension = new Vector2(5f,5f);

        this.level = level;
        this.targetShip = level.ship;

        targetPos = new Array<Vector2>();
        targetPos.add(new Vector2(targetShip.position.x,targetShip.position.y + 10));
        targetPos.add(new Vector2(targetShip.position.x + 10,targetShip.position.y));
        targetPos.add(new Vector2(targetShip.position.x ,targetShip.position.y - 10));
        targetPos.add(new Vector2(targetShip.position.x -10,targetShip.position.y));

        setLives(2);
    }

    public void movement()
    {
        float treshold = 1f;

        Vector2 target = new Vector2(targetPos.get(currentPoint));
        //update waypoints
        if((position.x > target.x - treshold && position.x < target.x + treshold) &&
                (position.y > target.y - treshold && position.y < target.y + treshold))
        {
            currentPoint++;
        }

        Vector2 direction = new Vector2(target.x - position.x, target.y - position.y);
        double distance = Math.sqrt((target.x - position.x)*(target.x - position.x) + (target.y - position.y)*(target.y - position.y));
        direction.x /= distance;
        direction.y /= distance;

        this.position.x += Constants.SPECIALENEMY_SPEED * direction.x;
        this.position.y += Constants.SPECIALENEMY_SPEED * direction.y;
    }

    public void lookAt()
    {
        rotation = (float) (Math.atan2(targetShip.position.y - this.position.y, targetShip.position.x - this.position.x) * (180 / Math.PI)-90);
    }

    public void shoot()
    {
        level.shoots.add(new Bullet(this.position, "specialBullet", level));
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(specialEnemyAnimation.getKeyFrame(elapsedTime,true),position.x,position.y, 2, 2, dimension.x, dimension.y, 1, 1, rotation);
    }

    @Override
    public void update(float elapsedTime)
    {
        this.elapsedTime += elapsedTime;

        shootCounter++;
        if(shootCounter > 70)
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
