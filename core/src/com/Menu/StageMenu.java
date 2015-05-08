package com.Menu;

import com.badlogic.gdx.Gdx;
import com.game.HeroesNightfall.HeroesNightfall;
import com.uwsoft.editor.renderer.Overlap2DStage;

/**
 * Created by Albert on 07/05/2015.
 */
public class StageMenu extends Overlap2DStage {

    private HeroesNightfall game;


    public StageMenu(HeroesNightfall game){
        super();
        this.game = game;
        initSceneLoader();


    }

    public void loadThatScene(String sceneName){
        Gdx.input.setInputProcessor(this);
        sceneLoader.loadScene(sceneName);
        //MenuMotherScript mms = new MenuMotherScript(this);
        SonOfTheMenuTest sotmt = new SonOfTheMenuTest(this);
        sceneLoader.getRoot().addScript(sotmt);
        addActor(sceneLoader.getRoot());
    }
}
