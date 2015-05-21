package com.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.HeroesNightfall.GameResourses;

/**
 * Created by Albert on 11/05/2015.
 */
public class Hero {

    private final int FRAME_ROWS = 2;
    private final int FRAME_COLS = 8;

    /**
     * Detectar el moviment
     */
    private boolean moureEsquerra;
    private boolean moureDreta;
    private boolean ferSalt;
    private boolean personatgeCaraDreta;
    private float yPosition = 0;

    private World world;                // Refer�ncia al mon on est� definit el personatge
    private Body cos;                   // per definir les propietats del cos
    private Sprite spritePersonatge;    // sprite associat al personatge
    private AnimatorWalk spriteAnimat;// animaci� de l'sprite
    private Texture stoppedTexture;     // la seva textura
    private Sound soSalt;               // el so que reprodueix en saltar
    private Texture animatedTexture;
    private boolean isAlive;
    private int score=0;


    public Hero(World world, String animatedImage, String stoppedImage, float positionX, float positionY, String tag){
        moureEsquerra = moureDreta = ferSalt = false;
        this.setWorld(world);
        carregarTextures(animatedImage, stoppedImage);
        //carregarSons();
        crearProtagonista(positionX, positionY, tag);
        setAlive(true);
        //getCos().setGravityScale(0);
    }


