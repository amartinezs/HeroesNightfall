package com.game.HeroesNightfall;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.screen.SplashScreen;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class HeroesNightfall extends Game {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 320;
	public static int gameMode = 0;
	private int screenWidth = WIDTH;
	private int screenHeight = HEIGHT;

	/**
	 * Constructor per defecte
	 */
	public HeroesNightfall() {
		setScreenWidth(-1);
		setScreenHeight(-1);
	}

	/**
	 * Constructor amb par?metres
	 *
	 * @param width
	 *            Amplada de la finestra
	 * @param height
	 *            Al?ada de la finestra
	 */
	public HeroesNightfall(int width, int height) {
		setScreenWidth(width);
		setScreenHeight(height);
	}


	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}


	@Override
	public void resume() {

	}

	@Override
	public void render() {
		super.render();
	}



	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
	}


	public String getTitol() {	return GameResourses.titol;}

	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
























	/*private GameStage gameTest;
	//private ResourceManager rm;
	
	@Override
	public void create () {
		//rm = new ResourceManager();
		//rm.initAllResources();
		//gameTest = new GameStage(rm);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//gameTest.act();
		//gameTest.draw();
	}*/
}
