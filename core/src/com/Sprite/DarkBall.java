package com.Sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.HeroesNightfall.GameResourses;

/**
 * Created by Albert on 28/05/2015.
 */
public class DarkBall extends Enemy {

    private Array<TextureRegion> stopAttactAnimation;


    public DarkBall(String spriteSheetPath, float positionX, float positionY, World world, float fps, String tag){
        super(spriteSheetPath,  positionX,  positionY,  world,  fps, tag, GameResourses.DARKBALL_WIDTH, GameResourses.DARBALL_HEIGHT);
        //buildStoppedAnimation();
        buildAttackAnimation();
        //buildHitAnimation();
        buildStopAttactAnimation();
        atlas.getCos().setGravityScale(0);
    }

    @Override
    public void draw(SpriteBatch batch, float delta){
        super.draw(batch, delta);
    }
    @Override
    public void dispose(){
        super.dispose();
    }


    private void buildStopAttactAnimation(){
        stopAttactAnimation = new Array<TextureRegion>();
        stopAttactAnimation.add(atlas.getTextureRegion("atac3"));
        stopAttactAnimation.add(atlas.getTextureRegion("atac4"));

        currentAnimation = new Animation(atlas.getFps(), stopAttactAnimation);
        atlas.setAnimation(currentAnimation);

    }

    private void buildAttackAnimation(){
        attackAction = new Array<TextureRegion>();
        attackAction.add(atlas.getTextureRegion("atac3"));
        attackAction.add(atlas.getTextureRegion("atac4"));

        currentAnimation = new Animation(atlas.getFps(), attackAction);
        atlas.setAnimation(currentAnimation);
    }

    private void buildHitAnimation(){
        atlas.setAnimation(null);
        hitAction = new Array<TextureRegion>();
        hitAction.add(atlas.getTextureRegion("hit1"));
        hitAction.add(atlas.getTextureRegion("hit2"));
        hitAction.add(atlas.getTextureRegion("hit3"));
        currentAnimation = new Animation(atlas.getFps(), hitAction);
        atlas.setAnimation(currentAnimation);

    }

}
