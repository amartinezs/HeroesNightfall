package com.screen;

import com.Map.GestorContactes;
import com.Map.MapBodyManager;
import com.Map.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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

    //Gestor de colisions y llista de cosos a destruir
    GestorContactes gestorContactes;
    Array<Body> bodyDestroyList;

    /*Musica i UI*/
    private Music music; // TODO No implementat trobar musica

    // TODO Afegir declaracions dels Sprites

    //private Texture splashTexture;
    //private Image splashImage;

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

        // Assegurar que la camera nomes mostra el mapa i res mes
        //Gdx.app.log("ScreenWidth",String.valueOf(joc.getScreenWidth()));
         /* if (mapHelper.getCamera().position.x < joc.getScreenWidth() / 2) {
                mapHelper.getCamera().position.x = joc.getScreenWidth()/ 2;
            }
            if (mapHelper.getCamera().position.x >= mapHelper.getWidth()
                    -  joc.getScreenWidth()/ 2) {
                mapHelper.getCamera().position.x = mapHelper.getWidth()
                        - joc.getScreenWidth()/ 2;
            }

            if (mapHelper.getCamera().position.y < joc.getScreenHeight() / 2) {
                mapHelper.getCamera().position.y = joc.getScreenHeight()/ 2;
            }
            if (mapHelper.getCamera().position.y >= mapHelper.getHeight()
                    - joc.getScreenHeight() / 2) {
                mapHelper.getCamera().position.y = mapHelper.getHeight()
                        - joc.getScreenHeight() / 2;
            }

        if (mapHelper.getCamera().position.x < 600){
            mapHelper.getCamera().position.x = 600;
        }

        if (mapHelper.getCamera().position.x > 6686){
            mapHelper.getCamera().position.x = 6686;
        }*/

        // actualitzar els nous valors de la càmera
        mapHelper.getCamera().update();

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            mapHelper.getCamera().zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            mapHelper.getCamera().zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mapHelper.getCamera().translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mapHelper.getCamera().translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mapHelper.getCamera().translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mapHelper.getCamera().translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            mapHelper.getCamera().rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            mapHelper.getCamera().rotate(rotationSpeed, 0, 0, 1);
        }

        mapHelper.getCamera().update();
       /* mapHelper.getCamera().zoom = MathUtils.clamp(mapHelper.getCamera().zoom, 0.1f, 100 / mapHelper.getCamera().viewportWidth);

        float effectiveViewportWidth = mapHelper.getCamera().viewportWidth * mapHelper.getCamera().zoom;
        float effectiveViewportHeight = mapHelper.getCamera().viewportHeight * mapHelper.getCamera().zoom;

        mapHelper.getCamera().position.x = MathUtils.clamp(mapHelper.getCamera().position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        mapHelper.getCamera().position.y = MathUtils.clamp(mapHelper.getCamera().position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);*/
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
        // carregar la imatge
        /*splashTexture = new Texture(
                Gdx.files.internal("win.png"));
        // seleccionar Linear per millorar l'estirament
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        splashImage = new Image(splashTexture);
        splashImage.setFillParent(true);

        stageMenu.addActor(splashImage);*/

    }

    @Override
    public void render(float delta){

        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        moureCamera();
        handleInput();
        mapHelper.render();
        batch.setProjectionMatrix(mapHelper.getCamera().combined);

        batch.begin();

        batch.end();



        /*stageMenu.act(delta);
        stageMenu.draw();*/

    }

    @Override
    public void dispose() {

    }
    public void show() {

    }



}
