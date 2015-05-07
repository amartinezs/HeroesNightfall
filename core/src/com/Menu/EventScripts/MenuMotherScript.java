package com.Menu.EventScripts;

import com.Menu.StageMenu;
import com.badlogic.gdx.Gdx;
import com.game.HeroesNightfall.HeroesNightfall;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.IBaseItem;
import com.uwsoft.editor.renderer.script.IScript;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

import java.util.ArrayList;

/**
 * Created by Albert on 07/05/2015.
 */
public class MenuMotherScript implements IScript {

    private StageMenu stageMenu;
    private CompositeItem menu;
    private CompositeItem button;
    private SimpleButtonScript buttonScript;


    public MenuMotherScript(StageMenu stageMenu){
        this.stageMenu = stageMenu;
    }



    @Override
    public void init(CompositeItem item) {
        menu = item;

        button = menu.getCompositeById("sheep");
        buttonScript = new SimpleButtonScript();
        buttonScript.init(button);
    }

    @Override
    public void dispose() {
        button.dispose();
        buttonScript.dispose();
    }

    @Override
    public void act(float delta) {
        buttonScript.act(delta);
        if(buttonScript.isDown()){
            Gdx.app.log("Touch a la ","ovella");
        }
        //menu.getCompositeById("sheep").setLayerIndex(-1);
    }
}
