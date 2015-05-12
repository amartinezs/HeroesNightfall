package com.screen;

import com.Map.GestorContactes;
import com.Map.MapBodyManager;
import com.Map.TiledMapHelper;
import com.Sprite.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.game.HeroesNightfall.GameResourses;
import com.game.HeroesNightfall.HeroesNightfall;

/**
 * Created by Albert on 07/05/2015.
 */
public class GameScreen extends AbstractScreen {

    //Gestio creacio i gestio del nivell
    private TiledMapHelper mapHelper;
    private World world;
    MapBodyManager mapBodyManager;
    float rotationSpeed = 0.5f;
    Box2DDebugRenderer box2DRenderer;


    //Gestor de colisions y llista de cosos a destruir
    GestorContactes gestorContactes;
    Array<Body> bodyDestroyList;

    /*Musica i UI*/
    private Music music; // TODO No implementat trobar musica

    // TODO Afegir declaracions dels Sprites
    private Hero hero;


    /** Retorna una instancia de World amb una forsa de
     * gravetat indicada per parametres.
     * @param gravityX
     * @param gravityY */
    private World initWorld(float gravityX, float gravityY){
        if(world == null){
            return new World(new Vector2(gravityX,gravityY),true);
        } else {
            return null;
        }

    }
    /**Instancia tiledMapHelper
     * Carrega el Mapa del nivell
     * Prepara la camara del joc*/
    private void initMap(){
        mapHelper = new TiledMapHelper();
        mapHelper.setPackerDirectory("levels/Zone1");
        mapHelper.loadMap("levels/Zone1/theFinalMap.tmx");
        mapHelper.prepareCamera(joc.getScreenWidth(),joc.getScreenHeight());
    }
    /**Crea la la fisica del joc
     * asignant les propietats per material segons el fitxer json*/
    private void makePhysics() {
        mapBodyManager = new MapBodyManager(world,
                GameResourses.PIXELS_PER_METRE,
                Gdx.files.internal("levels/Zone1/materials.json"), 1);
        mapBodyManager.createPhysics(mapHelper.getMap(), "Box2D");
    }
    /**Instancia el gestor de contactes,
     * la llista de destruir cossos
     * I li envia al gestor de contactes */
    private void enableColissions(){
        bodyDestroyList = new Array<Body>();
        gestorContactes = new GestorContactes(bodyDestroyList);
        world.setContactListener(gestorContactes);
    }
    /**
     * Moure la càmera en funció de la posició del personatge
     */
    private void moureCamera() {

        // Posicionem la camera centran-la on hi hagi l'sprite del protagonista
       /*mapHelper.getCamera().position.x = GameResourses.PIXELS_PER_METRE
                * 3+400;*/
        mapHelper.getCamera().position.x = 100*3.5f;
        mapHelper.getCamera().position.y = 100*3.5f;

        mapHelper.getCamera().update();

    }

    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            hero.setMoureDreta(true);

        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getX() > Gdx.graphics.getWidth() * 0.80f) {
                    hero.setMoureDreta(true);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            hero.setMoureEsquerra(true);

        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.20f) {
                    hero.setMoureEsquerra(true);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            hero.setFerSalt(true);
        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getY() < Gdx.graphics.getHeight() * 0.20f) {
                    hero.setFerSalt(true);
                }
            }
        }

    }


    /**
     * Constructor
     *
     * @param joc Classe principal del joc
     */
    public GameScreen(HeroesNightfall joc) {
        super(joc);

        world = initWorld(0,-9.8f);
        initMap();
        makePhysics();
        enableColissions();
        box2DRenderer = new Box2DDebugRenderer();
        hero = new Hero(world, "sprites/noBlankMario.png", "sprites/stopmario.png", 3, 3, "mario");

    }

    @Override
    public void render(float delta){


        hero.inicialitzarMoviments();
        handleInput();
        hero.moure();
        hero.updatePosition();


        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        moureCamera();

        mapHelper.render();


        batch.setProjectionMatrix(mapHelper.getCamera().combined);

        batch.begin();
            hero.dibuixar(batch);
        batch.end();

        box2DRenderer.render(world, mapHelper.getCamera().combined.scale(
                GameResourses.PIXELS_PER_METRE, GameResourses.PIXELS_PER_METRE,
                GameResourses.PIXELS_PER_METRE));

    }

    @Override
    public void dispose() {

    }
    public void show() {

    }



}
