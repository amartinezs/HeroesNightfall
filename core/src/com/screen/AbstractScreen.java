package com.screen;

import com.Menu.StageMenu;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.HeroesNightfall.HeroesNightfall;

/**
 * Created by Albert on 07/05/2015.
 */


/**
 * Classe que abstreu les caracter?stiques b?siques d'una pantalla del joc
 *
 * @author Marc
 *
 */
public class AbstractScreen implements Screen, InputProcessor {
    // atributs
    protected final HeroesNightfall joc;
    /**
     * Objecte SpriteBatch Defineix un bloc en que es crea un lot d'ordres de
     * dibuix per enviar a la GPU
     */
    protected final SpriteBatch
            batch;

    // escena
    protected StageMenu stageMenu;

    /**
     * Constructor
     *
     * @param joc
     *            Classe principal del joc
     */
    public AbstractScreen(HeroesNightfall joc) {
        this.joc= joc;
        this.batch = new SpriteBatch();
        // definim l'stage amb un viewport de mida 0x0px i que mantingui la relaci? d'aspecte
        this.stageMenu = new StageMenu(joc); //0, 0, true);
        this.stageMenu = new StageMenu(joc); //0, 0, true);
        Gdx.input.setInputProcessor(this);
    }

    /**
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render(float delta) {
        // (1) processar la l?gica del joc

        // actualitzar els actors
        stageMenu.act(delta);

        // (2) dibuixar el resultat
        // Pinta la pantalla amb color negre
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // dibuixar els actors.
        stageMenu.draw();
    }

    /**
     * @see com.badlogic.gdx.Screen#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        Gdx.app.log(HeroesNightfall.class.getSimpleName(), "Redimensionant pantalla: " + getName() + " a: "
                + width + " x " + height);

        // redimensionar el viewport del stage
        // tamb? es crida a aquest m?tode en crear la pantalla
        //stage.setViewport(width, height, true);
    }

    /**
     * @see com.badlogic.gdx.Screen#show()
     */
    @Override
    public void show() {
        Gdx.app.log(HeroesNightfall.class.getSimpleName(), "Mostrant pantalla: " + getName());
    }

    /**
     * @see com.badlogic.gdx.Screen#hide()
     */
    @Override
    public void hide() {
        Gdx.app.log(HeroesNightfall.class.getSimpleName(), "Amagant pantalla: " + getName());

        // dispose de la pantalla quan deixem la pantalla.
        // Aquest m?tode no es crida autom?ticament.
        dispose();
    }

    /**
     * @see com.badlogic.gdx.Screen#pause()
     */
    @Override
    public void pause() {
        Gdx.app.log(HeroesNightfall.class.getSimpleName(), "Pansant la pantalla: " + getName());
    }

    /**
     * @see com.badlogic.gdx.Screen#resume()
     */
    @Override
    public void resume() {
        Gdx.app.log(HeroesNightfall.class.getSimpleName(), "Tornant a la pantalla: " + getName());
    }

    /**
     * @see com.badlogic.gdx.Screen#dispose()
     */
    @Override
    public void dispose() {
        Gdx.app.log(HeroesNightfall.class.getSimpleName(), "Alliberant recursos de la pantalla: " + getName());

        stageMenu.dispose();
        batch.dispose();
    }

    /**
     * per recuperar el nom de la pantalla
     *
     * @return
     */
    protected String getName() {
        return getClass().getSimpleName();
    }

    protected HeroesNightfall getGame() {
        return joc;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
