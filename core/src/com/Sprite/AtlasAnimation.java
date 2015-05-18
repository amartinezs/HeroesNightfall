package com.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.HeroesNightfall.GameResourses;

/**
 * Created by Albert on 18/05/2015.
 */
public class AtlasAnimation {

    private TextureAtlas spriteSheet;
    private Array<Sprite> sprites;
    private World world;
    private Body cos;
    private Animation animation;
    private float elapsedTime;
    private float fps;

    private float conversion = 87.5f;

    public AtlasAnimation(String spriteSheetPath, World world){
        spriteSheet = new TextureAtlas(spriteSheetPath);
        animation = new Animation(0.1f,spriteSheet.getRegions());
        this.world = world;

        createObject(4,3,"coin");
        //0.0116f

    }

    public void draw(SpriteBatch batch, float delta){
        elapsedTime += delta;

        float posicioX = cos.getLinearVelocity().x*elapsedTime*conversion+cos.getPosition().x;
        //float posicioY = ;
        Gdx.app.log("Speed: ", String.valueOf(cos.getLinearVelocity().x));
        if(cos.getLinearVelocity().x > 0){
            batch.draw(animation.getKeyFrame(elapsedTime, true),posicioX,cos.getPosition().y*conversion);
        } else {
            batch.draw(animation.getKeyFrame(elapsedTime, true),cos.getPosition().x*conversion,cos.getPosition().y*conversion);
        }
    }

    public void dispose() {
        spriteSheet.dispose();
    }




    public void createObject(float positionX, float positionY, String tag){

            // Definir el tipus de cos i la seva posició
            BodyDef defCos = new BodyDef();
            defCos.type = BodyDef.BodyType.DynamicBody;
            defCos.position.set(positionX, positionY);

            cos = world.createBody(defCos);
            cos.setUserData(tag);

            /**
             * Definir les vores de l'sprite
             */
            PolygonShape requadre = new PolygonShape();
            requadre.setAsBox((spriteSheet.findRegion("Coin1").getRegionWidth()/2) / (2 * GameResourses.PIXELS_PER_METRE),
                    (spriteSheet.findRegion("Coin1").getRegionHeight()/2) / (2 *GameResourses.PIXELS_PER_METRE));

            /**
             * La densitat i fricció del protagonista. Si es modifiquen aquests
             * valor anirà més ràpid o més lent.
             */
            FixtureDef propietats = new FixtureDef();
            propietats.shape = requadre;
            propietats.density = 0.0f;
            propietats.friction = 0.0f;

            cos.setFixedRotation(true);
            cos.createFixture(propietats);

        cos.setGravityScale(0);

        requadre.dispose();




    }




}
