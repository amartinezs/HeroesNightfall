package com.Sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.HeroesNightfall.GameResourses;

/**
 * Created by Albert on 18/05/2015.
 */
public class AtlasAnimation {

    private TextureAtlas spriteSheet;

    private float positionX;
    private float positionY;
    private World world;


    private Animation animation;
    private float elapsedTime;
    private float fps;

    private Body cos;

    /**
     * Creates an animation and a body for that animation in the map
     * By default does an animation with all the regions in the atlas
     * and body with the width and height of the modelRegion parameter
     * */
    public AtlasAnimation(String spriteSheetPath, float positionX, float positionY, World world, float fps , String tag, String modelRegionName){
        spriteSheet = new TextureAtlas(spriteSheetPath);
        this.fps = fps;
        animation = new Animation(this.fps,spriteSheet.getRegions());
        this.world = world;
        this.positionX = positionX;
        this.positionY = positionY;
        createObject(positionX, positionY, tag, modelRegionName);
    }
    /**
     * Creates an animation and a body with a custom width and height
     * for that animation
     *
     * */
    public AtlasAnimation(String spriteSheetPath, float positionX, float positionY, World world, float fps , String tag, float width, float height){
        spriteSheet = new TextureAtlas(spriteSheetPath);
        this.fps = fps;
        animation = new Animation(this.fps,spriteSheet.getRegions());
        this.world = world;
        this.positionX = positionX;
        this.positionY = positionY;
        createObject(positionX, positionY, tag, width, height);
    }


    public void draw(SpriteBatch batch, float delta){
        elapsedTime += delta;
        updatePosition();
        batch.draw(animation.getKeyFrame(elapsedTime, true), positionX, positionY);
    }

    public void draw(SpriteBatch batch, float delta, float width, float height){
        elapsedTime += delta;
        updatePositionCustom(width,height);
        batch.draw(animation.getKeyFrame(elapsedTime, true), positionX, positionY,width,height);
    }

    public void dispose() {
        spriteSheet.dispose();
    }


    /**
     * createObject overload method does the same but creates a body the specified width and height
     * */
    public void createObject(float positionX, float positionY, String tag, float width, float height){
        // Definir el tipus de cos i la seva posici�
        BodyDef defCos = new BodyDef();
        defCos.type = BodyDef.BodyType.DynamicBody;
        defCos.position.set(positionX, positionY);

        cos = world.createBody(defCos);
        cos.setUserData(tag);

        /**
         * Definir les vores de l'sprite
         */
        PolygonShape requadre = new PolygonShape();
        requadre.setAsBox(width / (2 * GameResourses.PIXELS_PER_METRE),
                height / (2 *GameResourses.PIXELS_PER_METRE));

        /**
         * La densitat i fricci� del protagonista. Si es modifiquen aquests
         * valor anir� m�s r�pid o m�s lent.
         */
        FixtureDef propietats = new FixtureDef();
        propietats.shape = requadre;
        propietats.density = 1.0f;
        propietats.friction = 1.0f;

        cos.setFixedRotation(true);
        cos.createFixture(propietats);

        //cos.setGravityScale(0);

        requadre.dispose();



    }


    public void createObject(float positionX, float positionY, String tag, String modelRegionName){

            // Definir el tipus de cos i la seva posici�
            BodyDef defCos = new BodyDef();
            defCos.type = BodyDef.BodyType.DynamicBody;
            defCos.position.set(positionX, positionY);

            cos = world.createBody(defCos);
            cos.setUserData(tag);

            /**
             * Definir les vores de l'sprite
             */
            PolygonShape requadre = new PolygonShape();
            requadre.setAsBox((spriteSheet.findRegion(modelRegionName).getRegionWidth()) / (2 * GameResourses.PIXELS_PER_METRE),
                    (spriteSheet.findRegion(modelRegionName).getRegionHeight()) / (2 *GameResourses.PIXELS_PER_METRE));

            /**
             * La densitat i fricci� del protagonista. Si es modifiquen aquests
             * valor anir� m�s r�pid o m�s lent.
             */
            FixtureDef propietats = new FixtureDef();
            propietats.shape = requadre;
            propietats.density = 1.0f;
            propietats.friction = 1.0f;

            cos.setFixedRotation(true);
            cos.createFixture(propietats);

        //cos.setGravityScale(0);

        requadre.dispose();




    }

    public void updatePosition() {
        positionX = GameResourses.PIXELS_PER_METRE * cos.getPosition().x
                - animation.getKeyFrame(elapsedTime, true).getRegionWidth()/2;
        positionY = GameResourses.PIXELS_PER_METRE * cos.getPosition().y
                - animation.getKeyFrame(elapsedTime, true).getRegionHeight()/2;
    }

    public void updatePositionCustom(float width, float height){
        positionX = GameResourses.PIXELS_PER_METRE * cos.getPosition().x
                - width/2;
        positionY = GameResourses.PIXELS_PER_METRE * cos.getPosition().y
                - height/2;
    }

    public TextureAtlas getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(TextureAtlas spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getCos() {
        return cos;
    }

    public void setCos(Body cos) {
        this.cos = cos;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public float getFps() {
        return fps;
    }

    public void setFps(float fps) {
        this.fps = fps;
    }

    public TextureRegion getTextureRegion(String regionName){

        return spriteSheet.findRegion(regionName);


    }


}
