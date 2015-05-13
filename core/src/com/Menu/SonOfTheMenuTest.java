package com.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.game.HeroesNightfall.HeroesNightfall;
import com.uwsoft.editor.renderer.actor.CompositeItem;

/**
 * Created by Albert on 08/05/2015.
 */
public class SonOfTheMenuTest extends MenuMotherScript {

    public SonOfTheMenuTest(StageMenu stageMenu) {
        super(stageMenu);
    }

    @Override
    public void init(CompositeItem item) {
        super.init(item);
        gameButtonArray = new Array<GameButton>();
        gameButtonArray.add(new GameButton("playButton", menu));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void act(float delta) {
        for(GameButton eventButton: gameButtonArray){
            eventButton.getButtonScript().act(delta);
            if(eventButton.getButtonScript().isDown()){
                Gdx.app.log("Touch a la ","ovella");
                HeroesNightfall.gameMode = 1;
            }
        }
    }


}
