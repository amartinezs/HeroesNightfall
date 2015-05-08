package com.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.HeroesNightfall.HeroesNightfall;

/**
 * Created by Albert on 07/05/2015.
 */
public class GameScreen extends AbstractScreen {

    private Texture splashTexture;
    private Image splashImage;

    /**
     * Constructor
     *
     * @param joc Classe principal del joc
     */
    public GameScreen(HeroesNightfall joc) {
        super(joc);

        // carregar la imatge
        splashTexture = new Texture(
                Gdx.files.internal("win.png"));
        // seleccionar Linear per millorar l'estirament
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        splashImage = new Image(splashTexture);
        splashImage.setFillParent(true);

        stageMenu.addActor(splashImage);

    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stageMenu.act(delta);
        stageMenu.draw();

    }

    @Override
    public void dispose() {

    }




    public void show() {




    }



}
