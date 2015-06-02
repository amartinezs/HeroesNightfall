package com.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.game.HeroesNightfall.HeroesNightfall;

/**
 * Created by Albert on 07/05/2015.
 */
public class MainMenuScreen extends AbstractScreen {

    private HeroesNightfall game;
    Music musica;

    /**
     * Constructor
     *
     * @param joc Classe principal del joc
     */
    public MainMenuScreen(HeroesNightfall joc) {
        super(joc);
        this.game = joc;
        stageMenu.clear();
        stageMenu.loadThatScene("MainScene");

        //ResourceManager rm = new ResourceManager();
        //GameStage gs = new GameStage(rm);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(HeroesNightfall.gameMode == 0){
            stageMenu.act();
            stageMenu.draw();
        } else if(HeroesNightfall.gameMode == 1){
            //stageMenu.clear();
            ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
        } else if(HeroesNightfall.gameMode == 3){
            Gdx.app.exit();
        }


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        musica = Gdx.audio.newMusic(Gdx.files
                .internal("audio/02 Telepathetic.mp3"));
        musica.setLooping(true);
        musica.setPosition(3f);
        musica.setVolume(1f);
        musica.play();


    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        musica.dispose();


    }






}
