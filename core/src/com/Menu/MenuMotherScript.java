package com.Menu;

import com.Menu.GameButton;
import com.Menu.StageMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by Albert on 07/05/2015.
 *
 * Conte la informacio basica d'un menu del joc
 *
 * StageMenu (conte el stage del joc amb l'escenari carregat)
 * CompositeItem menu conte el conjunt de items dins l'escena del menu
 * Array<GameButton> gameButtonArray Conte els botons corresponents a aquest escenari/menu
 *
 *
 */
public abstract class MenuMotherScript implements IScript {

    protected StageMenu stageMenu;
    protected CompositeItem menu;
    protected Array<GameButton> gameButtonArray;

    public MenuMotherScript(StageMenu stageMenu){
        this.stageMenu = stageMenu;
    }

    @Override
    public void init(CompositeItem item) {
        menu = item;
    }

    @Override
    public void dispose() {
        for(GameButton button :  gameButtonArray) {
            button.getButton().dispose();
            button.getButtonScript().dispose();
        }
    }


    @Override
    public abstract void act(float delta);

}
