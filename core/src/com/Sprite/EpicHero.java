package com.Sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.HeroesNightfall.GameResourses;

/**
 * Created by Albert on 22/05/2015.
 */
public class EpicHero {

    //Avaible moves
    private boolean moveRight;
    private boolean moveLeft;
    private boolean jump;
    private boolean stop;
    //Does al related painting/load sprite tasks
    private AtlasAnimation atlas;
    private boolean platformMode;
    private boolean alive;
    private boolean defense;
    private TextureRegion attack;


    public EpicHero(String spriteSheetPath, float positionX, float positionY, World world, float fps , String tag){
        moveRight = moveLeft = jump = false;
        atlas = new AtlasAnimation(spriteSheetPath, positionX, positionY, world, fps , tag, GameResourses.HERO_WIDTH, GameResourses.HERO_HEIGHT);
        buildWalkRightAnimation();
        platformMode = true;
        alive = true;
        attack = atlas.getTextureRegion("12");
    }

    public void dispose(){
        atlas.dispose();
    }

    public void draw(SpriteBatch batch , float delta) {
        atlas.draw(batch, delta,GameResourses.HERO_WIDTH,GameResourses.HERO_HEIGHT);
    }

    public void inicialitzarMoviments() {
        stop = false;
        jump = false;
        moveRight = true;
        moveLeft = false;
    }

    public void attackAction(){
        atlas.setAnimation(new Animation(atlas.getFps(),attack));
    }


    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public AtlasAnimation getAtlas() {
        return atlas;
    }

    public void setAtlas(AtlasAnimation atlas) {
        this.atlas = atlas;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    /**
     * Fer que el personatge es mogui
     * <p/>
     * Canvia la posici? del protagonista
     * Es tracta de forma separada el salt perqu? es vol que es pugui moure si salta
     * al mateix temps..
     * <p/>
     * Els impulsos s'apliquen des del centre del protagonista
     */
    public void moure() {

        if (moveRight && atlas.getCos().getLinearVelocity().x < 2 && alive) {
            atlas.getCos().applyLinearImpulse(new Vector2(0.5f, 0.0f),
                    atlas.getCos().getWorldCenter(), true);
            //getSpriteAnimat().setDirection(AnimatorWalk.Direction.RIGHT);

            //setPersonatgeCaraDreta(true);
        } else if (moveLeft && alive) {
            atlas.getCos().applyLinearImpulse(new Vector2(-0.5f, 0.0f),
                    atlas.getCos().getWorldCenter(), true);
            //setPersonatgeCaraDreta(false);
        }

        if (jump && Math.abs(atlas.getCos().getLinearVelocity().y) < 1e-9 && alive) {
            atlas.getCos().applyLinearImpulse(new Vector2(0.0f, 4.5f),
                    atlas.getCos().getWorldCenter(), true);
            //long id = soSalt.play();
        }

        /*if(yPosition > getPositionBody().y){
            getSpriteAnimat().setDirection(AnimatorWalk.Direction.FALLING);
        }*/

        //yPosition = getPositionBody().y;
    }


    public void buildWalkRightAnimation(){
        atlas.setAnimation(null);
        Array<TextureRegion> walkRightFrames = new Array<TextureRegion>();

        walkRightFrames.add(atlas.getTextureRegion("0"));
        walkRightFrames.add(atlas.getTextureRegion("1"));
        walkRightFrames.add(atlas.getTextureRegion("2"));
        walkRightFrames.add(atlas.getTextureRegion("3"));
        walkRightFrames.add(atlas.getTextureRegion("4"));
        walkRightFrames.add(atlas.getTextureRegion("5"));
        walkRightFrames.add(atlas.getTextureRegion("6"));
        walkRightFrames.add(atlas.getTextureRegion("7"));

        atlas.setAnimation(new Animation(atlas.getFps(),walkRightFrames));

    }


    public boolean bodyExists(){

        if(atlas.getCos() == null){
            return false;
        } else {
            return true;
        }

    }

    public boolean isPlatformMode() {
        return platformMode;
    }

    public void setPlatformMode(boolean platformMode) {
        this.platformMode = platformMode;
    }

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isDefense() {
        return defense;
    }

    public void setDefense(boolean defense) {
        this.defense = defense;
    }
}
