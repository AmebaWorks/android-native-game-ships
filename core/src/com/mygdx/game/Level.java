package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Cristina on 05/06/2018.
 */

public class Level {
    Ship ship;
    Vector2 prevPosShip;

    Array<RegularEnemy> regularEnemies;
    Array<SpecialEnemy> specialEnemies;
    ArrayList<Bullet> shoots;

    float speed;
    int enemyCounter = 0;

    WorldController wc;

    Random random;

    Level(WorldController wc)
    {
        ship = new Ship(this);
        prevPosShip = new Vector2();
        regularEnemies = new Array<RegularEnemy>();
        specialEnemies = new Array<SpecialEnemy>();
        shoots = new ArrayList<Bullet>();
        random = new Random();
        this.wc = wc;
        wc.camera.setToOrtho(false,Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGTH);
        wc.camera.update();
        init();
    }

    private void init()
    {
        ship.position.x = 0;
        ship.position.y = 0;
        ship.setLives(5);

        regularEnemies = new Array<RegularEnemy>();
        specialEnemies = new Array<SpecialEnemy>();
    }

    void reset()
    {
        regularEnemies.clear();
        specialEnemies.clear();
        shoots.clear();
        init();
    }

    void update(float dt)
    {
        ship.position.x = MathUtils.clamp(ship.position.x , -50 , 50);
        ship.position.y = MathUtils.clamp(ship.position.y , -100, 100);

        prevPosShip = new Vector2(ship.position.x, ship.position.y);
        ship.update(dt);

        //regularEnemies
        for(int i = 0; i < regularEnemies.size; i++)
        {
            regularEnemies.get(i).update(dt);
            if(regularEnemies.get(i).checkBounds() || regularEnemies.get(i).getLives() == 0)
            {
                regularEnemies.removeIndex(i);
            }
        }

        //specialEnemies
        for(int i = 0; i < specialEnemies.size; i++)
        {
            specialEnemies.get(i).update(dt);
            if(specialEnemies.get(i).checkBounds() || specialEnemies.get(i).currentPoint == 4 || specialEnemies.get(i).getLives() == 0)
            {
                specialEnemies.removeIndex(i);
                i--;
            }
        }

        for(int i = 0; i < shoots.size(); i++)
        {
            shoots.get(i).update(dt);
            if(shoots.get(i).outOfBounds())
            {
                shoots.remove(i);
            }
        }

        updateCollisions();
        speed = Constants.SHIP_SPEED * Gdx.graphics.getDeltaTime();
        generateEnemies();

        if(ship.getLives() <= 0)
        {
            reset();
        }
    }

    private void updateCollisions()
    {
        ArrayList<Integer> toDestroyRegularEnemies = new ArrayList<Integer>();
        ArrayList<Integer> toDestroySpecialEnemies = new ArrayList<Integer>();
        ArrayList<Integer> toDestroyShoots = new ArrayList<Integer>();

        //regular enemies contra nave muere enemigo y resto vida a nave
        for(int i = 0; i < regularEnemies.size; i++){
            if(CollisionHelper.simpleCollision(ship, regularEnemies.get(i)))
            {
                ship.damage();
                toDestroyRegularEnemies.add(i);
            }
        }

        //special enemies contra nave muere enemigo y resto vida a nave
        for(int i = 0; i < specialEnemies.size; i++)
        {
            if(CollisionHelper.simpleCollision(ship, specialEnemies.get(i)))
            {
                ship.damage();
                toDestroySpecialEnemies.add(i);
            }
        }

        //shoots
        for(int j = 0; j < shoots.size(); j++)
        {
            //balas de regular contra nave
            if(shoots.get(j).name == "regularBullet")
            {
                if(CollisionHelper.simpleCollision(ship, shoots.get(j)))
                {
                    ship.damage();
                    toDestroyShoots.add(j);
                    //System.out.print("le doy a la naveeeeeeeeeee");
                }
            }
            //balas de special contra nave
            if(shoots.get(j).name == "specialBullet")
            {
                //balas de special contra nave
                if(CollisionHelper.simpleCollision(ship, shoots.get(j)))
                {
                    ship.damage(2);
                    toDestroyShoots.add(j);
                }
            }

            //bala de la nave
            if(shoots.get(j).name == "fromShip")
            {
                //contra regularEnemie
                for (int i = 0; i < regularEnemies.size; i++)
                {
                    if (CollisionHelper.simpleCollision(regularEnemies.get(i), shoots.get(j)))
                    {
                        regularEnemies.get(i).damage();
                        toDestroyShoots.add(j);
                    }
                }
                //contra specialEnemie
                for (int i = 0; i < specialEnemies.size; i++)
                {
                    if (CollisionHelper.simpleCollision(specialEnemies.get(i), shoots.get(j)))
                    {
                        specialEnemies.get(i).damage();
                        toDestroyShoots.add(j);
                    }
                }
                //contra balas missiles
                for(int i = 0; i < shoots.size(); i++)
                {
                    if (shoots.get(i).name == "specialBullet")
                    {
                        if (CollisionHelper.simpleCollision(shoots.get(j), shoots.get(i))) {
                            toDestroyShoots.add(i);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < toDestroyRegularEnemies.size(); i++)
        {
            regularEnemies.removeIndex(toDestroyRegularEnemies.get(i));
        }
        for(int i = 0; i < toDestroySpecialEnemies.size(); i++)
        {
            specialEnemies.removeIndex(toDestroySpecialEnemies.get(i));
        }
        for(int i = 0; i < toDestroyShoots.size(); i++)
        {
            shoots.remove(toDestroyShoots.get(i));
        }
    }

    void generateEnemies()
    {
        enemyCounter++;

        float positionX;
        float positionY;
        int directionX;
        if(random(2) == 0)
        {
            positionX = ship.position.x - Constants.VIEWPORT_WIDTH/2;
            positionY = ship.position.y + random(-Constants.VIEWPORT_HEIGTH/2,Constants.VIEWPORT_HEIGTH/2);
            directionX = +1;
        }
        else
        {
            positionX = ship.position.x + (Constants.VIEWPORT_WIDTH/2);
            positionY = ship.position.y - random(-Constants.VIEWPORT_HEIGTH/2,Constants.VIEWPORT_HEIGTH/2);
            directionX = -1;
        }

        if(enemyCounter > 60)
        {
            if(random(2) == 0)
            {
                specialEnemies.add(new SpecialEnemy(new Vector2(positionX, positionY), this));
            }
            else
            {
                regularEnemies.add(new RegularEnemy(new Vector2(positionX, positionY), directionX, this));
            }

            enemyCounter = 0;
        }
    }


    public int random (int range)
    {
        return random.nextInt(range + 1);
    }

    float random (float start, float end)
    {
        return start + random.nextFloat() * (end - start);
    }
}
