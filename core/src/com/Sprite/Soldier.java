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

/**
 * Created by Albert on 28/05/2015.
 */
public class Soldier extends Enemy {

    private Array<TextureRegion> moveAnimation;
    private Array<TextureRegion> actionsAnimation;
    private float distanceFromPlayer;
    private boolean engaging;

    public Soldier(String spriteSheetPath, float positionX, float positionY, World world, float fps, String tag){
        super(spriteSheetPath, positionX, positionY, world, fps, tag, GameResourses.SOLDIER_WIDTH, GameResourses.SOLDIER_HEIGHT);
        buildStoppedAnimation();
        buildAttackAnimation();
        buildHitAnimation();
        buildMoveAnimation();
        currentAnimation = new Animation(atlas.getFps(), moveAnimation);
        distanceFromPlayer = 3;
    }

    @Override
    public void dispose(){
        super.dispose();
    }

    @Override
    public void draw(SpriteBatch batch, float delta){
        atlas.setAnimation(currentAnimation);
        super.draw(batch, delta);
    }


    private void buildMoveAnimation(){
        //atlas.setAnimation(null);
        moveAnimation = new Array<TextureRegion>();
        moveAnimation.add(atlas.getTextureRegion("move1"));
        moveAnimation.add(atlas.getTextureRegion("move2"));
        moveAnimation.add(atlas.getTextureRegion("move3"));
        moveAnimation.add(atlas.getTextureRegion("move4"));
        moveAnimation.add(atlas.getTextureRegion("move5"));
        moveAnimation.add(atlas.getTextureRegion("move6"));
        moveAnimation.add(atlas.getTextureRegion("move10"));
        moveAnimation.add(atlas.getTextureRegion("move8"));
        moveAnimation.add(atlas.getTextureRegion("move9"));
        //currentAnimation = new Animation(atlas.getFps(), moveAnimation);
        //atlas.setAnimation(currentAnimation);


    }

    private void buildStoppedAnimation(){
        //atlas.setAnimation(null);
        stoppedAnimation = new Array<TextureRegion>();
        stoppedAnimation.add(atlas.getTextureRegion("stop1"));
        stoppedAnimation.add(atlas.getTextureRegion("stop2"));
        stoppedAnimation.add(atlas.getTextureRegion("stop3"));
        stoppedAnimation.add(atlas.getTextureRegion("stop4"));
        stoppedAnimation.add(atlas.getTextureRegion("stop5"));
        stoppedAnimation.add(atlas.getTextureRegion("stop6"));
        stoppedAnimation.add(atlas.getTextureRegion("stop7"));
        stoppedAnimation.add(atlas.getTextureRegion("stop8"));
        //currentAnimation = new Animation(atlas.getFps(), stoppedAnimation);
        //atlas.setAnimation(currentAnimation);
    }

    private void buildAttackAnimation(){
        //atlas.setAnimation(null);
        attackAction = new Array<TextureRegion>();
        attackAction.add(atlas.getTextureRegion("atac4"));
        attackAction.add(atlas.getTextureRegion("atac5"));
        attackAction.add(atlas.getTextureRegion("atac6"));
        attackAction.add(atlas.getTextureRegion("atac7"));
        //currentAnimation = new Animation(atlas.getFps(), attackAction);
        //atlas.setAnimation(currentAnimation);
    }

    private void buildHitAnimation(){
        //atlas.setAnimation(null);
        hitAction = new Array<TextureRegion>();
        hitAction.add(atlas.getTextureRegion("hit1"));
        hitAction.add(atlas.getTextureRegion("hit2"));
        hitAction.add(atlas.getTextureRegion("hit3"));
        hitAction.add(atlas.getTextureRegion("hit4"));
        hitAction.add(atlas.getTextureRegion("hit5"));
        hitAction.add(atlas.getTextureRegion("hit6"));
        //currentAnimation = new Animation(atlas.getFps(), hitAction);
        //atlas.setAnimation(currentAnimation);

    }

    public void makeSoldierActions(Body player){

            calcDistanceFromPlayer(player);

            if(atlas.getCos().getLinearVelocity().x < 0.1f && distanceFromPlayer > 2.5f){
                atlas.getCos().applyLinearImpulse(new Vector2(-0.2f, 0.0f),
                        atlas.getCos().getWorldCenter(), true);
            } else if(distanceFromPlayer < 2.5f && distanceFromPlayer > 1.5f && !engaging ){
                atlas.getCos().applyLinearImpulse(new Vector2(-2.0f, 1.0f),
                        atlas.getCos().getWorldCenter(), true);
                currentAnimation = new Animation(atlas.getFps(),attackAction);

            } else if(distanceFromPlayer < 1.5f){
                Gdx.app.log("para","para");
                atlas.getCos().setLinearVelocity(new Vector2(-0.2f, 0.0f));
            /*atlas.getCos().applyLinearImpulse(new Vector2(-0.2f, 0.0f),
                    atlas.getCos().getWorldCenter(), true);*/
                engaging = true;
            }


    }


    private void calcDistanceFromPlayer(Body player){

        distanceFromPlayer = atlas.getCos().getPosition().x - player.getPosition().x;
        Gdx.app.log("Distance between soldier and player: ", String.valueOf(distanceFromPlayer));


    }


    public boolean isEngaging() {
        return engaging;
    }

    public void setEngaging(boolean engaging) {
        this.engaging = engaging;
    }

    public float getDistanceFromPlayer() {
        return distanceFromPlayer;
    }

    public void setDistanceFromPlayer(float distanceFromPlayer) {
        this.distanceFromPlayer = distanceFromPlayer;
    }

    public void setHitAnimation(){
        currentAnimation = new Animation(atlas.getFps(),hitAction);
    }

}
