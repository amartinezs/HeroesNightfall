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
    private Array<Sprite> spriteAnimation;
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

        createObject(4, 3, "coin");
        //0.0116f

    }

    public void draw(SpriteBatch batch, float delta){
        elapsedTime += delta;

        float posicioX = GameResourses.PIXELS_PER_METRE * cos.getPosition().x
                - animation.getKeyFrame(elapsedTime, true).getRegionWidth()/2;//((cos.getLinearVelocity().x*delta)+(conversion*cos.getPosition().x))*GameResourses.PIXELS_PER_METRE;//conversion*cos.getPosition().x
        float posicioY = GameResourses.PIXELS_PER_METRE * cos.getPosition().y
                - animation.getKeyFrame(elapsedTime, true).getRegionHeight()/2;//((cos.getLinearVelocity().y*delta)+(conversion*cos.getPosition().y))*GameResourses.PIXELS_PER_METRE;//cos.getPosition().y*conversion
        Gdx.app.log("Speed: ", String.valueOf(cos.getLinearVelocity().x));

        batch.draw(animation.getKeyFrame(elapsedTime, true),posicioX,posicioY);

    }

    public void dispose() {
        spriteSheet.dispose();
    }




    public void createObject(float positionX, float positionY, String tag){

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
            requadre.setAsBox((spriteSheet.findRegion("Coin1").getRegionWidth()) / (2 * GameResourses.PIXELS_PER_METRE),
                    (spriteSheet.findRegion("Coin1").getRegionHeight()) / (2 *GameResourses.PIXELS_PER_METRE));

            /**
             * La densitat i fricci� del protagonista. Si es modifiquen aquests
             * valor anir� m�s r�pid o m�s lent.
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


    public void updatePosition() {
  /*      int[] positions;

        positions[0] = ;

        getSpritePersonatge().setPosition(
                GameResourses.PIXELS_PER_METRE * getCos().getPosition().x
                        - getSpritePersonatge().getWidth() / FRAME_COLS / 2,
                GameResourses.PIXELS_PER_METRE* getCos().getPosition().y
                        - getSpritePersonatge().getHeight() / FRAME_ROWS / 2);
        getSpriteAnimat().setPosition(getSpritePersonatge().getX(), getSpritePersonatge().getY());
*/
    }




}
