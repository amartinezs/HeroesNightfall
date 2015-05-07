package com.Menu;

import com.Menu.EventScripts.MenuMotherScript;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.game.HeroesNightfall.HeroesNightfall;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.resources.ResourceManager;

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
        MenuMotherScript mms = new MenuMotherScript(this);
        sceneLoader.getRoot().addScript(mms);
        addActor(sceneLoader.getRoot());
    }

    public void loadButton(){







    }





}