    private void carregarTextures(String animatedImage, String stoppedImage) {
        if(animatedImage != null){
            setAnimatedTexture(new Texture(Gdx.files.internal(animatedImage)));
            getAnimatedTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        if(stoppedImage != null) {
            setStoppedTexture(new Texture(Gdx.files.internal(stoppedImage)));
            getStoppedTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    /**
     * Carregar els arxius de so
     */
    private void carregarSons() {
        soSalt = Gdx.audio.newSound(Gdx.files.internal("sons/salt.mp3"));
    }

    private void crearProtagonista(float position1, float position2, String tag) {
        setSpritePersonatge(new Sprite(getAnimatedTexture()));
        setSpriteAnimat(new AnimatorWalk(getSpritePersonatge(), FRAME_COLS, FRAME_ROWS, getStoppedTexture()));

        // Definir el tipus de cos i la seva posici�
        BodyDef defCos = new BodyDef();
        defCos.type = BodyDef.BodyType.DynamicBody;
        defCos.position.set(position1, position2);

        setCos(getWorld().createBody(defCos));
        getCos().setUserData(tag);
        /**
         * Definir les vores de l'sprite
         */
        PolygonShape requadre = new PolygonShape();
        requadre.setAsBox((getSpritePersonatge().getWidth() / FRAME_COLS) / (2 * GameResourses.PIXELS_PER_METRE),
                (getSpritePersonatge().getHeight() / FRAME_ROWS) / (2 *GameResourses.PIXELS_PER_METRE));

        /**
         * La densitat i fricci� del protagonista. Si es modifiquen aquests
         * valor anir� m�s r�pid o m�s lent.
         */
        FixtureDef propietats = new FixtureDef();
        propietats.shape = requadre;
        propietats.density = 1.0f;
        propietats.friction = 3.0f;

        getCos().setFixedRotation(true);
        getCos().createFixture(propietats);

        requadre.dispose();
    }

    public AnimatorWalk getSpriteAnimat(){
        return this.spriteAnimat;
    }

    public void inicialitzarMoviments() {
        setMoureDreta(false);
        setMoureEsquerra(false);
        setFerSalt(false);

        if(getCos().getLinearVelocity().x == 0){
            getSpriteAnimat().setDirection(AnimatorWalk.Direction.STOPPED);
        }



    }

    /**
     * Actualitza la posici� de l'sprite
     */
    public void updatePosition() {
        getSpritePersonatge().setPosition(
                GameResourses.PIXELS_PER_METRE * getCos().getPosition().x
                        - getSpritePersonatge().getWidth() / FRAME_COLS / 2,
                GameResourses.PIXELS_PER_METRE* getCos().getPosition().y
                        - getSpritePersonatge().getHeight() / FRAME_ROWS / 2);
        getSpriteAnimat().setPosition(getSpritePersonatge().getX(), getSpritePersonatge().getY());

    }

    public void dibuixar(SpriteBatch batch) {
        getSpriteAnimat().draw(batch);
    }

    /**
     * Fer que el personatge es mogui
     * <p/>
     * Canvia la posici� del protagonista
     * Es tracta de forma separada el salt perqu� es vol que es pugui moure si salta
     * al mateix temps..
     * <p/>
     * Els impulsos s'apliquen des del centre del protagonista
     */
    public void moure() {

        if (moureDreta && getCos().getLinearVelocity().x < 3) {
            getCos().applyLinearImpulse(new Vector2(0.2f, 0.0f),
                    getCos().getWorldCenter(), true);
                getSpriteAnimat().setDirection(AnimatorWalk.Direction.RIGHT);

            setPersonatgeCaraDreta(true);
        } else if (moureEsquerra) {
            getCos().applyLinearImpulse(new Vector2(-0.2f, 0.0f),
                    getCos().getWorldCenter(), true);
            setPersonatgeCaraDreta(false);
        }

        if (ferSalt && Math.abs(getCos().getLinearVelocity().y) < 1e-9) {
            getCos().applyLinearImpulse(new Vector2(0.0f, 6.0f),
                    getCos().getWorldCenter(), true);
            //long id = soSalt.play();
        }

        /*if(yPosition > getPositionBody().y){
            getSpriteAnimat().setDirection(AnimatorWalk.Direction.FALLING);
        }*/

        yPosition = getPositionBody().y;
    }






    public boolean isMoureEsquerra() {
        return moureEsquerra;
    }
    public void setMoureEsquerra(boolean moureEsquerra) {
        this.moureEsquerra = moureEsquerra;
    }
    public boolean isMoureDreta() {
        return moureDreta;
    }
    public void setMoureDreta(boolean moureDreta) {
        this.moureDreta = moureDreta;
    }
    public boolean isFerSalt() {
        return ferSalt;
    }
    public void setFerSalt(boolean ferSalt) {
        this.ferSalt = ferSalt;
    }
    public boolean isCaraDreta() {
        return this.isPersonatgeCaraDreta();
    }
    public void setCaraDreta(boolean caraDreta) {
        this.setPersonatgeCaraDreta(caraDreta);

    }
    public Sound getSoSalt() {
        return soSalt;
    }
    public void setSoSalt(Sound soSalt) {
        this.soSalt = soSalt;
    }
    public Vector2 getPositionBody() {
        return this.getCos().getPosition();
    }
    public Vector2 getPositionSprite() {
        return new Vector2().set(this.getSpritePersonatge().getX(), this.getSpritePersonatge().getY());
    }
    public Texture getTextura() {
        return getStoppedTexture();
    }
    public void setTextura(Texture textura) {
        this.setStoppedTexture(textura);
    }
    public void dispose() {
        getAnimatedTexture().dispose();
        getStoppedTexture().dispose();
//        soSalt.dispose();
    }
    public boolean isPersonatgeCaraDreta() {
        return personatgeCaraDreta;
    }
    public void setPersonatgeCaraDreta(boolean personatgeCaraDreta) {
        this.personatgeCaraDreta = personatgeCaraDreta;
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
    public Sprite getSpritePersonatge() {
        return spritePersonatge;
    }
    public void setSpritePersonatge(Sprite spritePersonatge) {
        this.spritePersonatge = spritePersonatge;
    }
    public Texture getStoppedTexture() {
        return stoppedTexture;
    }
    public void setStoppedTexture(Texture stoppedTexture) {
        this.stoppedTexture = stoppedTexture;
    }
    public Texture getAnimatedTexture() {
        return animatedTexture;
    }
    public void setAnimatedTexture(Texture animatedTexture) {
        this.animatedTexture = animatedTexture;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    public void setSpriteAnimat(AnimatorWalk spriteAnimat) {
        this.spriteAnimat = spriteAnimat;
    }
    public int getScore() {return score;}
    public void setScore(int score) { this.score = this.score + score; }




















}
