package com.screen;

import com.Map.GestorContactes;
import com.Map.MapBodyManager;
import com.Map.TiledMapHelper;
import com.Sprite.AtlasAnimation;
import com.Sprite.Coin;
import com.Sprite.Soldier;
import com.Sprite.EpicHero;
import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.math.Vector3;
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

import java.util.ArrayList;
import java.util.Random;

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
    private float deltaDimen = 0.25f;
    private BitmapFont font = new BitmapFont();
    Skin skin = new Skin(Gdx.files.internal("skins/skin.json"));
    int i = 0;
    Label score;
    Label labelAttack;
    Label labelDodge;
    Label labelGameOver;
    Label labelWin;
    private boolean reactionTime = false;
    private Table table;
    private Array<Coin> monedes;
    private float timedelay;


    public void init() {
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
        TextureRegionParallaxLayer mountainsLayerA = new TextureRegionParallaxLayer(mountainsRegionA, worldWidth, new Vector2(.3f, .3f), Utils.WH.width);

        TextureRegion mountainsRegionB = atlas.findRegion("mountains_b");
        TextureRegionParallaxLayer mountainsLayerB = new TextureRegionParallaxLayer(mountainsRegionB, worldWidth * .7275f, new Vector2(.6f, .6f), Utils.WH.width);
        mountainsLayerB.setPadLeft(.2725f * worldWidth);

        TextureRegion cloudsRegion = atlas.findRegion("clouds");
        TextureRegionParallaxLayer cloudsLayer = new TextureRegionParallaxLayer(cloudsRegion, worldWidth, new Vector2(.6f, .6f), Utils.WH.width);
        cloudsLayer.setPadBottom(worldHeight * .467f);

        TextureRegion buildingsRegionA = atlas.findRegion("buildings_a");
        TextureRegionParallaxLayer buildingsLayerA = new TextureRegionParallaxLayer(buildingsRegionA, worldWidth, new Vector2(.75f, .75f), Utils.WH.width);

        TextureRegion buildingsRegionB = atlas.findRegion("buildings_b");
        TextureRegionParallaxLayer buildingsLayerB = new TextureRegionParallaxLayer(buildingsRegionB, worldWidth * .8575f, new Vector2(1, 1), Utils.WH.width);
        buildingsLayerB.setPadLeft(.07125f * worldWidth);
        buildingsLayerB.setPadRight(buildingsLayerB.getPadLeft());

        TextureRegion buildingsRegionC = atlas.findRegion("buildings_c");
        TextureRegionParallaxLayer buildingsLayerC = new TextureRegionParallaxLayer(buildingsRegionC, worldWidth, new Vector2(1.3f, 1.3f), Utils.WH.width);

        parallaxBackground = new ParallaxBackground();
        parallaxBackground.addLayers(mountainsLayerA, mountainsLayerB, cloudsLayer, buildingsLayerA, buildingsLayerB, buildingsLayerC);
        //parallaxBackground.addLayers(mountainsLayerA);


    }

    private void applyWorldAdvance() {
        worldCamera.position.add(deltaDimen, 0, 0);

    }

    /**/

    boolean platformMode = true;
    AtlasAnimation atan;
    //Gestio creacio i gestio del nivell
    private TiledMapHelper mapHelper;
    private World world;
    MapBodyManager mapBodyManager;
    float rotationSpeed = 0.5f;
    Box2DDebugRenderer box2DRenderer;


    //Gestor de colisions y llista de cosos a destruir
    GestorContactes gestorContactes;
    ArrayList<Body> bodyDestroyList;

    /*Musica i UI*/
    Music musica;


    // TODO Afegir declaracions dels Sprites
    //private Hero hero;
    private EpicHero epicHero;
    private Soldier soldier;
    private Texture test;

    private void loadBackground() {
        test = new Texture("levels/Zone1/Cloudy-sky.jpg");
    }

    /**
     * Retorna una instancia de World amb una forsa de
     * gravetat indicada per parametres.
     *
     * @param gravityX
     * @param gravityY
     */
    private World initWorld(float gravityX, float gravityY) {
        if (world == null) {
            return new World(new Vector2(gravityX, gravityY), true);
        } else {
            return null;
        }

    }

    /**
     * Instancia tiledMapHelper
     * Carrega el Mapa del nivell
     * Prepara la camara del joc
     */
    private void initMap() {
        mapHelper = new TiledMapHelper();
        mapHelper.setPackerDirectory("levels/Zone1");
        mapHelper.loadMap("levels/Zone1/level1.tmx");
        mapHelper.prepareCamera(joc.getScreenWidth(), joc.getScreenHeight());

    }

    /**
     * Crea la la fisica del joc
     * asignant les propietats per material segons el fitxer json
     */
    private void makePhysics() {
        mapBodyManager = new MapBodyManager(world,
                GameResourses.PIXELS_PER_METRE,
                Gdx.files.internal("levels/Zone1/materials.json"), 1);
        mapBodyManager.createPhysics(mapHelper.getMap(), "Box2D");
    }

    /**
     * Instancia el gestor de contactes,
     * la llista de destruir cossos
     * I li envia al gestor de contactes
     */
    private void enableColissions() {
        bodyDestroyList = new ArrayList<Body>();
        gestorContactes = new GestorContactes(bodyDestroyList, epicHero);
        world.setContactListener(gestorContactes);
    }

    /**
     * Moure la c�mera en funci� de la posici� del personatge
     */
    private void moureCamera() {

        if (epicHero.bodyExists() && epicHero.isAlive()) {
            // Posicionem la camera centran-la on hi hagi l'sprite del protagonista
            mapHelper.getCamera().position.x = GameResourses.PIXELS_PER_METRE
                    * epicHero.getAtlas().getCos().getPosition().x;
            mapHelper.getCamera().position.y = 346;

        }
        mapHelper.getCamera().update();
    }

    private void handleInputPlatformMode() {

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            epicHero.setMoveRight(true);

        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getX() > Gdx.graphics.getWidth() * 0.80f) {
                    epicHero.setMoveRight(true);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            epicHero.setMoveLeft(true);
        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.20f) {
                    epicHero.setMoveLeft(true);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            epicHero.setJump(true);
        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getY() < Gdx.graphics.getHeight() * 0.20f) {
                    epicHero.setJump(true);
                }
            }
        }

    }

    private void handleInputCombatMode() {

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            epicHero.setDefense(true);
            Gdx.app.log("defense","def");

        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getX() > Gdx.graphics.getWidth() * 0.80f) {
                    epicHero.setDefense(true);
                    Gdx.app.log("defense", "def");
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            epicHero.setDefense(true);
            Gdx.app.log("defense", "def");
        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)
                        && Gdx.input.getX() < Gdx.graphics.getWidth() * 0.20f) {
                    epicHero.setDefense(true);
                    Gdx.app.log("defense", "def");
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
        world = initWorld(0, -9.8f);

        initMap();
        makePhysics();

        epicHero = new EpicHero("sprites/marioSprites.atlas", 3, 5, world, 0.3f, "mario");
        soldier = new Soldier("sprites/soldier.atlas",100,4,world,GameResourses.FPS,"soldier");

        enableColissions();
        box2DRenderer = new Box2DDebugRenderer();
        timedelay = 0;


        init();
        monedes = new Array<Coin>();
        generateCoins("gameObjects/coin.atlas", 7, 5, world, 0.1f, "coin", "Coin1");

    }

    @Override
    public void render(float delta) {
        Gdx.app.log("Position", String.valueOf( epicHero.getAtlas().getCos().getPosition().x));
        epicHero.inicialitzarMoviments();

        if (platformMode) {
            handleInputPlatformMode();

            epicHero.moure();

            world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);


            Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            moureCamera();
            applyWorldAdvance();

        /*ParallaxBackground draw*/
            batch.begin();
            parallaxBackground.draw(worldCamera, batch);
            batch.end();
        /*ParallaxBackground draw*/

        /*Ui Prints*/
            refreshScore();
            stageMenu.act();
            stageMenu.draw();
        /*Ui Prints*/

        /*Game Object draws*/
            SpriteBatch batch2 = new SpriteBatch();
            batch2.setProjectionMatrix(mapHelper.getCamera().combined);
            batch2.begin();
            //hero.dibuixar(batch2);
            if (atan != null) {
                atan.draw(batch2, delta);
            }
            epicHero.draw(batch2, delta);
            drawCoins(batch2, delta);

            batch2.end();
        /*Game Object draws*/

            //Es dibuixa el mapa
            mapHelper.render();

            //Debug renderer
            box2DRenderer.render(world, mapHelper.getCamera().combined.scale(
                    GameResourses.PIXELS_PER_METRE, GameResourses.PIXELS_PER_METRE,
                    GameResourses.PIXELS_PER_METRE));
            destroyBodies();
        } else {
            epicHero.setMoveRight(true);


            makeVisibleCombatUI();
            if(soldier.isEngaging() && epicHero.isAlive() && !epicHero.isDefense()){

                reactionTime = true;
                timedelay += delta;
                Gdx.app.log("parascreen", String.valueOf(timedelay));
                if(timedelay >= 0.1f || epicHero.isDefense()){
                    soldier.setEngaging(false);
                    timedelay = 0;
                    epicHero.setDefense(false);
                }
                moureCamera();
                deltaDimen = 0;
                soldier.getAtlas().setFps(1f);
                epicHero.getAtlas().setFps(1f);

            } else {
                if(reactionTime){
                    handleInputCombatMode();
                }
                soldier.makeSoldierActions(epicHero.getAtlas().getCos());

                epicHero.moure();

                world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
                Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                moureCamera();
                applyWorldAdvance();

        /*ParallaxBackground draw*/
            batch.begin();
            parallaxBackground.draw(worldCamera, batch);
            batch.end();
        /*ParallaxBackground draw*/

        /*Ui Prints*/
            refreshScore();
            stageMenu.act();
            stageMenu.draw();
        /*Ui Prints*/

        /*Game Object draws*/
            SpriteBatch batch2 = new SpriteBatch();
            batch2.setProjectionMatrix(mapHelper.getCamera().combined);
                batch2.begin();
            //hero.dibuixar(batch2);
            if (atan != null) {
                atan.draw(batch2, delta);
            }
                drawCoins(batch2, delta);
                epicHero.draw(batch2, delta);
            soldier.draw(batch2,delta);
                batch2.end();
        /*Game Object draws*/

            //Es dibuixa el mapa
                mapHelper.render();

            //Debug renderer
            box2DRenderer.render(world, mapHelper.getCamera().combined.scale(
                    GameResourses.PIXELS_PER_METRE, GameResourses.PIXELS_PER_METRE,
                    GameResourses.PIXELS_PER_METRE));

        }


    }

        if(epicHero.getAtlas().getCos().getPosition().y <= 0){
            epicHero.setAlive(false);
        }


        if(!epicHero.isAlive()){
            labelGameOver.setVisible(true);
            timedelay += delta;
            Gdx.app.log("dead", String.valueOf(timedelay));
            if(timedelay >= 1.5f){
                ((Game)Gdx.app.getApplicationListener()).setScreen(null);
                HeroesNightfall.gameMode = 0;
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(joc));
            }

        } else if(!epicHero.isPlatformMode()){
            platformMode = false;
        }

        if(soldier.getAtlas().getCos().getUserData().equals("dead")){
            labelWin.setVisible(true);
            timedelay += delta;
            soldier.setHitAnimation();
            epicHero.attackAction();
            Gdx.app.log("dead", String.valueOf(timedelay));
            if(timedelay >= 1.5f){
                ((Game)Gdx.app.getApplicationListener()).setScreen(null);
                HeroesNightfall.gameMode = 0;
                epicHero = null;
                soldier = null;
                monedes = null;
                musica.stop();
                musica = null;
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(joc));
            }
        }

    }



    @Override
    public void dispose() {
        super.dispose();
        epicHero.dispose();
        soldier.dispose();
        world.dispose();
        atlas.dispose();
        musica.dispose();
    }
    @Override
    public void show() {
        stageMenu.clear();

        musica = Gdx.audio.newMusic(Gdx.files
                .internal("audio/EKKOLoginMusic.mp3"));
        musica.setLooping(true);
        musica.setVolume(3f);
        musica.play();


        //Gdx.app.log("Score current value: ", String.valueOf(gestorContactes.getScore()));
        table = new Table();
        score = new Label("Score: " + gestorContactes.getScore(), skin);
        table.add(score).padBottom(600).row();
        table.padLeft(120);


        labelAttack = new Label("Attack",skin);
        labelDodge = new Label("Dodge",skin);
        labelAttack.setPosition(500, 200);
        labelDodge.setPosition(10, 200);

        hideCombatUI();

        labelGameOver = new Label("Game Over",skin);
        labelWin = new Label("Level Complete",skin);

        labelGameOver.setPosition(300,250);
        labelWin.setPosition(300,250);

        labelGameOver.setSize(100,100);

        labelGameOver.setVisible(false);
        labelWin.setVisible(false);

        //TODO Visual paradigm ingenieria inversa
        table.debug();

        stageMenu.addActor(table);
        stageMenu.addActor(labelAttack);
        stageMenu.addActor(labelDodge);
        stageMenu.addActor(labelGameOver);
        stageMenu.addActor(labelWin);
    }

    public void hideCombatUI(){
        labelAttack.setVisible(false);
        labelDodge.setVisible(false);
    }

    public void makeVisibleCombatUI(){
        labelAttack.setVisible(true);
        labelDodge.setVisible(true);
    }

    private void refreshScore(){
        score.setText("Score:  " + gestorContactes.getScore());
    }


    private void destroyBodies(){

        if(!bodyDestroyList.isEmpty()){
            for(Body b : bodyDestroyList){
                for(Coin c : monedes){
                    c.killCoin(b);
                }
                world.destroyBody(b);
            }
        }
        bodyDestroyList.clear();


    }

    private void drawCoins(SpriteBatch batch, float delta){
        for(Coin c: monedes){
            if(c.isAlive()){
                c.draw(batch, delta);
            }
        }
    }

    private void restartAgainPlatMode(){

        ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(joc));


    }

    public Array<Coin> generateCoins(String spriteSheetPath, float positionX, float positionY, World world, float fps, String tag, String modelRegionName){
        Random rnd = new Random();
        int randomNum = rnd.nextInt((10 - 3) + 1) + 3;
        int coinX;
        int coinY;

        for(int i = 0; i < 40; i++){

            coinX = 7+i;

            if(coinX > 46 && coinX < 60){
                coinY = 3;
            } else {
                coinY = 5;
            }

            monedes.add(new Coin(spriteSheetPath, coinX ,  coinY,  world,  fps,  tag,  modelRegionName));

        }


        return monedes;
    }




}
