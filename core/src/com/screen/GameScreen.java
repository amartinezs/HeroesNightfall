package com.screen;

import com.Map.GestorContactes;
import com.Map.MapBodyManager;
import com.Map.TiledMapHelper;
import com.Sprite.AtlasAnimation;
import com.Sprite.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.game.HeroesNightfall.GameResourses;
import com.game.HeroesNightfall.HeroesNightfall;
import com.rahul.libgdx.parallax.ParallaxBackground;
import com.rahul.libgdx.parallax.TextureRegionParallaxLayer;
import com.rahul.libgdx.parallax.Utils;

/**
 * Created by Albert on 07/05/2015.
 */
public class GameScreen extends AbstractScreen {

    /*Parallax testing*/
    private OrthographicCamera worldCamera;
    private TextureAtlas atlas;
    private ParallaxBackground parallaxBackground;
    private final float worldWidth = 40;
    private float worldHeight;
    private Color clearColor = new Color(0Xbeaf7bff);
    private final float deltaDimen = 0.25f;
    private BitmapFont font = new BitmapFont();
    Skin skin = new Skin(Gdx.files.internal("skins/skin.json"));
    int i = 0;
    Label score;
    private Table table = new Table();


    public void init () {
        worldHeight = Utils.calculateOtherDimension(Utils.WH.width, worldWidth, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, worldWidth, worldHeight);
        worldCamera.update();
        createLayers();
        stageMenu.clear();

    }

    private void createLayers() {
        atlas = new TextureAtlas("data/main_atlas.atlas");


        TextureRegion mountainsRegionA = atlas.findRegion("mountains_a");
        TextureRegionParallaxLayer mountainsLayerA = new TextureRegionParallaxLayer(mountainsRegionA, worldWidth, new Vector2(.3f,.3f), Utils.WH.width);

        TextureRegion mountainsRegionB = atlas.findRegion("mountains_b");
        TextureRegionParallaxLayer mountainsLayerB = new TextureRegionParallaxLayer(mountainsRegionB, worldWidth*.7275f, new Vector2(.6f,.6f), Utils.WH.width);
        mountainsLayerB.setPadLeft(.2725f*worldWidth);

        TextureRegion cloudsRegion = atlas.findRegion("clouds");
        TextureRegionParallaxLayer cloudsLayer = new TextureRegionParallaxLayer(cloudsRegion, worldWidth, new Vector2(.6f,.6f), Utils.WH.width);
        cloudsLayer.setPadBottom(worldHeight*.467f);

        TextureRegion buildingsRegionA = atlas.findRegion("buildings_a");
        TextureRegionParallaxLayer buildingsLayerA = new TextureRegionParallaxLayer(buildingsRegionA, worldWidth, new Vector2(.75f,.75f), Utils.WH.width);

        TextureRegion buildingsRegionB = atlas.findRegion("buildings_b");
        TextureRegionParallaxLayer buildingsLayerB = new TextureRegionParallaxLayer(buildingsRegionB, worldWidth*.8575f, new Vector2(1,1), Utils.WH.width);
        buildingsLayerB.setPadLeft(.07125f*worldWidth);
        buildingsLayerB.setPadRight(buildingsLayerB.getPadLeft());

        TextureRegion buildingsRegionC = atlas.findRegion("buildings_c");
        TextureRegionParallaxLayer buildingsLayerC = new TextureRegionParallaxLayer(buildingsRegionC, worldWidth, new Vector2(1.3f,1.3f), Utils.WH.width);

        parallaxBackground = new ParallaxBackground();
        parallaxBackground.addLayers(mountainsLayerA,mountainsLayerB,cloudsLayer,buildingsLayerA,buildingsLayerB,buildingsLayerC);
        //parallaxBackground.addLayers(mountainsLayerA);


    }

    private void applyWorldAdvance(){
        worldCamera.position.add(deltaDimen, 0, 0);

    }

    /**/


    AtlasAnimation atan;
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
    private Texture test;

    private void loadBackground(){
        test = new Texture("levels/Zone1/Cloudy-sky.jpg");
    }

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
        gestorContactes = new GestorContactes(bodyDestroyList,hero);
        world.setContactListener(gestorContactes);
    }
    /**
     * Moure la c�mera en funci� de la posici� del personatge
     */
    private void moureCamera() {

        // Posicionem la camera centran-la on hi hagi l'sprite del protagonista
       mapHelper.getCamera().position.x = GameResourses.PIXELS_PER_METRE
                * hero.getPositionBody().x;
        mapHelper.getCamera().position.y = GameResourses.PIXELS_PER_METRE * hero.getPositionBody().y;
           /*mapHelper.getCamera().position.x = 100*3.5f;
        mapHelper.getCamera().position.y = 100*3.5f;*/

        mapHelper.getCamera().update();

    }

    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            hero.setMoureDreta(true);
            hero.setScore(i++);
        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getX() > Gdx.graphics.getWidth() * 0.80f) {
                    hero.setMoureDreta(true);
                    hero.setScore(i++);

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

        hero = new Hero(world, "sprites/mario.png", "sprites/stopmario.png", 3, 3, "mario");

        enableColissions();
        box2DRenderer = new Box2DDebugRenderer();
        //loadBackground();
        init();
        atan = new AtlasAnimation("gameObjects/coin.atlas",world);

    }

    @Override
    public void render(float delta){

        hero.inicialitzarMoviments();
        handleInput();
        hero.moure();
        hero.updatePosition();

        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        moureCamera();
        applyWorldAdvance();
        batch.begin();
        parallaxBackground.draw(worldCamera, batch);

        stageMenu.draw();
        batch.end();
        SpriteBatch batch2 = new SpriteBatch();

        batch2.setProjectionMatrix(mapHelper.getCamera().combined);
        batch2.begin();
        hero.dibuixar(batch2);
        atan.draw(batch2, delta);

        batch2.end();

        mapHelper.render();


        //Gdx.app.log(String.valueOf(hero.getCos().getPosition().y),"har");

        box2DRenderer.render(world, mapHelper.getCamera().combined.scale(
                GameResourses.PIXELS_PER_METRE, GameResourses.PIXELS_PER_METRE,
                GameResourses.PIXELS_PER_METRE));

    }

    @Override
    public void dispose() {
        super.dispose();
        atan.dispose();
        //.dispose();
        stageMenu.dispose();
        hero.dispose();
        world.dispose();
        atlas.dispose();
    }
    public void show() {
        score = new Label("Score: " + hero.getScore(), skin);
        table.add(score).padBottom(600).row();
        table.padLeft(600);
        stageMenu.addActor(table);
    }



}
