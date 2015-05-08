package com.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.HeroesNightfall.GameResourses;
import com.game.HeroesNightfall.GameStage;
import com.game.HeroesNightfall.HeroesNightfall;
import com.uwsoft.editor.renderer.resources.ResourceManager;

/**
 * Created by Albert on 07/05/2015.
 */
public class MainMenuScreen extends AbstractScreen {


    /**
     * Constructor
     *
     * @param joc Classe principal del joc
     */
    public MainMenuScreen(HeroesNightfall joc) {
        super(joc);
        stageMenu.clear();
        stageMenu.loadThatScene("MainScene");
        //ResourceManager rm = new ResourceManager();
        //GameStage gs = new GameStage(rm);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(GameResourses.gameMode == 0){
            stageMenu.act();
            stageMenu.draw();
        } else if(GameResourses.gameMode == 1){
            //((Game)Gdx.app.getApplicationListener()).setScreen(new MainScreen(getGame()));
        }


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {



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



    }






}
