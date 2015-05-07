package com.game.HeroesNightfall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.resources.ResourceManager;

import javafx.scene.Scene;


/**
 * Created by Albert on 06/05/2015.
 */
public class GameStage extends Overlap2DStage {

    private ResourceManager rm;

    public GameStage(ResourceManager rm){
        super();
        this.rm = rm;
        initSceneLoader(rm);
        sceneLoader.loadScene("MainScene");
        addActor(sceneLoader.getRoot());
    }




}
