package com.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.game.HeroesNightfall.GameResourses;
import com.game.HeroesNightfall.HeroesNightfall;
import com.uwsoft.editor.renderer.actor.CompositeItem;

/**
 * Created by Albert on 08/05/2015.
 */
public class SonOfTheMenuTest extends MenuMotherScript {

    private final String PLAY_BUTTON = "playButton";
    private final String CUSTOM_BUTTON = "customButton";
    private final String EXIT_BUTTON = "exitButton";
    private final String RANK_BUTTON = "rankButton";
    private final String WEB_BUTTON = "webButton";

    public SonOfTheMenuTest(StageMenu stageMenu) {
        super(stageMenu);
    }

    @Override
    public void init(CompositeItem item) {
        super.init(item);
        gameButtonArray = new Array<GameButton>();
        gameButtonArray.add(new GameButton(PLAY_BUTTON, menu));
        gameButtonArray.add(new GameButton(CUSTOM_BUTTON, menu));
        gameButtonArray.add(new GameButton(EXIT_BUTTON, menu));
        gameButtonArray.add(new GameButton(WEB_BUTTON, menu));
        gameButtonArray.add(new GameButton(RANK_BUTTON, menu));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void act(float delta) {
        for(GameButton eventButton: gameButtonArray){
            eventButton.getButtonScript().act(delta);
            String buttonName = eventButton.getButtonId();
            if(eventButton.getButtonScript().isDown()){
                if(buttonName == PLAY_BUTTON){
                    Gdx.app.log("Touch a la ","ovella");
                    HeroesNightfall.gameMode = 1;
                } else if(buttonName == EXIT_BUTTON) {
                    Gdx.app.log("Touch al boto exit"," Tanquem aplicacio");
                } else if(buttonName == CUSTOM_BUTTON){
                    Gdx.app.log("Touch al boto custom"," obrim custom");
                } else if(buttonName == WEB_BUTTON){
                    Gdx.app.log("Touch boto web"," obrim web");
                } else if(buttonName == RANK_BUTTON){
                    Gdx.app.log("Touch boto rank"," obrim rankings");
                }
            }
        }
    }


}
