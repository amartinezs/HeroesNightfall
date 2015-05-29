package com.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.HeroesNightfall.GameResourses;

import java.util.Random;

/**
 * Created by Albert on 29/05/2015.
 */
public class Coin {

    private AtlasAnimation atlas;
    private boolean alive;


    public Coin(String spriteSheetPath, float positionX, float positionY, World world, float fps, String tag, String modelRegionName){
        atlas = new AtlasAnimation(spriteSheetPath, positionX, positionY, world, fps, tag, modelRegionName);
        atlas.getCos().setGravityScale(0);
        alive = true;
    }

    public void dispose(){
        atlas.dispose();
    }

    public void draw(SpriteBatch batch, float delta){
        atlas.draw(batch, delta);
    }


    public AtlasAnimation getAtlas() {
        return atlas;
    }

    public void setAtlas(AtlasAnimation atlas) {
        this.atlas = atlas;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void killCoin(Body b){
        if(b.equals(atlas.getCos())){
            alive = false;
        }
    }


}





